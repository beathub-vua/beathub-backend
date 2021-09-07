package beathub.api.repository;

import beathub.api.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository {
    List<Account> getAccounts();

    Boolean checkIfUsernameIsTaken(String username);

    Boolean checkIfEmailIsTaken(String email);

    void registerUser(Account account);

    List<Account> loadAccountByUsername(String username);

    int deleteAccount(Long accountId);

    Account getAccountById(Long accountId);
}
