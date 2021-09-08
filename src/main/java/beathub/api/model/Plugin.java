package beathub.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import java.util.Set;

@Entity
@Table(name = "plugin")
public class Plugin {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonAlias("id")
    @Column(name = "code")
    private Long code;
    @Column(name = "unique_id")
    private String uniqueId;
    @Column(name = "route_id")
    private int routeId;
    @Column(name = "name")
    private String name;
    @Column(name = "dll_path")
    private String dllPath;
    @OneToMany(mappedBy = "plugin", cascade = CascadeType.ALL)
    private Set<VstParameter> parameters;
    @ManyToOne
    @JoinColumn(name = "track_id", referencedColumnName = "id")
    private Track track;

    public void addToVstParameters(Set<VstParameter> vstParameters) {
        vstParameters.forEach(
                p -> p.setPlugin(this)
        );
    }

    public Plugin() {
    }

    public Plugin(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<VstParameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<VstParameter> parameters) {
        this.parameters = parameters;
    }

    public String getDllPath() {
        return dllPath;
    }

    public void setDllPath(String dllPath) {
        this.dllPath = dllPath;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
