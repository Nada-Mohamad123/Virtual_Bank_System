package com.Virtual_Bank_System.User_Service.Controller;

import com.Virtual_Bank_System.User_Service.DTOs.*;
import com.Virtual_Bank_System.User_Service.Model.User;
import com.Virtual_Bank_System.User_Service.Service.LogProducerService;
import com.Virtual_Bank_System.User_Service.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final LogProducerService logProducer;
    public UserController(UserService userService, LogProducerService logProducer){
        this.userService = userService;
        this.logProducer = logProducer;
    }
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/")
    public String logging(){
        logProducer.sendLog("GET /users/", "Request");
        String message = "User Service is running.";
        logProducer.sendLog("{\"message\": \"" + message + "\"}", "Response");
        return "User Service is running.";
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO>registerUser(@RequestBody RegisterRequestDTO registerRequest){
        logProducer.sendLog(registerRequest, "Request");
        User user = userService.RegisterUser(registerRequest);
        RegisterResponseDTO response = new RegisterResponseDTO(
                user.getUserId(),
                user.getUserName(),
                "User registered successfully."
        );
        logProducer.sendLog(response, "Request");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequest){
        logProducer.sendLog(loginRequest, "Request");
        User user = userService.LoginUser(loginRequest);
        LoginResponseDTO response = new LoginResponseDTO(
                user.getUserId(),
                user.getUserName()
        );
       logProducer.sendLog(response, "Response");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{userId}/profile")
    public ResponseEntity<ProfileResponseDTO>getUserProfile(@PathVariable UUID userId){
        logProducer.sendLog("GET /users/" + userId + "/profile - Request received", "Request");
        User user = userService.getUserProfileById(userId);
           ProfileResponseDTO response = new ProfileResponseDTO(
                   user.getUserId(),
                   user.getUserName(),
                   user.getEmail(),
                   user.getFirstName(),
                   user.getLastName()
           );
        logProducer.sendLog(response, "Response");
           return ResponseEntity.ok(response);
    }

}
