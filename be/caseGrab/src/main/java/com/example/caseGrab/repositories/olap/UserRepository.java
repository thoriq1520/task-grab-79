package com.example.caseGrab.repositories.olap;

import java.util.UUID;

import com.example.caseGrab.model.olap.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<Users, UUID> {
    Boolean existsByUsername(String username);

    Users findByUsername(String username);
}