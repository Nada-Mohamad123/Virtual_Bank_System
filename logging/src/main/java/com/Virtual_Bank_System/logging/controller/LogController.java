package com.Virtual_Bank_System.logging.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogProducer logProducer;

    @PostMapping
    public String sendLog(@RequestParam String message, @RequestParam String type) {
        try {
            logProducer.sendLog(message, type);
            return "Log sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending log";
        }
    }
}
