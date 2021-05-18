package beathub.api.service;

import beathub.api.exception.DuplicateEmailException;
import beathub.api.exception.DuplicateUsernameException;
import beathub.api.exception.InvalidEmailException;
import beathub.api.model.Account;
import beathub.api.repository.impl.AccountRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;

@Service
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AccountRepositoryImpl repository;
    private final Predicate<String> emailPredicate;

    @Autowired
    public AccountService(AccountRepositoryImpl repository, @Qualifier("email") Predicate<String> emailPredicate) {
        this.repository = repository;
        this.emailPredicate = emailPredicate;
    }

    public List<Account> getAccounts() {
        return repository.getAccounts();
    }

    @Transactional
    public void registerAccount(Account account) {
        checkEmail(account.getEmail());
        checkUsername(account.getUsername());
        repository.registerAccount(account);
    }

    private void checkEmail(String email) {
        try {
            repository.getAccountIdByEmail(email);
            throw new DuplicateEmailException();
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("Email: {} is not used", email);
        }
        if (!emailPredicate.test(email)) {
            throw new InvalidEmailException();
        }
    }

    private void checkUsername(String username) {
        try {
            repository.getAccountIdByUsername(username);
            throw new DuplicateUsernameException();
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("Username: {} is not used", username);
        }
    }
}
