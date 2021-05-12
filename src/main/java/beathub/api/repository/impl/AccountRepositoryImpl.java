package beathub.api.repository.impl;

import beathub.api.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import beathub.api.repository.AccountRepository;

import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Value("${spring.sql.get_accounts}")
    private String getAccountsSql;

    private final JdbcTemplate template;

    @Autowired
    public AccountRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Account> getAccounts() {
        return template.query(getAccountsSql,
                (rs, rowNum) -> new Account(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")));
    }
}
