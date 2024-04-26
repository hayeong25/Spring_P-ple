package com.api.pple.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AdRequest {
    @Getter
    @AllArgsConstructor
    public static class Register {
        @NotBlank
        private String companyName;
        @NotBlank
        private String image;
        @NotBlank
        private String url;
    }
}