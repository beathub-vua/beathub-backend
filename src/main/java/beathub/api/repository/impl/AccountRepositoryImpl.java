package beathub.api.repository.impl;

import beathub.api.model.Account;
import beathub.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Value("${spring.sql.account.get_accounts}")
    private String getAccountsSql;
    @Value("${spring.sql.account.get_account_by_id}")
    private String getAccountByIdSql;
    @Value("${spring.sql.account.get_account_by_username}")
    private String getAccountByUsernameSql;
    @Value("${spring.sql.account.get_accountId_by_username}")
    private String getAccountIdByUsernameSql;
    @Value("${spring.sql.account.get_accountId_by_email}")
    private String getAccountIdByEmailSql;
    @Value("${spring.sql.account.register_account}")
    private String registerUserSql;
    @Value("${spring.sql.account.delete_account}")
    private String deleteAccountSql;

    private final JdbcTemplate template;
    private final RowMapper<Account> accountRowMapper;

    @Autowired
    public AccountRepositoryImpl(JdbcTemplate template, RowMapper<Account> accountRowMapper) {
        this.template = template;
        this.accountRowMapper = accountRowMapper;
    }

    @Override
    public List<Account> getAccounts() {
        return template.query(getAccountsSql, accountRowMapper);
    }

    @Override
    public Boolean checkIfUsernameIsTaken(String username) {
        try {
            template.queryForObject(getAccountIdByUsernameSql, Integer.class, username);
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean checkIfEmailIsTaken(String email) {
        try {
            template.queryForObject(getAccountIdByEmailSql, Integer.class, email);
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public void registerUser(Account account) {
        template.update(registerUserSql,
                account.getUsername(),
                account.getPassword(),
                account.getEmail(),
                Timestamp.from(Instant.now()));
    }

    @Override
    public List<Account> loadAccountByUsername(String username) {
        return template.query(getAccountByUsernameSql, accountRowMapper, username);
    }

    @Override
    public int deleteAccount(Long accountId) {
        return template.update(deleteAccountSql, accountId);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return template.queryForObject(getAccountByIdSql, accountRowMapper, accountId);
    }
}
