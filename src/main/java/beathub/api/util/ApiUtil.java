package beathub.api.util;

import beathub.api.model.Account;
import beathub.api.model.Project;
import beathub.api.security.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Configuration
public class ApiUtil {

    private final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\" +
            "x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
            "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9" +
            "]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\" +
            "x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Bean(name = "email")
    public Predicate<String> getEmailPredicate() {
        return Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE).asMatchPredicate();
    }

    @Bean
    public RowMapper<Account> getAccountRowMapper() {
        return (rs, rowNum) -> new Account(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getTimestamp("date_created"),
                Role.values()[rs.getInt("role")]
        );
    }

    @Bean(name = "withoutCommits")
    public RowMapper<Project> getProjectRowMapper() {
        return (rs, rowNum) -> new Project(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getTimestamp("date_created")
        );
    }
}
