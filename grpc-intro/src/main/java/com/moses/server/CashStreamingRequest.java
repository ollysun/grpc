package com.moses.server;

import com.moses.models.Balance;
import com.moses.models.DepositRequest;
import io.grpc.stub.StreamObserver;

public class CashStreamingRequest implements StreamObserver<DepositRequest> {

    private final StreamObserver<Balance> balanceStreamObserver;

    public CashStreamingRequest(StreamObserver<Balance> balanceStreamObserver) {
        this.balanceStreamObserver = balanceStreamObserver;
    }

    private int accountBalance;

    @Override
    public void onNext(DepositRequest depositRequest) {

        int accountNumber = depositRequest.getAccountNumber();
        int amount = depositRequest.getAmount();
        accountBalance = AccountDatabase.addBalance(accountNumber,amount);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        Balance balance = Balance.newBuilder().setAmount(accountBalance).build();
        balanceStreamObserver.onNext(balance);
        balanceStreamObserver.onCompleted();
    }
}
