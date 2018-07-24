package com.example.lz.firstproject;

public class ToDoTask {
    private String task;
    private String importance;
    private String description;
    private String date;

    public ToDoTask(){}

    public ToDoTask(String task, String importance, String description, String date) {
        this.task = task;
        this.importance = importance;
        this.description = description;
        this.date = date;
    }

    public String getTask() { return task; }

    public void setTask(String task) { this.task = task; }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}