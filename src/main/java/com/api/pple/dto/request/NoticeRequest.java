package com.api.pple.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NoticeRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private int fixedYn;
}