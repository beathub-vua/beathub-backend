package beathub.api.controller;

import beathub.api.exception.DuplicateEmailException;
import beathub.api.exception.InvalidEmailException;
import beathub.api.exception.DuplicateUsernameException;
import beathub.api.model.Account;
import beathub.api.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public ResponseEntity<Object> getAccounts() {
        List<Account> accounts;
        log.info("[START] Get all accounts");
        try {
            accounts = accountService.getAccounts();
        } catch (Exception e) {
            log.error("[ERROR] Get all accounts with error: ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("[STOP] Get all accounts");
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Account account) {
        log.info("[START] Register account: {}", account.getUsername());
        try {
            accountService.registerAccount(account);
        } catch (InvalidEmailException e) {
            log.error("[ERROR] Register account: {} with error: InvalidEmailException: {}",
                    account.getUsername(), account.getEmail());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DuplicateEmailException e) {
            log.error("[ERROR] Register account: {} with error: DuplicateEmailException: {}",
                    account.getUsername(), account.getEmail());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DuplicateUsernameException e) {
            log.error("[ERROR] Register account with error: DuplicateUsernameException: {}", account.getUsername());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            log.error("[ERROR] Register account: {} with error: {}", account.getUsername(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("[STOP] Successfully registered account: {}", account.getUsername());
        return ResponseEntity.ok().build();
    }

}
