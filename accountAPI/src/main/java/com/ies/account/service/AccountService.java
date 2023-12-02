package com.ies.account.service;

import com.ies.account.dto.AccountDto;
import com.ies.account.dto.AccountDtoConverter;
import com.ies.account.dto.CreateAccountRequest;
import com.ies.account.model.Account;
import com.ies.account.model.Customer;
import com.ies.account.model.Transaction;
import com.ies.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter converter;

    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService, AccountDtoConverter converter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.converter = converter;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest){
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account(customer,
                createAccountRequest.getInitialCredit(),
                LocalDateTime.now());

        if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0){
            //Transaction transaction = transactionService.initiateMoney(account, createAccountRequest.getInitialCredit());
            Transaction transaction =  new Transaction(createAccountRequest.getInitialCredit(), account);
            account.getTransaction().add(transaction);
        }

        return converter.convert(accountRepository.save(account));
    }
}
