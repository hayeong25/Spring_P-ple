package com.api.pple.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {
    private String id;
    private String writer;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private int fixedYn;
    private String enrollmentDateTime;
}