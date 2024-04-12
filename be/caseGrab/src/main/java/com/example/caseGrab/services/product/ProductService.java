package com.example.caseGrab.services.product;

import java.util.List;

import com.example.caseGrab.dto.req.ProductRequestDTO;
import com.example.caseGrab.dto.res.BodyResponseDTO;
import com.example.caseGrab.dto.res.ProductResponseDTO;
import com.example.caseGrab.repositories.olap.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<BodyResponseDTO> getProducts(ProductRequestDTO request) {
        String search = request.getSearch() == null ? "" : request.getSearch();

        List<ProductResponseDTO> products = productRepository.findAllProduct(search);

        BodyResponseDTO bodyResponseDTO = BodyResponseDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .message("Successfully retrieved the list of products")
                .data(products)
                .build();

        return ResponseEntity.ok().body(bodyResponseDTO);
    }
}
