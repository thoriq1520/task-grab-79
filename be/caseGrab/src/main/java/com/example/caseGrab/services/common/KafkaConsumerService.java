package com.example.caseGrab.services.common;

import com.example.caseGrab.model.olap.OlapTransactions;
import com.example.caseGrab.model.olap.Products;
import com.example.caseGrab.model.olap.Users;
import com.example.caseGrab.model.oltp.OltpTransactions;
import com.example.caseGrab.repositories.olap.OlapTransactionRepository;
import com.example.caseGrab.repositories.olap.ProductRepository;
import com.example.caseGrab.repositories.olap.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;


@Service
@Transactional
public class KafkaConsumerService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OlapTransactionRepository olapTransactionRepository;

    @KafkaListener(topics = "transaction", groupId = "order")
    public void consume(@Valid OltpTransactions oltpTransactions) {
        Users user = userRepository.findById(oltpTransactions.getUserId()).orElse(null);
        Products product = productRepository.findById(oltpTransactions.getProductId()).orElse(null);

        OlapTransactions olapTransactions = OlapTransactions.builder()
                .transactionId(oltpTransactions.getTransactionId())
                .user(user)
                .product(product)
                .status(oltpTransactions.getStatus())
                .createdAt(oltpTransactions.getCreatedAt())
                .updatedAt(oltpTransactions.getUpdatedAt())
                .build();

        olapTransactionRepository.save(olapTransactions);
    }
}