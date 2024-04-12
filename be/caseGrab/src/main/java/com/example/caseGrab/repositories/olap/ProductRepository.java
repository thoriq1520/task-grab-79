package com.example.caseGrab.repositories.olap;

import java.util.List;
import java.util.UUID;

import com.example.caseGrab.dto.res.ProductResponseDTO;
import com.example.caseGrab.model.olap.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface ProductRepository extends JpaRepository<Products, UUID> {
    @Query("SELECT NEW com.example.caseGrab.dto.res.ProductResponseDTO("+
            "p.productId, p.name, p.image, p.price, COALESCE(COUNT(t), 0)) " +
            "FROM Products p " +
            "LEFT JOIN p.transactions t ON t.status = 'completed' " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "GROUP BY p.productId " +
            "ORDER BY COALESCE(COUNT(t), 0) DESC")
    List<ProductResponseDTO> findAllProduct(String search);
}
