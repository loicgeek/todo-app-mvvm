package com.loicngou.todoapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todos_table")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    int id;

    private String name;
    private String description;
    private String date ;
    private Boolean done;

    public Todo(String name, String description,String date) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.done = false;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
