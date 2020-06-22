package com.grpc.test.server;

import com.grpc.test.server.service.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcServer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GrpcServer.class, args);
        Server server = ServerBuilder
                .forPort(Integer.parseInt(System.getenv("GRPC_SERVER_PORT")))
                .addService(new HelloServiceImpl())
                .build();
        server.start();
        server.awaitTermination();
    }
}
