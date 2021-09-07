package beathub.api.configuration;

import beathub.api.model.Account;
import beathub.api.model.Commit;
import beathub.api.model.Plugin;
import beathub.api.model.Project;
import beathub.api.model.Track;
import beathub.api.security.Role;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource datasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(datasource());
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

    @Bean()
    public RowMapper<Commit> getCommitRowMapper() {
        return (rs, rowNum) -> new Commit(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getBoolean("is_current"),
                rs.getString("file_path"),
                rs.getTimestamp("date_created")
        );
    }

    @Bean()
    public RowMapper<Track> getTrackRowMapper() {
        return (rs, rowNum) -> new Track(
                rs.getLong("id"),
                rs.getString("track_name"),
                rs.getInt("track_route_id"),
                rs.getInt("channels")
        );
    }

    @Bean()
    public RowMapper<Plugin> getPluginRowMapper() {
        return (rs, rowNum) -> new Plugin(
                rs.getLong("id"),
                rs.getString("name")
        );
    }

}
