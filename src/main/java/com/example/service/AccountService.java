package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerUser(Account account){
        if(account.getUsername() == null){
            return null;
        }
        if(account.getPassword() == null || account.getPassword().length() < 4){
            return null;
        }
        return accountRepository.save(account);
    }

    public boolean usernameExists(Account account){
        Optional<Account> accountOptional = accountRepository.findByUsername(account.getUsername());
        if(accountOptional.isPresent()){
            return true;
        }
        return false;
    }

    public Account loginUser(Account account){
        Optional<Account> accountOptional = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(accountOptional.isEmpty()){
            return null;
        }
        return accountOptional.get();
    }
}
