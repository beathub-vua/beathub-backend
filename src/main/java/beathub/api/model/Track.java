package beathub.api.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "track")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "track_name")
    private String trackName;
    @Column(name = "track_route_id")
    private int trackRouteId;
    @Column(name = "channels")
    private int channels;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "playlist_id", referencedColumnName = "id")
    private Playlist playlist;
    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL)
    private Set<AudioFile> audioFiles;
    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL)
    private Set<Plugin> plugins;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commit_id", referencedColumnName = "id")
    private Commit commit;

    public void addToAudioFiles(Set<AudioFile> audioFiles) {
        audioFiles.forEach(
                a -> a.setTrack(this)
        );
    }

    public void addToPlugins(Set<Plugin> plugins) {
        plugins.forEach(
                p -> p.setTrack(this)
        );
    }

    public void addToPlaylist(Playlist playlist) {
        playlist.setTrack(this);
    }

    public Track() {
    }

    public Track(Long id, String trackName, int trackRouteId, int channels) {
        this.id = id;
        this.trackName = trackName;
        this.trackRouteId = trackRouteId;
        this.channels = channels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public int getTrackRouteId() {
        return trackRouteId;
    }

    public void setTrackRouteId(int trackRouteId) {
        this.trackRouteId = trackRouteId;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public Set<AudioFile> getAudioFiles() {
        return audioFiles;
    }

    public void setAudioFiles(Set<AudioFile> audioFiles) {
        this.audioFiles = audioFiles;
    }

    public Set<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(Set<Plugin> plugins) {
        this.plugins = plugins;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }
}
