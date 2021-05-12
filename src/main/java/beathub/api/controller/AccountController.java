package beathub.api.controller;

import beathub.api.model.Account;
import beathub.api.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("[START] Get all users");
        try {
            accounts = accountService.getUsers();
        } catch (Exception e) {
            log.error("[ERROR] Get all users with error ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("[STOP] Get all users");
        return ResponseEntity.ok(accounts);
    }

}
