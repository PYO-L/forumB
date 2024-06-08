package com.example.blog.controller;

import com.example.blog.dto.LoginRequest;
import com.example.blog.dto.RegisterRequest;
import com.example.blog.dto.UserDTO;
import com.example.blog.model.User;
import com.example.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUsername(@RequestHeader("Authorization") String token) {
        Optional<UserDTO> userDTO = authService.getUserByUsername(token);
        return userDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        String response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        String tokenWithBearer = "Bearer " + token; // 토큰 앞에 "Bearer " 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenWithBearer);
        return ResponseEntity.ok().headers(headers).body(token); // 토큰 반환
    }

}
