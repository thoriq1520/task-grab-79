package com.example.caseGrab.services.transaction;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.caseGrab.dto.req.CreateTransactionRequestDTO;
import com.example.caseGrab.dto.req.UpdateTransactionRequestDTO;
import com.example.caseGrab.dto.res.BodyResponseDTO;
import com.example.caseGrab.model.oltp.OltpTransactions;
import com.example.caseGrab.repositories.oltp.OltpTransactionRepository;
import com.example.caseGrab.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TransactionService {
    @Autowired
    OltpTransactionRepository oltpTransactionRepository;

    @Autowired
    private KafkaTemplate<String, OltpTransactions> kafkaTemplate;

    public ResponseEntity<BodyResponseDTO> createTransaction(CreateTransactionRequestDTO requestDTO) {
        UUID userId = JwtUtil.getCurrentUser().getUserId();
        UUID transactionId = UUID.randomUUID();
        UUID productId = UUID.fromString(requestDTO.getProductId());
        LocalDateTime time = LocalDateTime.now();
        String status = "ongoing";

        OltpTransactions oltpTransactions = new OltpTransactions(transactionId, userId, productId, status, time, time);
        oltpTransactionRepository.save(oltpTransactions);
        kafkaTemplate.send("transaction", oltpTransactions);

        BodyResponseDTO bodyResponseDTO = BodyResponseDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .message("Successfully Create Transaction")
                .data(null)
                .build();

        return ResponseEntity.ok().body(bodyResponseDTO);
    }

    public ResponseEntity<BodyResponseDTO> updateTransaction(UUID transactionId, UpdateTransactionRequestDTO requestDTO) {
        Optional<OltpTransactions> optionalTransaction = oltpTransactionRepository.findById(transactionId);

        if (optionalTransaction.isPresent()) {
            OltpTransactions existingTransaction = optionalTransaction.get();
            // Update transaction details
            existingTransaction.setStatus(requestDTO.getStatus());

            oltpTransactionRepository.save(existingTransaction);

            BodyResponseDTO bodyResponseDTO = BodyResponseDTO.builder()
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK.name())
                    .message("Transaction updated successfully")
                    .data(existingTransaction)
                    .build();

            return ResponseEntity.ok().body(bodyResponseDTO);
        } else {
            BodyResponseDTO bodyResponseDTO = BodyResponseDTO.builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message("Transaction not found")
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bodyResponseDTO);
        }
    }

    public ResponseEntity<BodyResponseDTO> getTransactions() {
        List<OltpTransactions> oltpTransactions = oltpTransactionRepository.findAll();

        BodyResponseDTO bodyResponseDTO = BodyResponseDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .message("Successfully retrieved the list of products")
                .data(oltpTransactions)
                .build();

        return ResponseEntity.ok().body(bodyResponseDTO);
    }

    public ResponseEntity<BodyResponseDTO> getTransactionById(UUID transactionId) {
        Optional<OltpTransactions> optionalTransaction = oltpTransactionRepository.findById(transactionId);

        if (optionalTransaction.isPresent()) {
            OltpTransactions transaction = optionalTransaction.get();
            BodyResponseDTO bodyResponseDTO = BodyResponseDTO.builder()
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK.name())
                    .message("Transaction found")
                    .data(transaction)
                    .build();
            return ResponseEntity.ok().body(bodyResponseDTO);
        } else {
            BodyResponseDTO bodyResponseDTO = BodyResponseDTO.builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message("Transaction not found")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bodyResponseDTO);
        }
    }

    public ResponseEntity<BodyResponseDTO> getOngoingTransactionsByPassengerId(UUID passengerId) {
        List<OltpTransactions> ongoingTransactions = oltpTransactionRepository.findByUserIdAndStatus(passengerId, "ongoing");

        BodyResponseDTO bodyResponseDTO;
        if (!ongoingTransactions.isEmpty()) {
            bodyResponseDTO = BodyResponseDTO.builder()
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK.name())
                    .message("Ongoing transactions found for passenger with ID: " + passengerId)
                    .data(ongoingTransactions)
                    .build();
            return ResponseEntity.ok().body(bodyResponseDTO);
        } else {
            bodyResponseDTO = BodyResponseDTO.builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message("No ongoing transactions found for passenger with ID: " + passengerId)
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bodyResponseDTO);
        }
    }

    public ResponseEntity<BodyResponseDTO> getOrderStatistics() {
        long totalOrders = oltpTransactionRepository.count();

        BodyResponseDTO bodyResponseDTO = BodyResponseDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .message("Order statistics retrieved successfully")
                .data("Total Orders: " + totalOrders)
                .build();

        return ResponseEntity.ok().body(bodyResponseDTO);
    }
}