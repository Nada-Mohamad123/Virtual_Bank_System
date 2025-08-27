package com.Virtual_Bank_System.BFF.Client;

import com.Virtual_Bank_System.BFF.DTOs.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URL;
import java.util.UUID;

@FeignClient(name="user-service", url="${user-service.url}")
public interface UserServiceClient {
   @GetMapping("/users/{userId}/profile")
   UserDto getUserProfile(@PathVariable UUID userId);
}
