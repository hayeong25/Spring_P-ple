package com.api.pple.controller;

import com.api.pple.dto.request.ProductRequest;
import com.api.pple.dto.response.ProductResponse;
import com.api.pple.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    /*
    * 마켓 상품 리스트
    */
    @PostMapping("/list")
    public ResponseEntity<List<ProductResponse>> getProductList() {
        return ResponseEntity.ok(productService.getProductList());
    }

    /*
    * 마켓 상품 상세
    */
    @GetMapping("/detail/{productCode}")
    public ResponseEntity<ProductResponse> getProductDetail(@PathVariable("productCode")String productCode) {
        return ResponseEntity.ok(productService.getProductDetail(productCode));
    }
    
    /*
    * 상품 등록 
    */
    @PostMapping("/register")
    public ResponseEntity<String> registerProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(productService.registerProduct(request));
    }

    /*
    * 상품 삭제
    */
    @PutMapping("/delete")
    public ResponseEntity<String> deleteProduct(String productCode) {
        return ResponseEntity.ok(productService.deleteProduct(productCode));
    }
}