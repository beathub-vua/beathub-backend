package beathub.api.model;

import java.sql.Timestamp;
import java.util.List;

public class Project {

    private Long id;
    private String name;
    private String description;
    private Timestamp dateCreated;
    private List<Commit> commits;

    public Project(Long id, String name, String description, Timestamp dateCreated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
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
}
