package com.api.pple.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeResponse {
    private String id;
    private String title;
    private String content;
    private int useYn;
    private int fixedYn;
    private String enrollmentDate;
}