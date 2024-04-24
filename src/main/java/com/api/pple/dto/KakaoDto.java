package com.api.pple.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoDto {
    private String service;
    private String message;
    private String callbackNo;
    private String subject;
    private String mobile;
    private String template;
    private String memberId;
    private String backupMessage;
    private String backupProcessCode;
}