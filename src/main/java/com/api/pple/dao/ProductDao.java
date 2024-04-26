package com.api.pple.dao;

import com.api.pple.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {
    List<Product> getProductList();

    Product getProductDetail(String productCode);

    int registerProduct(Product request);

    int deleteProduct(String productCode);
}