package com.api.pple.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {
    private String id;
    @NotBlank
    private String type;
    @NotBlank
    private String status;
    private int point;
    private String getDateTime;
    private String useDateTime;
}