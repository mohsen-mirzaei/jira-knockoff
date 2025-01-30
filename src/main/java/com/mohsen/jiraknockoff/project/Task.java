package com.mohsen.jiraknockoff.project;

import com.mohsen.jiraknockoff.user.User;

public class Task {
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    public enum Status {
        TODO, IN_PROGRESS, DONE
    }

    private String title;
    private Priority priority;
    private Status status;
    private User assignedTo;

    public Task(String title, Priority priority, Status status, User assignedTo) {
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.assignedTo = assignedTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }
}