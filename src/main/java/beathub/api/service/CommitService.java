package beathub.api.service;

import beathub.api.exception.NoSuchProjectException;
import beathub.api.model.Commit;
import beathub.api.model.Project;
import beathub.api.model.Track;
import beathub.api.repository.CommitRepository;
import beathub.api.repository.JpaCommitRepository;
import beathub.api.repository.JpaProjectRepository;
import beathub.api.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommitService {

    private final CommitRepository repository;
    private final TrackRepository trackRepository;
    private final JpaCommitRepository jpaRepository;
    private final JpaProjectRepository projectRepository;

    @Autowired
    public CommitService(CommitRepository repository, TrackRepository trackRepository, JpaCommitRepository jpaRepository, JpaProjectRepository projectRepository) {
        this.repository = repository;
        this.trackRepository = trackRepository;
        this.jpaRepository = jpaRepository;
        this.projectRepository = projectRepository;
    }

    public List<Commit> getCommitsByProjectId(Long projectId) {
//        List<Commit> commits = repository.getCommitsByProjectId(projectId);

//        commits.forEach(c ->
//        {
//            c.setTracks(repository.getTracksByCommitId(c.getId()));
//            c.getTracks().forEach(t -> t.setPlugins(repository.getPluginByTrackId(t.getId())));
//        });
        Set<Commit> commits = null;
        Optional<Project> optProject = projectRepository.findById(projectId);

        if (optProject.isPresent()) {
            commits = optProject.get().getCommits();
        }
        return new ArrayList<>(commits);
    }

    public Commit getCommitById(Long commitId) {
        Commit commit = null;
        Optional<Commit> optionalCommit = jpaRepository.findById(commitId);
        if (optionalCommit.isPresent()) {
            commit = optionalCommit.get();
        }
        return commit;
    }

    public void saveCommit(Commit commit, Long projectId) {
        // Check if project exists
        Optional<Project> optProject = projectRepository.findById(projectId);
        if (optProject.isEmpty()) {
            throw new NoSuchProjectException(projectId);
        }

        // Fill entities
        commit.addToTracks(commit.getTracks());
        commit.setProject(optProject.get());
        commit.getTracks().forEach(t -> {
            t.addToAudioFiles(t.getAudioFiles());
            t.addToPlaylist(t.getPlaylist());
            t.getPlugins().forEach(p -> p.addToVstParameters(p.getParameters()));
            t.getPlaylist().addToRegion(t.getPlaylist().getRegion());
        });
        commit.getTracks().forEach(t -> t.addToPlugins(t.getPlugins()));
        jpaRepository.saveAndFlush(commit);
    }
}
