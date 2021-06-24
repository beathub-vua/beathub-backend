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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class AccountService implements UserDetailsService {

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

    public void registerAccount(Account account) {
        checkEmail(account.getEmail());
        checkUsername(account.getUsername());
        repository.registerUser(account);
    }

    @Override
    public Account loadUserByUsername(String username) {
        List<Account> accounts = repository.loadAccountByUsername(username);

        if (accounts.isEmpty()) {
            log.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
        return accounts.get(0);
    }

    private void checkEmail(String email) {
        if (repository.getAccountIdByEmail(email) != null) {
            throw new DuplicateUsernameException();
        }

        if (!emailPredicate.test(email)) {
            throw new InvalidEmailException();
        }
    }

    private void checkUsername(String username) {
        if (repository.getAccountIdByUsername(username) != null) {
            throw new DuplicateUsernameException();
        }
    }
}
