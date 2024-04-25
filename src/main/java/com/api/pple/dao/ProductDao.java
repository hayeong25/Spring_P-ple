package com.api.pple.dao;

import com.api.pple.dto.ProductDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {
    List<ProductDto> getProductList();

    ProductDto getProductDetail(String productCode);

    int registerProduct(ProductDto request);

    int deleteProduct(String productCode);
}