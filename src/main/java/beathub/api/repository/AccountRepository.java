package beathub.api.repository;

import beathub.api.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository {
    List<Account> getAccounts();

    Integer getAccountIdByUsername(String username);

    Integer getAccountIdByEmail(String email);

    void registerAccount(Account account);
}