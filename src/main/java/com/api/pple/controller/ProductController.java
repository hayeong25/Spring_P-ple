package com.api.pple.controller;

import com.api.pple.dto.ProductDto;
import com.api.pple.service.ProductService;
import com.api.pple.utils.Token;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    /*
    * 마켓 상품 리스트
    */
    @PostMapping("/list")
    public ResponseEntity<List<ProductDto>> getProductList(HttpServletRequest servletRequest) {
        log.info("ProductController getProductList Header : {}", servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(productService.getProductList(accessToken));
    }

    /*
    * 마켓 상품 상세
    */
    @GetMapping("/detail/{productCode}")
    public ResponseEntity<ProductDto> getProductDetail(@PathVariable("productCode")String productCode, HttpServletRequest servletRequest) {
        log.info("ProductController getProductDetail productCode : {}, Header : {}", productCode, servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(productService.getProductDetail(productCode, accessToken));
    }
    
    /*
    * 상품 등록 
    */
    @PostMapping("/register")
    public ResponseEntity<String> registerProduct(@RequestBody @Valid ProductDto request, HttpServletRequest servletRequest) {
        log.info("ProductController registerProduct requestBody : {}, Header : {}", request, servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(productService.registerProduct(request, accessToken));
    }

    /*
    * 상품 삭제
    */
    @PutMapping("/delete")
    public ResponseEntity<String> deleteProduct(String productCode, HttpServletRequest servletRequest) {
        log.info("ProductController deleteProduct productCode : {}, Header : {}", productCode, servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(productService.deleteProduct(productCode, accessToken));
    }
}