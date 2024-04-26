package com.api.pple.service;

import com.api.pple.dao.ProductDao;
import com.api.pple.dto.request.ProductRequest;
import com.api.pple.dto.response.ProductResponse;
import com.api.pple.entity.Product;
import com.api.pple.exception.ClientException;
import com.api.pple.utils.ErrorCode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductDao productDao;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final ModelMapper modelMapper = new ModelMapper();

    public List<ProductResponse> getProductList() {
        List<Product> productList = Optional.of(productDao.getProductList())
                                            .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        return productList.stream()
                          .map(m -> new ProductResponse(m.getProductCode(), m.getProductName(), m.getProductType(),
                                  m.getCategory(), m.getProductStockCount(), m.getProductOriginalPrice(),
                                  m.getProductSalePrice(), m.getProductDiscountRatio(), m.getProductUrl(),
                                  m.getProductDisplayYn(), m.getProductEnrollmentDate()))
                          .toList();
    }

    public ProductResponse getProductDetail(String productCode) {
        Product productDetail = Optional.of(productDao.getProductDetail(productCode))
                                        .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (ObjectUtils.isEmpty(productDetail)) {
            throw new ClientException(ErrorCode.SELECT_FAIL);
        }

        return modelMapper.map(productDetail, ProductResponse.class);
    }

    public String registerProduct(ProductRequest request) {
        Product product = Product.builder()
                                 .productName(request.getName())
                                 .productType(request.getType())
                                 .category(request.getCategory())
                                 .productStockCount(request.getStockCount())
                                 .productOriginalPrice(request.getOriginalPrice())
                                 .productSalePrice(request.getSalePrice())
                                 .productDiscountRatio(request.getDiscountRatio())
                                 .productUrl(request.getUrl())
                                 .productDisplayYn(1)
                                 .productEnrollmentDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                                 .build();

        int result = Optional.of(productDao.registerProduct(product))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }

        return product.getProductCode();
    }

    public String deleteProduct(String productCode) {
        int result = Optional.of(productDao.deleteProduct(productCode))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.DELETE_FAIL);
        }

        return productCode;
    }
}