package beathub.api.service;

import beathub.api.model.Commit;
import beathub.api.repository.CommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommitService {

    private final CommitRepository repository;

    @Autowired
    public CommitService(CommitRepository repository) {
        this.repository = repository;
    }

    public List<Commit> getCommitsByProjectId(Long projectId) {
        return repository.getCommitsByProjectId(projectId);
    }

    public Commit getCommitById(Long commitId) {
        return repository.getCommitsById(commitId);
    }
}
