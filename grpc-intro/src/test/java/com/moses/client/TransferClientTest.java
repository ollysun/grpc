package com.moses.client;

import com.moses.models.BankServiceGrpc;
import com.moses.models.TransferRequest;
import com.moses.models.TransferServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferClientTest {


    // asynchronous request
    private TransferServiceGrpc.TransferServiceStub transferServiceStub;

    @BeforeAll
    public void setUp(){
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext().build();
        transferServiceStub = TransferServiceGrpc.newStub(managedChannel);
    }

    @Test
    void transfer() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        TransferStreamingResponse transferStreamingResponse = new TransferStreamingResponse(latch);
        StreamObserver<TransferRequest> requestStreamObserver = transferServiceStub.transfer(transferStreamingResponse);
        // let pass the transfer request here
        for (int i = 0; i < 100; i++) {

            TransferRequest transferRequest = TransferRequest.newBuilder()
                    .setFromAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setToAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setAmount(ThreadLocalRandom.current().nextInt(1, 22))
                    .build();
            requestStreamObserver.onNext(transferRequest);
        }
        requestStreamObserver.onCompleted();
        latch.await();
    }
}
