package beathub.api.model.dto;

import beathub.api.model.Commit;
import beathub.api.model.Track;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommitDto {
    private String projectName;
    private String filePath;
    private boolean isCurrent;
    private Timestamp dateTime;
    private Set<Track> tracks;

    public CommitDto() {
    }

    public CommitDto(String projectName, String filePath, boolean isCurrent, Timestamp dateTime, Set<Track> tracks) {
        this.projectName = projectName;
        this.filePath = filePath;
        this.isCurrent = isCurrent;
        this.dateTime = dateTime;
        this.tracks = tracks;
    }

    public static CommitDto from(Commit commit) {
        CommitDto commitDto = new CommitDto(commit.getProjectName(), commit.getFilePath(), commit.isCurrent(), commit.getDateTime(), commit.getTracks());
        commitDto.getTracks().forEach(t ->
        {
            t.setCommit(null);
            t.getAudioFiles().forEach(a -> a.setTrack(null));
            t.getPlugins().forEach(p -> p.setTrack(null));
            t.getPlugins().forEach(p -> {
                p.setTrack(null);
                p.getParameters().forEach(vstParameter -> vstParameter.setPlugin(null));
            });
            t.getPlaylist().setTrack(null);
            t.getPlaylist().getRegion().setPlaylist(null);
        });

        return commitDto;
    }

    public static List<CommitDto> fromList(List<Commit> commits) {
        List<CommitDto> commitDtos = new ArrayList<>();
        commits.forEach(c -> commitDtos.add(from(c)));
        return commitDtos;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }
}
