package beathub.api.service;

import beathub.api.model.Project;
import beathub.api.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository repository;

    @Autowired
    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public List<Project> getProjectsByAccountId(Long accountId) {
        return repository.getProjectsByAccountId(accountId);
    }
}
