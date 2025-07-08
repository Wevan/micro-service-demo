package com.example.clientservice.config;

import com.example.userservice.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceStub() {
        return userServiceStub;
    }
}