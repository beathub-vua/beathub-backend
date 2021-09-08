package beathub.api.service;

import beathub.api.exception.NoSuchAccountException;
import beathub.api.model.Account;
import beathub.api.model.Commit;
import beathub.api.model.Project;
import beathub.api.repository.JpaProjectRepository;
import beathub.api.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository repository;
    private final JpaProjectRepository jpaRepository;
    private final CommitService commitService;
    private final AccountService accountService;

    @Autowired
    public ProjectService(ProjectRepository repository, JpaProjectRepository jpaRepository, CommitService commitService, AccountService accountService) {
        this.repository = repository;
        this.jpaRepository = jpaRepository;
        this.commitService = commitService;
        this.accountService = accountService;
    }

    public List<Project> getProjectsByAccountId(Long accountId) {
        return repository.getProjectsByAccountId(accountId);
    }

    public boolean projectExists(Long projectId) {
        try {
            repository.getProjectById(projectId);
            return true;
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
    }

    public void newProject(Commit commit, Project project, Long accountId) {
        // Check if account exists
        Optional<Account> optAccount = accountService.getAccountById(accountId);
        if (optAccount.isEmpty()) {
            throw new NoSuchAccountException(accountId);
        }
        // Save project
        project.setAccount(optAccount.get());
        Project newProject = jpaRepository.saveAndFlush(project);
        // Save to account
        optAccount.get().getProjects().add(newProject);
        accountService.saveAccount(optAccount.get());
        // Save first commit
        commitService.saveCommit(commit, newProject.getId());
    }
}
