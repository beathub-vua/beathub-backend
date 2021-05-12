package beathub.api.service;

import beathub.api.model.Account;
import beathub.api.repository.impl.AccountRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepositoryImpl repository;

    @Autowired
    public AccountService(AccountRepositoryImpl repository) {
        this.repository = repository;
    }

    public List<Account> getUsers() {
        return repository.getAccounts();
    }
}
