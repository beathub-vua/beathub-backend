package beathub.api.repository;

import beathub.api.model.Commit;
import beathub.api.model.Plugin;
import beathub.api.model.Track;

import java.util.List;

public interface CommitRepository {
    List<Commit> getCommitsByProjectId(Long projectId);

    Commit getCommitsById(Long commitId);

    List<Track> getTracksByCommitId(Long commitId);

    List<Plugin> getPluginByTrackId(Long trackId);
}
