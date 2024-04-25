package com.api.pple.service;

import com.api.pple.dao.ProductDao;
import com.api.pple.dto.ProductDto;
import com.api.pple.exception.ClientException;
import com.api.pple.utils.ErrorCode;
import com.api.pple.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductDao productDao;

    public List<ProductDto> getProductList(String accessToken) {
        Token.checkToken(accessToken);
        return productDao.getProductList();
    }

    public ProductDto getProductDetail(String productCode, String accessToken) {
        Token.checkToken(accessToken);

        ProductDto productDetail = productDao.getProductDetail(productCode);

        if (ObjectUtils.isEmpty(productDetail)) {
            throw new ClientException(ErrorCode.SELECT_FAIL);
        }

        return productDetail;
    }

    public String registerProduct(ProductDto request, String accessToken) {
        Token.checkToken(accessToken);

        int result = productDao.registerProduct(request);

        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }

        return request.getCode();
    }

    public String deleteProduct(String productCode, String accessToken) {
        Token.checkToken(accessToken);

        int result = productDao.deleteProduct(productCode);

        if (result < 1) {
            throw new ClientException(ErrorCode.DELETE_FAIL);
        }

        return productCode;
    }
}