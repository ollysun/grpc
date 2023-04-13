package com.moses.server.loadbalancing;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer2 {

    public static void main(String[] args) throws Exception {

        Server server = ServerBuilder.forPort(7575)
                .addService(new BankService())
                .build();

        server.start();

        server.awaitTermination();

    }
}
