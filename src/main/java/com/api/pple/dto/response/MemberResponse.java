package com.api.pple.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MemberResponse {
    @Getter
    @AllArgsConstructor
    public static class Login {
        private String id;
        private String name;
        private String accessToken;
    }

    @Getter
    @AllArgsConstructor
    public static class Point {
        private String id;
        private String type;
        private String division;
        private int point;
        private String content;
        private String enrollmentDate;
        private String useDate;
    }

    @Getter
    @AllArgsConstructor
    public static class Sales {
        private int sales;
        private int totalSales;
    }
}