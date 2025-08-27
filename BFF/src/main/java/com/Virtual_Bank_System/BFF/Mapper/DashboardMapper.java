package com.Virtual_Bank_System.BFF.Mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.Virtual_Bank_System.BFF.DTOs.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface DashboardMapper {
    @Mapping(target = "userId" ,source = "user.userId")
    @Mapping(target="username",source="user.userName")
    @Mapping(target="email",source="user.email")
    @Mapping(target="firstName",source="user.firstName")
    @Mapping(target="lastName",source="user.lastName")
    @Mapping(target = "accounts", source = "accounts")
    DashboardResponse toResponse(DashboardAggregate aggregate);

    @Mapping(target = "accountId",source="account.accountId")
    @Mapping(target="accountNumber", source="account.accountNumber")
    @Mapping(target="type",source="account.type")
    @Mapping(target = "balance", source="account.balance")
    @Mapping(target = "transactions", source = "transactions")
    AccWithTransResponse toResponse(AccountWithTrans accountWthTrans);
    List<AccWithTransResponse> toResponseList(List<AccountWithTrans> accounts);
    TransactionResponse toResponse(TransactionDto transaction);

}
