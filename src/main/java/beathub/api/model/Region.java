package beathub.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToOne(mappedBy = "region")
    private Playlist playlist;
    @JsonAlias("audioSourceId")
    @Transient
    private Set<AudioFile> audioFiles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AudioFile> getAudioFiles() {
        return audioFiles;
    }

    public void setAudioFiles(Set<AudioFile> audioSourceId) {
        this.audioFiles = audioSourceId;
    }

    @JsonSetter("audioSourceId")
    public void setAudioSourceId(List<Long> audioSourceIds) {
        audioFiles.clear();
        for (Long audioSourceId : audioSourceIds) {
            audioFiles.add(new AudioFile(audioSourceId));
        }
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
