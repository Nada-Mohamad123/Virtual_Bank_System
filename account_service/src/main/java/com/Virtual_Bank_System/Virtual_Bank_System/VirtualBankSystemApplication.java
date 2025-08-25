package com.Virtual_Bank_System.Virtual_Bank_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VirtualBankSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualBankSystemApplication.class, args);
	}

}
