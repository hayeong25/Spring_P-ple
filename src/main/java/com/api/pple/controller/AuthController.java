package com.api.pple.controller;

import com.api.pple.dto.request.AuthRequest;
import com.api.pple.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    /*
    * 본인인증 6자리 번호 전송
    */
    @PostMapping("/otp")
    public ResponseEntity<String> sendOtp(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authService.sendOtp(request));
    }
}