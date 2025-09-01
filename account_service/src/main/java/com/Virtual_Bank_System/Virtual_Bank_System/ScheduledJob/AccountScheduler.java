package com.Virtual_Bank_System.Virtual_Bank_System.ScheduledJob;

import com.Virtual_Bank_System.Virtual_Bank_System.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AccountScheduler {
    @Autowired
    AccountService accountService;
    //job run every one hour
    //60000 ms = 1 minute
    //3600000 ms = 1 hour
    @Scheduled(fixedRate = 3600000)
    public void RunJob(){
        accountService.InactiveAccounts();
    }
}
