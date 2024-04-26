package com.api.pple.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberRequest {
    @Getter
    @Setter
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
        private String zipcode;
        @NotBlank
        private String address;
    }

    @Getter
    @AllArgsConstructor
    public static class Login {
        @NotBlank
        private String id;
        @NotBlank
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class FindId {
        @NotBlank
        private String name;
        @NotBlank
        private String telNo;
    }

    @Getter
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
    @AllArgsConstructor
    public static class Business {
        @NotBlank
        private String businessNo;
        @NotBlank
        private String companyName;
    }

    @Getter
    @AllArgsConstructor
    public static class Modify {
        @NotBlank
        private String id;
        private String prePassword;
        private String newPassword;
        private String telNo;
    }

    @Getter
    @AllArgsConstructor
    public static class Point {
        @NotBlank
        private String id;
        @NotBlank
        private String type;
        @NotBlank
        private String status;
        private int point;
        @NotBlank
        private String content;
    }
}