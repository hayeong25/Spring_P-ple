package com.api.pple.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class MemberDto {
    private String id;
    private String name;
    private String accessToken;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class Join {
        @NotBlank
        private String id;
        @NotBlank
        private String password;
        @NotBlank
        private String name;
        @NotBlank
        private String telNo;
        @NotBlank
        private String email;
        @NotBlank
        private String businessNo;
        @NotBlank
        private String companyName;
        @NotBlank
        private String zipcode;
        @NotBlank
        private String address;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class Login {
        @NotBlank
        private String id;
        @NotBlank
        private String password;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class FindId {
        @NotBlank
        private String name;
        @NotBlank
        private String telNo;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class FindPw {
        @NotBlank
        private String id;
        @NotBlank
        private String name;
        @NotBlank
        private String email;
        @NotBlank
        private String telNo;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class Modify {
        @NotBlank
        private String id;
        private String prePassword;
        private String newPassword;
        private String telNo;
    }
}