package beathub.api.model;

import java.sql.Timestamp;

public class Account {

    private int id;
    private String username = "";
    private String password;
    private String email;
    private Timestamp dateCreated;

    public Account() {
    }

    public Account(int id, String username, String password, String email, Timestamp dateCreated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }
}
