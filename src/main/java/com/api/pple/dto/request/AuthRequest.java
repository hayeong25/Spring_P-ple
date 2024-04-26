package com.api.pple.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class AuthRequest {
    @NotBlank
    private String telNo;
}