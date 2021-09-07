package beathub.api.model;

import java.sql.Timestamp;
import java.util.List;

public class Commit {
    private Long commitId;
    private String projectName;
    private boolean isCurrent;
    private String filePath;
    private Timestamp dateTime;
    private List<Track> tracks;

    public Commit(Long commitId, String projectName, boolean isCurrent, String filePath, Timestamp dateTime) {
        this.commitId = commitId;
        this.projectName = projectName;
        this.isCurrent = isCurrent;
        this.filePath = filePath;
        this.dateTime = dateTime;
    }

    public Long getCommitId() {
        return commitId;
    }

    public void setCommitId(Long commitId) {
        this.commitId = commitId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
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
}
