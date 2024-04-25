package com.api.pple.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdDto {
    private String code;
    @NotBlank
    private String companyName;
    @NotBlank
    private String image;
    @NotBlank
    private String url;
    private String division; // 신청, 게시
    private String status; // 신청, 반려, 광고 대기, 광고 중, 광고 종료, 광고 중지
    private String enrollmentDateTime;
}