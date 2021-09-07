package beathub.api.repository.impl;

import beathub.api.model.Project;
import beathub.api.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private final RowMapper<Project> projectRowMapper;
    private final JdbcTemplate template;

    @Value("${spring.sql.project.get_projects_by_account_id}")
    private String getProjectsByAccountIdSql;

    @Autowired
    public ProjectRepositoryImpl(@Qualifier("withoutCommits") RowMapper<Project> projectRowMapper, JdbcTemplate template) {
        this.projectRowMapper = projectRowMapper;
        this.template = template;
    }

    @Override
    public List<Project> getProjectsByAccountId(Long accountId) {
        return template.query(getProjectsByAccountIdSql, projectRowMapper, accountId);
    }
}
