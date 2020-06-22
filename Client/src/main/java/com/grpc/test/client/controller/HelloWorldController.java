package com.grpc.test.client.controller;

import com.grpc.test.HelloServiceGrpc;
import com.grpc.test.HelloserviceMsg;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@RestController
public class HelloWorldController {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @GetMapping("/hello")
    public String addMessage(@RequestParam String firstName){
        logger.info("request sended to gateway at "+LocalDateTime.now());
        String result = this.getGreeting(System.getenv("GRPC_SERVER_ADDRESS"),
                Integer.parseInt(System.getenv("GRPC_SERVER_PORT")),
                firstName);

        logger.info("response received from gateway at "+LocalDateTime.now());
        return result;
    }
	
    private String getGreeting(String address,Integer port,String firstName) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(address,port)
                .usePlaintext()
                .build();
        HelloServiceGrpc.HelloServiceBlockingStub stub
                = HelloServiceGrpc.newBlockingStub(channel);
        HelloserviceMsg.HelloResponse helloResponse = stub.hello(HelloserviceMsg.HelloRequest.newBuilder()
                .setFirstName(firstName)
                .build());
        channel.shutdown();
        return helloResponse.getGreeting();
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String test(){
        return "Client is running!!!";
    }
	
	@GetMapping("/check")
    public String checkConnections(@RequestParam String address, @RequestParam Integer port){
        logger.info("request sended to gateway at "+LocalDateTime.now());
        try {
            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress(address,port)
                    .usePlaintext()
                    .build();
            HelloServiceGrpc.HelloServiceBlockingStub stub
                    = HelloServiceGrpc.newBlockingStub(channel);

            HelloserviceMsg.CheckResponse helloResponse = stub.check(HelloserviceMsg.CheckRequest.newBuilder()
                    .build());
            channel.shutdown();
            logger.info("response received from gateway at "+LocalDateTime.now());
            return helloResponse.getResponse();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return "Connection to gateway error: "+sw.toString();
        }
    }
}
