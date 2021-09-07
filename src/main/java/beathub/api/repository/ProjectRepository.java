package beathub.api.repository;

import beathub.api.model.Project;

import java.util.List;

public interface ProjectRepository {
    List<Project> getProjectsByAccountId(Long accountId);
}
