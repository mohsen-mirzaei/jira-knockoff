package com.mohsen.jiraknockoff.project;

import com.mohsen.jiraknockoff.user.User;

public class Task {
    enum priority {
        LOW, MEDIUM, HIGH
    }

    enum taskStatus {
        TODO, IN_PROGRESS, DONE
    }

    private String title;
    private priority priority;
    private taskStatus status;
    private User assignedTo;

    public Task(String title, priority priority, taskStatus status, User assignedTo) {
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

    public priority getPriority() {
        return priority;
    }

    public void setPriority(priority priority) {
        this.priority = priority;
    }

    public taskStatus getStatus() {
        return status;
    }

    public void setStatus(taskStatus status) {
        this.status = status;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }
}