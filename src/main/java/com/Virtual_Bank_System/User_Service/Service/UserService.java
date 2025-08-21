package com.Virtual_Bank_System.User_Service.Service;

import com.Virtual_Bank_System.User_Service.DTOs.LoginRequestDTO;
import com.Virtual_Bank_System.User_Service.DTOs.RegisterRequestDTO;
import com.Virtual_Bank_System.User_Service.Exception.BaseException;
import com.Virtual_Bank_System.User_Service.Model.User;
import com.Virtual_Bank_System.User_Service.Repository.UserRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;

     public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
         this.userRepository = userRepository;
         this.passwordEncoder = passwordEncoder;
     }
     public User RegisterUser(RegisterRequestDTO request){
         if(userRepository.existsByUserNameOrEmail(request.getUserName(), request.getEmail())){
             throw new BaseException.UserExistsException("Username or email already exists.");
         }
         User user = new User(
                request.getUserName(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName()
         );
         return userRepository.save(user);
     }

     public User LoginUser(LoginRequestDTO request){
         User user = userRepository.findByUserName(request.getUserName())
                 .orElseThrow(()->new BaseException.InvalidCredentialsException("Invalid username or password."));
         if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
             throw new BaseException.InvalidCredentialsException("Invalid username or password.");
         }
         return user;
     }
     public User getUserProfileById(UUID userId){
         User user = userRepository.findById(userId)
                 .orElseThrow(()->new BaseException.UserNotFoundException("User with ID "+userId+" not found."));
         return user;
     }

}
