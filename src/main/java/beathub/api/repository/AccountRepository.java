package beathub.api.repository;

import beathub.api.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository {
    List<Account> getAccounts();
}
