package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account account)
    {
        return accountRepository.save(account);
    }

    public Account findByUsername(String username){
        Optional<Account> account = accountRepository.findByUsername(username);
        if(account.isPresent()){
            return account.get();
        }
        else
        {
            return null;
        }
    }

    public Account findById(Integer id)
    {
        Optional<Account> account = accountRepository.findById(id);
        if(account.isPresent()){
            return account.get();
        }
        else
        {
            return null;
        }
    }

    public Account login(Account account)
    {
        Optional<Account> optionalAccount;
        optionalAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(optionalAccount.isPresent())
        {
            return optionalAccount.get();
        }
        return null;
    }

}
