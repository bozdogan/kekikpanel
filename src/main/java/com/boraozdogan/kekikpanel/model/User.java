package com.boraozdogan.kekikpanel.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table( // NOTE(bora): https://github.com/h2database/h2database/issues/3363#issuecomment-1021542613
        name="\"user\"")
public class User {
    @Id
    private String username;
    private String pwHash;
    private boolean isAdmin;

    @OneToMany(mappedBy = "owner",
               cascade = CascadeType.ALL)
    private Set<Note> notes = new HashSet<>();

    private User() {
    }

    public User(String username, String pwHash, boolean isAdmin) {
        this.username = username;
        this.pwHash = pwHash;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", pwHash='" + pwHash + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
