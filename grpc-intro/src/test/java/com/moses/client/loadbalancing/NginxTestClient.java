package com.moses.client.loadbalancing;

import com.moses.models.Balance;
import com.moses.models.BalanceCheckRequest;
import com.moses.models.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NginxTestClient {

    // synchronous request
    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;



    @BeforeAll
    public void setUp(){
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8585)
                .usePlaintext().build();
        bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
    }

    @Test
    void balanceTest(){

        for (int i = 1; i < 100; i++) {
            BalanceCheckRequest build = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(ThreadLocalRandom.current().nextInt(1,11))
                    .build();
            Balance balance = this.bankServiceBlockingStub.getBalance(build);
            System.out.println("Receive amount " + balance.getAmount());
        }


    }

}
