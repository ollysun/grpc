package com.moses.server;

import com.moses.models.Account;
import com.moses.models.TransferRequest;
import com.moses.models.TransferResponse;
import com.moses.models.TransferStatus;
import io.grpc.stub.StreamObserver;

public class TransferStreamingRequest implements StreamObserver<TransferRequest> {

    private final StreamObserver<TransferResponse> transferResponseStreamObserver;

    public TransferStreamingRequest(StreamObserver<TransferResponse> transferResponseStreamObserver) {
        this.transferResponseStreamObserver = transferResponseStreamObserver;
    }

    @Override
    public void onNext(TransferRequest transferRequest) {
        int fromAccount = transferRequest.getFromAccount();
        int toAccount = transferRequest.getToAccount();
        int amount = transferRequest.getAmount();
        int balance = AccountDatabase.getBalance(fromAccount);

        TransferStatus status = TransferStatus.FAILED;

        if(balance >= amount && fromAccount != toAccount){
          AccountDatabase.deductBalance(fromAccount, amount);
          AccountDatabase.addBalance(toAccount, amount);
          status = TransferStatus.SUCCESS;
        }

        Account fromAccountBalance = Account.newBuilder()
                .setAccountNumber(fromAccount)
                .setAmount(AccountDatabase.getBalance(fromAccount))
                .build();
        Account toAccountBalance = Account.newBuilder()
                .setAccountNumber(toAccount)
                .setAmount(AccountDatabase.getBalance(toAccount))
                .build();

        TransferResponse transferResponse = TransferResponse.newBuilder()
                .setStatus(status)
                .addAccounts(fromAccountBalance)
                .addAccounts(toAccountBalance)
                .build();
        transferResponseStreamObserver.onNext(transferResponse);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        AccountDatabase.printAccountDetails();
        transferResponseStreamObserver.onCompleted();
    }
}
