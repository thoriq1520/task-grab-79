package com.example.caseGrab.controller;

import com.example.caseGrab.dto.req.CreateTransactionRequestDTO;
import com.example.caseGrab.dto.req.UpdateTransactionRequestDTO;
import com.example.caseGrab.dto.res.BodyResponseDTO;
import com.example.caseGrab.services.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;


    //Create API Create Order
    @PostMapping("/create")
    public ResponseEntity<BodyResponseDTO> createTransaction(@Valid @RequestBody CreateTransactionRequestDTO requestDTO) {
        return transactionService.createTransaction(requestDTO);
    }

    //Create API Update Order
    @PutMapping("/update/{transactionId}")
    public ResponseEntity<BodyResponseDTO> updateTransaction(@PathVariable UUID transactionId, @RequestBody UpdateTransactionRequestDTO requestDTO) {
        return transactionService.updateTransaction(transactionId, requestDTO);
    }

    //Create API Get Order byId
    @GetMapping("/{transactionId}")
    public ResponseEntity<BodyResponseDTO> getTransactionById(@PathVariable UUID transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    //Create API Get ongoing orders by passenger Id
    @GetMapping("/ongoing/{passengerId}")
    public ResponseEntity<BodyResponseDTO> getOngoingTransactionsByPassengerId(@PathVariable UUID passengerId) {
        return transactionService.getOngoingTransactionsByPassengerId(passengerId);
    }

    //Create API Get historical orders by various condition
    @GetMapping("")
    public ResponseEntity<BodyResponseDTO> getTransaction() {
        return transactionService.getTransactions();
    }


    //Create API Get order statistics (ex. get the number of orders)
    @GetMapping("/statistics")
    public ResponseEntity<BodyResponseDTO> getOrderStatistics() {
        return transactionService.getOrderStatistics();
    }


}