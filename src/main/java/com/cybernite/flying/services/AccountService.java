package com.cybernite.flying.services;

import com.cybernite.flying.common.Constants;
import com.cybernite.flying.dto.LoginData;
import com.cybernite.flying.entities.Account;
import com.cybernite.flying.exeptions.FlyingBudRequestExeption;
import com.cybernite.flying.exeptions.FlyingNotFoundExeption;
import com.cybernite.flying.repo.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    public LoginData readAccountByEmail(String email) throws FlyingNotFoundExeption {
        Account account = getAccountByEmail(email);
        LoginData response = mapper.map(account, LoginData.class);
        return response;
    }

    public LoginData createAccount(LoginData request) throws FlyingBudRequestExeption {
        if (!accountRepository.findById(request.getEmail()).isEmpty()) {
            throw new FlyingBudRequestExeption(String.format("user %s already registered", request.getEmail()));
        }
        Account account = mapper.map(request, Account.class);
        account.setRole("ROLE_" + Constants.ROLE.USER);
        LoginData response = mapper.map(accountRepository.save(account), LoginData.class);
        return response;
    }

    public String getRoleAccount(String email) {
        return accountRepository.findById(email).orElse(null).getRole();
    }

    public Account getAccountByEmail(String email) throws FlyingNotFoundExeption {
        Account account = accountRepository.findById(email).orElse(null);
        if (account == null) {
            throw new FlyingNotFoundExeption(String.format("Wrong name or password for %s", email));
        }
        return account;
    }
}
