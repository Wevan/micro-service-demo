package com.example.userservice.service;

import com.example.userservice.UserRequest;
import com.example.userservice.UserResponse;
import com.example.userservice.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Value("${server.port:9090}")
    private String serverPort;
    
    @Value("${spring.application.name:user-service}")
    private String applicationName;
    
    @Override
    public void getUserById(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        String instanceId = applicationName + "-" + serverPort;
        
        logger.info("=== 实例 {} 接收到请求 ===", instanceId);
        logger.info("请求用户ID: {}", request.getId());
        logger.info("请求时间: {}", java.time.LocalDateTime.now());
        
        UserResponse response = UserResponse.newBuilder()
                .setId(request.getId())
                .setName("User_" + request.getId())
                .setEmail("user" + request.getId() + "@example.com")
                .build();

        logger.info("实例 {} 返回响应: 用户ID={}, 用户名={}, 邮箱={}", 
                   instanceId, response.getId(), response.getName(), response.getEmail());
        logger.info("=== 实例 {} 处理完成 ===\n", instanceId);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}