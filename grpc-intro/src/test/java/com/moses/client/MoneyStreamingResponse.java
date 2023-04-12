package com.moses.client;

import com.moses.models.Money;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class MoneyStreamingResponse implements StreamObserver<Money> {

    private final CountDownLatch countDownLatch;

    public MoneyStreamingResponse(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onNext(Money money) {

        System.out.println(
                "received async : " + money.getValue()
        );
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(
                throwable.getMessage()
        );
        countDownLatch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println(
                "Server is Done"
        );
        countDownLatch.countDown();
    }
}
