package com.iconcept.projectapp.adapter;

public class ProjectDetails {
    int project_id ;
    String author;
    String title;
    String description;
    String status;
    int priority;
    String created_time;
    String updated_time;

    public int getProjectId() {
        return project_id;
    }

    public void setProjectId(int project_id) {
        this.project_id = project_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String time) {
        this.created_time = time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String time) {
        this.updated_time = time;
    }
}
