package beathub.api.model.dto;

import beathub.api.model.Project;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private Timestamp dateCreated;

    public static List<ProjectDto> fromList(List<Project> projects) {
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project : projects) {
            projectDtos.add(new ProjectDto(project.getId(), project.getName(), project.getDescription(), project.getDateCreated()));
        }
        return projectDtos;
    }

    public ProjectDto() {
    }

    public ProjectDto(Long id, String name, String description, Timestamp dateCreated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
