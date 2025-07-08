package com.example.clientservice.controller;

import com.example.userservice.UserRequest;
import com.example.userservice.UserResponse;
import com.example.userservice.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Value("${server.port:8081}")
    private String serverPort;
    
    @Value("${spring.application.name:client-service}")
    private String applicationName;

    private final UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable("id") int id) {
        String clientInstanceId = applicationName + "-" + serverPort;
        
//        logger.info("=== Client实例 {} 发送请求 ===", clientInstanceId);
//        logger.info("请求用户ID: {}", id);
//        logger.info("请求时间: {}", java.time.LocalDateTime.now());
        
        UserRequest request = UserRequest.newBuilder().setId(id).build();
        UserResponse response = userServiceStub.getUserById(request);
        
//        logger.info("Client实例 {} 收到响应: 用户ID={}, 用户名={}, 邮箱={}",
//                   clientInstanceId, response.getId(), response.getName(), response.getEmail());
//        logger.info("=== Client实例 {} 处理完成 ===\n", clientInstanceId);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("id", response.getId());
        responseBody.put("name", response.getName());
        responseBody.put("email", response.getEmail());
        responseBody.put("timestamp", java.time.LocalDateTime.now().toString());

        return ResponseEntity.ok(responseBody);
        
//        return "User: " + response.getName() + ", Email: " + response.getEmail();
    }
}