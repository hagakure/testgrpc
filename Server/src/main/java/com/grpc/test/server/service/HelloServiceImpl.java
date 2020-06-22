package com.grpc.test.server.service;

import com.grpc.test.HelloServiceGrpc;
import com.grpc.test.HelloserviceMsg.*;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        logger.info(request.getFirstName());
        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting("Hello "+request.getFirstName())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void check(CheckRequest request, StreamObserver<CheckResponse> responseObserver) {
        CheckResponse response = CheckResponse.newBuilder()
                .setResponse("Everything is fine!!! Aoc-stub connected")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
