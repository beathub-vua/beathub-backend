package beathub.api.model;

import java.util.List;

public class Region {
    private String name;
    private List<Integer> audioSourceId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getAudioSourceId() {
        return audioSourceId;
    }

    public void setAudioSourceId(List<Integer> audioSourceId) {
        this.audioSourceId = audioSourceId;
    }
}
