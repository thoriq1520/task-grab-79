package com.example.caseGrab.controller;

import com.example.caseGrab.dto.req.ProductRequestDTO;
import com.example.caseGrab.dto.res.BodyResponseDTO;
import com.example.caseGrab.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<BodyResponseDTO> getProducts(@ModelAttribute ProductRequestDTO requestDTO) {
        return productService.getProducts(requestDTO);
    }
}