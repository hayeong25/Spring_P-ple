package com.api.pple.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class ProductRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String type;
    @NotBlank
    private String category;
    @Min(0)
    private int stockCount;
    @Min(1)
    private int originalPrice;
    @Min(1)
    private int salePrice;
    @Min(1)
    private int discountRatio;
    @NotBlank
    private String url;
}