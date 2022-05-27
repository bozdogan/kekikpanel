package com.boraozdogan.kekikpanel.model;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private final String owner;
    @Column(columnDefinition = "TEXT")
    private String body;
    private LocalDate dateCreated;

    private Note() {
        this.owner = null;
    }

    public Note(String owner, String body, LocalDate dateCreated) {
        this.owner = owner;
        this.body = body;
        this.dateCreated = dateCreated;
    }


    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }


    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", body='" + body + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
