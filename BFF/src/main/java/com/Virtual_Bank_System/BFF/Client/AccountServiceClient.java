package com.Virtual_Bank_System.BFF.Client;

import com.Virtual_Bank_System.BFF.DTOs.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name="account-service",url="${account-service.url}")
public interface AccountServiceClient {
    @GetMapping("/users/{userId}/accounts")
    List<AccountDto> getUserAccounts(@PathVariable UUID userId);
}
