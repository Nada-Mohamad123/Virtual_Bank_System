package com.Virtual_Bank_System.logging.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.Virtual_Bank_System.logging.model.LogEntity;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, UUID> {
}
