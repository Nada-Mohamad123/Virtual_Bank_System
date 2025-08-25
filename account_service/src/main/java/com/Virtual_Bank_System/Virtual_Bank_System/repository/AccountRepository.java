package com.Virtual_Bank_System.Virtual_Bank_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Virtual_Bank_System.Virtual_Bank_System.model.account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;



public interface AccountRepository extends JpaRepository<account, UUID> {
    @Query("SELECT a FROM account a WHERE a.status= 'ACTIVE' AND a.lastTransactionAt < :cutoffTime")
    List<account> findStaleAccounts(@Param("cutoffTime") LocalDateTime cutoffTime);
      List<account> findByUserId(UUID userId);
    java.util.Optional<account> findByAccountNumber(String accountNumber);
}