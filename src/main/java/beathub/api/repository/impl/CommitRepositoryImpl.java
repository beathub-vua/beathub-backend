package beathub.api.repository.impl;

import beathub.api.model.Commit;
import beathub.api.repository.CommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommitRepositoryImpl implements CommitRepository {

    @Value("${spring.sql.commit.get_commits_by_project_id}")
    private String getCommitsByProjectIdSql;

    private final JdbcTemplate template;
    private final RowMapper<Commit> commitRowMapper;

    @Autowired
    public CommitRepositoryImpl(JdbcTemplate template, RowMapper<Commit> commitRowMapper) {
        this.template = template;
        this.commitRowMapper = commitRowMapper;
    }

    @Override
    public List<Commit> getCommitsByProjectId(Long projectId) {
        return template.query(getCommitsByProjectIdSql, commitRowMapper, projectId);
    }
}
