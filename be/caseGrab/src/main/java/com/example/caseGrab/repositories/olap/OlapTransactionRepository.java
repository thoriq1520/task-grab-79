package com.example.caseGrab.repositories.olap;

import java.util.UUID;

import com.example.caseGrab.model.olap.OlapTransactions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OlapTransactionRepository extends JpaRepository<OlapTransactions, UUID> {

}