package com.Virtual_Bank_System.Transaction.Service.Model;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {
@Id
@GeneratedValue(generator = "UUID")
@Column(updatable = false, nullable = false)
    private UUID transactionId;
    @Column(nullable = false)
    private UUID fromAccountId;
    @Column(nullable = false)
    private UUID toAccountId;
    @Column(nullable = false)
    private double amount;
    @Column(length = 255)
    private String description;
    @Column(nullable = false)
    private Instant timestamp;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus;
    public enum TransactionStatus{
        Initiated,
        Success,
        Failed
    }
    public enum DeliveryStatus{
        SENT,
        DELIVERED
    }
}
