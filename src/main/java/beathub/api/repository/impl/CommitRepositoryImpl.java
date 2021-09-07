package beathub.api.repository.impl;

import beathub.api.model.Commit;
import beathub.api.model.Plugin;
import beathub.api.model.Track;
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
    @Value("${spring.sql.commit.get_commit_by_id}")
    private String getCommitByIdSql;
    @Value("${spring.sql.track.get_tracks_by_commit_id}")
    private String getTracksByCommitIdSql;
    @Value("${spring.sql.plugin.get_plugins_by_track_id}")
    private String getPluginsByTrackIdSql;

    private final JdbcTemplate template;
    private final RowMapper<Commit> commitRowMapper;
    private final RowMapper<Track> trackRowMapper;
    private final RowMapper<Plugin> pluginRowMapper;

    @Autowired
    public CommitRepositoryImpl(JdbcTemplate template,
                                RowMapper<Commit> commitRowMapper,
                                RowMapper<Track> trackRowMapper,
                                RowMapper<Plugin> pluginRowMapper) {
        this.template = template;
        this.commitRowMapper = commitRowMapper;
        this.trackRowMapper = trackRowMapper;
        this.pluginRowMapper = pluginRowMapper;
    }

    @Override
    public List<Commit> getCommitsByProjectId(Long projectId) {
        return template.query(getCommitsByProjectIdSql, commitRowMapper, projectId);
    }

    @Override
    public Commit getCommitsById(Long commitId) {
        return template.queryForObject(getCommitByIdSql, commitRowMapper, commitId);
    }

    @Override
    public List<Track> getTracksByCommitId(Long commitId) {
        return template.query(getTracksByCommitIdSql, trackRowMapper, commitId);
    }

    @Override
    public List<Plugin> getPluginByTrackId(Long trackId) {
        return template.query(getPluginsByTrackIdSql, pluginRowMapper, trackId);
    }
}
