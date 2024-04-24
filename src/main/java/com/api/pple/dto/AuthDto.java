package com.api.pple.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class AuthDto {
    @Getter
    @ToString
    @AllArgsConstructor
    public static class Otp {
        @NotBlank
        private String telNo;
    }
}