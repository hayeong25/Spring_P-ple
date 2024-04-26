package com.api.pple.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Advertise {
    private String advertiseCode;
    private String advertiseManufacturerCompanyName;
    private String advertiseImage;
    private String advertiseUrl;
    private String advertiseDivision; // 신청, 게시
    private String advertiseState; // 신청, 반려, 광고대기, 광고중, 광고종료, 광고중지
    private String advertiseEnrollmentDate;
}