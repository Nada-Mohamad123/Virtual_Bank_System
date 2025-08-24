package com.Virtual_Bank_System.User_Service.Repository;

import com.Virtual_Bank_System.User_Service.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUserNameOrEmail(String userName, String email);

    Optional<User> findByUserName(String userName);
}
