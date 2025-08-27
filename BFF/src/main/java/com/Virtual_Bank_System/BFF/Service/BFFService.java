package com.Virtual_Bank_System.BFF.Service;

import com.Virtual_Bank_System.BFF.Client.AccountServiceClient;
import com.Virtual_Bank_System.BFF.Client.TransactionServiceClient;
import com.Virtual_Bank_System.BFF.Client.UserServiceClient;
import com.Virtual_Bank_System.BFF.DTOs.*;
import com.Virtual_Bank_System.BFF.Mapper.DashboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BFFService {
    private final UserServiceClient userClient;
    private final AccountServiceClient accountClient;
    private final TransactionServiceClient transactionClient;

    private final DashboardMapper dashboardMapper;
    public DashboardResponse getDashboard(UUID userId){
        UserDto user = userClient.getUserProfile(userId);
        List<AccountDto>accounts = accountClient.getUserAccounts(userId);
        List<AccountWithTrans>accWithTrans = accounts.stream()
                .map(acc -> new AccountWithTrans(acc,transactionClient.getAccountTransactions(acc.getAccountId())))
                .toList();
        DashboardAggregate aggregate = new DashboardAggregate(user,accWithTrans);
        return dashboardMapper.toResponse(aggregate);
    }
}
