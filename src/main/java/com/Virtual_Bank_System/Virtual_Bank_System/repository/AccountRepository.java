package com.Virtual_Bank_System.Virtual_Bank_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Virtual_Bank_System.Virtual_Bank_System.model.account;

import java.util.List;
import java.util.UUID;



public interface AccountRepository extends JpaRepository<account, UUID> {

      List<account> findByUserId(UUID userId);
    java.util.Optional<account> findByAccountNumber(String accountNumber);
}