package com.api.pple.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {
    private String id;
    private String type;
    private String status;
    private int point;
    private String getDateTime;
    private String useDateTime;
}