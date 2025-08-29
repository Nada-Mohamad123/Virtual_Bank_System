package com.Virtual_Bank_System.Virtual_Bank_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Virtual_Bank_System.Virtual_Bank_System.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;



public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query("SELECT a FROM Account  a WHERE a.status= 'ACTIVE' AND a.lastTransactionAt < :cutoffTime")
    List<Account> findStaleAccounts(@Param("cutoffTime") LocalDateTime cutoffTime);
      List<Account> findByUserId(UUID userId);
    java.util.Optional<Account> findByAccountNumber(String accountNumber);
}