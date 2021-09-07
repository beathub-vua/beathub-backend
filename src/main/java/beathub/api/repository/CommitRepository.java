package beathub.api.repository;

import beathub.api.model.Commit;

import java.util.List;

public interface CommitRepository {
    List<Commit> getCommitsByProjectId(Long projectId);

    Commit getCommitsById(Long commitId);
}
