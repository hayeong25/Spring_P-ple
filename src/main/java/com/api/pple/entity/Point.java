package com.api.pple.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Point {
    private String memberId;
    private String memberPointType;
    private String memberPointDivision;
    private int memberPoint;
    private String memberPointGetContent;
    private String memberPointEnrollmentDate;
    private String memberPointUseDate;
}