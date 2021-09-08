package beathub.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "commit")
public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String projectName;
    @Column(name = "is_current")
    private boolean isCurrent;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "date_created")
    @GeneratedValue()
    private Timestamp dateTime = Timestamp.from(Instant.now());
    @JsonAlias("track")
    @OneToMany(mappedBy = "commit", cascade = CascadeType.ALL)
    private Set<Track> tracks = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    public void addToTracks(Set<Track> tracks) {
        tracks.forEach(
                t -> t.setCommit(this)
        );
    }

    public void addToProject(Project project) {
        project.getCommits().add(this);
    }

    public Commit() {
    }

    public Commit(Long commitId, String projectName, boolean isCurrent, String filePath, Timestamp dateTime) {
        this.id = commitId;
        this.projectName = projectName;
        this.isCurrent = isCurrent;
        this.filePath = filePath;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
