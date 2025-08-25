package com.Virtual_Bank_System.Virtual_Bank_System.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data                   // Generates getters, setters, equals, hashCode, toString
@NoArgsConstructor      // Generates no-args constructor
@AllArgsConstructor     // Generates all-args constructor
@Builder                // Enables builder pattern
public class account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
   
    @Column(nullable = false, unique = true)
    private String accountNumber;


    @Enumerated(EnumType.STRING)
    private accountType accountType;  // Use enum instead of String


    @Column(nullable = false)
    private BigDecimal balance;


    @Column(nullable = false)
    private UUID userId;

    
    @Enumerated(EnumType.STRING)
    private accountStatus status;     // Use enum instead of String

    @PrePersist
    public void prePersist() {
        if (this.accountNumber == null || this.accountNumber.isEmpty()) {
            this.accountNumber = generateAccountNumber();
        }

        if (this.balance == null) {
            this.balance = BigDecimal.ZERO; // Default balance
        }

        if (this.status == null) {
            this.status = accountStatus.ACTIVE; // Or PENDING, depending on your logic
        }
    }

    private String generateAccountNumber() {
        // Example: Generate a random 12-digit number
        return String.valueOf(100000000000L + (long)(Math.random() * 900000000000L));
    }

}
