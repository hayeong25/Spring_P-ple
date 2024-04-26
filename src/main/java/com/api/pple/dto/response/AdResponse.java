package com.api.pple.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdResponse {
    private String code;
    private String companyName;
    private String image;
    private String url;
    private String division;
    private String state;
    private String enrollmentDate;
}