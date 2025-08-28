package com.Virtual_Bank_System.BFF.Controller;

import com.Virtual_Bank_System.BFF.DTOs.DashboardResponse;
import com.Virtual_Bank_System.BFF.DTOs.ErrorResponse;
import com.Virtual_Bank_System.BFF.Service.BFFService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bff/dashboard")
public class DashboardController {

    private final BFFService bffService;

    public DashboardController(BFFService bffService) {
        this.bffService = bffService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getDashboard(@PathVariable UUID userId) {
        DashboardResponse dashboard = bffService.getDashboard(userId);

        if (dashboard == null) {

            ErrorResponse errorResponse = new ErrorResponse(
                    404,
                    "Not Found",
                    "User not found " + userId
            );
            return ResponseEntity.status(404).body(errorResponse);
        }

        return ResponseEntity.ok(dashboard);
    }
}
