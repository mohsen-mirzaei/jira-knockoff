package com.mohsen.jiraknockoff.project;

import com.mohsen.jiraknockoff.user.User;
import java.util.List;
import java.util.ArrayList;

public class Project {
    private String name;
    private String description;
    private User manager;
    private List<User> teamMembers;
    private List<Task> tasks;

    public Project(String name, User manager) {
        this.name = name;
        this.description = "";
        this.manager = manager;
        this.teamMembers = new ArrayList<>();
        this.tasks = new ArrayList<>();
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

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public List<User> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<User> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Task createTask(String title, Task.Priority priority, Task.Status status, User assignedTo) {
        Task newTask = new Task(title, priority, status, assignedTo);
        this.tasks.add(newTask);
        return newTask;
    }

    @Override
    public String toString() {
        return name;
    }
}