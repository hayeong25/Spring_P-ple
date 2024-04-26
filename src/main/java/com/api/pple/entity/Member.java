package com.api.pple.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Member {
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberType; // 일반회원, 정회원
    private String memberContact;
    private String memberEmail;
    private int memberLevel;
    private String memberBusinessNo;
    private String memberCompanyName;
    private String memberZipcode;
    private String memberAddress;
    private int memberUseYn;
    private String memberEnrollmentDate;
    private String memberDormancyExpectDate;
}