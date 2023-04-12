package com.moses.client;

import com.moses.models.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

    // synchronous request
    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;

    // asynchronous request
    private BankServiceGrpc.BankServiceStub bankServiceStub;


    @BeforeAll
    public void setUp(){
        ManagedChannel  managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                                                                    .usePlaintext().build();
        bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        bankServiceStub = BankServiceGrpc.newStub(managedChannel);
    }
    
    @Test
    void balanceTest(){

        BalanceCheckRequest build = BalanceCheckRequest.newBuilder()
                .setAccountNumber(2)
                .build();
        Balance balance = this.bankServiceBlockingStub.getBalance(build);
        System.out.println("Receive amount " + balance.getAmount());
    }


    @Test
    void withDrawTest(){
        WithdrawRequest withdraw = WithdrawRequest.newBuilder()
                .setAccountNumber(7)
                .setAmount(40)
                .build();

        this.bankServiceBlockingStub.withdraw(withdraw)
                .forEachRemaining(money -> System.out.println("Received : " +
                        money.getValue()));
    }

    @Test
    void withdrawAsyncTest() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        WithdrawRequest withdraw = WithdrawRequest.newBuilder()
                .setAccountNumber(10)
                .setAmount(50)
                .build();
        bankServiceStub.withdraw(withdraw, new MoneyStreamingResponse(countDownLatch));
        countDownLatch.await();
    }

    @Test
    void cashStreamingRequest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        StreamObserver<DepositRequest> depositRequestStreamObserver = bankServiceStub.cashDeposit(new BalanceStreamObserver(countDownLatch));
        //send stream of request
        for (int i = 0; i < 10; i++) {
            DepositRequest build = DepositRequest.newBuilder()
                    .setAccountNumber(8)
                    .setAmount(10)
                    .build();
            depositRequestStreamObserver.onNext(build);
        }
        depositRequestStreamObserver.onCompleted();
        countDownLatch.await();
    }
}
