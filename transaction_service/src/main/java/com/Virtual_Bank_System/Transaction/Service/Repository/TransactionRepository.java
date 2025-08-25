package com.Virtual_Bank_System.Transaction.Service.Repository;

import com.Virtual_Bank_System.Transaction.Service.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.JavaBean;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
