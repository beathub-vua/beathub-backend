package beathub.api.model;

import java.util.List;

public class Track {
    private Long id;
    private String trackName;
    private int trackRouteId;
    private int channels;
    private Playlist playlist;
    private List<AudioSource> audioFiles;
    private List<Plugin> plugins;

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

    public List<AudioSource> getAudioFiles() {
        return audioFiles;
    }

    public void setAudioFiles(List<AudioSource> audioFiles) {
        this.audioFiles = audioFiles;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
