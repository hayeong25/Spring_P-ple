package com.api.pple.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProductDto {
    private String code;
    @NotBlank
    private String category;
    @NotBlank
    private String brand;
    @NotBlank
    private String name;
    private String url;
    private int stock;
    private int price;
    private int salePrice;
    private int saleRatio;
}