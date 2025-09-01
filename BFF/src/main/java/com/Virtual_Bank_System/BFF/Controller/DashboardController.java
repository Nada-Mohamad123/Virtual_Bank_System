package com.Virtual_Bank_System.BFF.Controller;

import com.Virtual_Bank_System.BFF.DTOs.DashboardResponse;
import com.Virtual_Bank_System.BFF.DTOs.ErrorResponse;
import com.Virtual_Bank_System.BFF.Service.BFFService;
import com.Virtual_Bank_System.BFF.Service.LogProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bff/dashboard")
public class DashboardController {

    private final BFFService bffService;
    private final LogProducerService logProducer;

    public DashboardController(BFFService bffService, LogProducerService logProducer) {
        this.bffService = bffService;
        this.logProducer = logProducer;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getDashboard(@PathVariable UUID userId) {
        logProducer.sendLog("GET /bff/dashboard/" + userId + " - Request received", "Request");
        try {
            DashboardResponse dashboard = bffService.getDashboard(userId);

            if (dashboard == null) {
                ErrorResponse errorResponse = new ErrorResponse(
                        404,
                        "Not Found",
                        "User not found " + userId
                );
                logProducer.sendLog("Dashboard not found for userId=" + userId, "Error");
                return ResponseEntity.status(404).body(errorResponse);
            }

            logProducer.sendLog(dashboard, "Response");
            return ResponseEntity.ok(dashboard);

        } catch (Exception e) {
            logProducer.sendLog("Failed to fetch dashboard for userId=" + userId + ": " + e.getMessage(), "Error");
            throw e;
        }
    }
}
