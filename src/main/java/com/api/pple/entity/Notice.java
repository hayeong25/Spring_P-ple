package com.api.pple.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Notice {
    private String noticeId;
    private String noticeTitle;
    private String noticeContent;
    private int noticeUseYn;
    private int noticeFixedYn;
    private String noticeEnrollmentDate;
}