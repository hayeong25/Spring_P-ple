package com.api.pple.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Product {
    private String productCode;
    private String productName;
    private String productType;
    private String category;
    private int productStockCount;
    private int productOriginalPrice;
    private int productSalePrice;
    private int productDiscountRatio;
    private String productUrl;
    private int productDisplayYn;
    private String productEnrollmentDate;
}