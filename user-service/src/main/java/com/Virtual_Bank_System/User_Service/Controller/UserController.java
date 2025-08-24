package com.Virtual_Bank_System.User_Service.Controller;

import com.Virtual_Bank_System.User_Service.DTOs.*;
import com.Virtual_Bank_System.User_Service.Model.User;
import com.Virtual_Bank_System.User_Service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
//    @GetMapping("/")
//    public String test(){
//        return "test";
//    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO>registerUser(@RequestBody RegisterRequestDTO registerRequest){
        User user = userService.RegisterUser(registerRequest);
        RegisterResponseDTO response = new RegisterResponseDTO(
                user.getUserId(),
                user.getUserName(),
                "User registered successfully."
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequest){
        User user = userService.LoginUser(loginRequest);
        LoginResponseDTO response = new LoginResponseDTO(
                user.getUserId(),
                user.getUserName()
        );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{userId}/profile")
    public ResponseEntity<ProfileResponseDTO>getUserProfile(@PathVariable UUID userId){
           User user = userService.getUserProfileById(userId);
           ProfileResponseDTO response = new ProfileResponseDTO(
                   user.getUserId(),
                   user.getUserName(),
                   user.getEmail(),
                   user.getFirstName(),
                   user.getLastName()
           );
           return ResponseEntity.ok(response);
    }
}
