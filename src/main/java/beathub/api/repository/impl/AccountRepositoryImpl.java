package beathub.api.repository.impl;

import beathub.api.model.Account;
import beathub.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Value("${spring.sql.get_accounts}")
    private String getAccountsSql;
    @Value("${spring.sql.get_account_by_username}")
    private String getAccountByUsernameSql;
    @Value("${spring.sql.get_accountId_by_username}")
    private String getAccountIdByUsernameSql;
    @Value("${spring.sql.get_accountId_by_email}")
    private String getAccountIdByEmailSql;
    @Value("${spring.sql.register_user}")
    private String registerUserSql;

    private final JdbcTemplate template;
    private final RowMapper<Account> accountRowMapper;

    @Autowired
    public AccountRepositoryImpl(JdbcTemplate template, @Qualifier("account") RowMapper<Account> accountRowMapper) {
        this.template = template;
        this.accountRowMapper = accountRowMapper;
    }

    @Override
    public List<Account> getAccounts() {
        return template.query(getAccountsSql, accountRowMapper);
    }

    @Override
    public Integer getAccountIdByUsername(String username) {
        return template.queryForObject(getAccountIdByUsernameSql, Integer.class, username);
    }

    @Override
    public Integer getAccountIdByEmail(String email) {
        return template.queryForObject(getAccountIdByEmailSql, Integer.class, email);
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
}
