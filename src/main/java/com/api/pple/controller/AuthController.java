package com.api.pple.controller;

import com.api.pple.dto.AuthDto;
import com.api.pple.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    /*
    * 본인인증 6자리 번호 전송
    */
    @PostMapping("/otp")
    public ResponseEntity<String> sendOtp(@RequestBody @Valid AuthDto.Otp request) {
        log.info("AuthController sendOtp requestBody : {}", request);
        return ResponseEntity.ok(authService.sendOtp(request));
    }
}