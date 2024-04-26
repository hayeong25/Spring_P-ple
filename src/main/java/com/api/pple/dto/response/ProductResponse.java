package com.api.pple.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {
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