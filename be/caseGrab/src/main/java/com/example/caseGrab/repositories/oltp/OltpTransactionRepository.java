package com.example.caseGrab.repositories.oltp;

import java.util.List;
import java.util.UUID;

import com.example.caseGrab.model.oltp.OltpTransactions;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OltpTransactionRepository extends MongoRepository<OltpTransactions, UUID> {
    List<OltpTransactions> findByUserIdAndStatus(UUID userId, String status);

}