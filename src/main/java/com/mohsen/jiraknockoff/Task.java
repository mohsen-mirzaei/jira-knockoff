package com.mohsen.jiraknockoff;

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

    public Task(String title, priority priority, taskStatus status) {
        this.title = title;
        this.priority = priority;
        this.status = status;
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
}