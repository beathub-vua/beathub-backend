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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class AccountService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AccountRepositoryImpl repository;
    private final Predicate<String> emailPredicate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepositoryImpl repository, @Qualifier("email") Predicate<String> emailPredicate, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.emailPredicate = emailPredicate;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Account> getAccounts() {
        return repository.getAccounts();
    }

    public void registerAccount(Account account) {
        checkEmail(account.getEmail());
        checkUsername(account.getUsername());
        account.setPassword(passwordEncoder.encode(account.getPassword()));
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
        if (repository.checkIfEmailIsTaken(email)) {
            throw new DuplicateEmailException();
        }

        if (!emailPredicate.test(email)) {
            throw new InvalidEmailException();
        }
    }

    private void checkUsername(String username) {
        if (repository.checkIfUsernameIsTaken(username)) {
            throw new DuplicateUsernameException();
        }
    }
}
