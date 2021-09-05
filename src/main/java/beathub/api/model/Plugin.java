package beathub.api.model;

import java.util.List;

public class Plugin {
    private int id;
    private String uniqueId;
    private int routeId;
    private String name;
    private List<VstParameter> parameters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<VstParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<VstParameter> parameters) {
        this.parameters = parameters;
    }
}
