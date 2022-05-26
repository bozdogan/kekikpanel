package com.boraozdogan.kekikpanel.model;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final int id;
    @Column(columnDefinition = "TEXT")
    private String body;
    private LocalDate date;
    private final String owner;

    public Task(int id, String body, LocalDate date, String owner) {
        this.id = id;
        this.body = body;
        this.date = date;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }
}
