package com.mohsen.jiraknockoff.user;

import com.mohsen.jiraknockoff.project.Project;
import com.mohsen.jiraknockoff.project.Task;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Project> teamMemberProjects;
    private List<Project> managerProjects;
    private List<Task> tasks;

    public User(String username, String password, List<Project> teamMemberProjects, List<Project> managerProjects, List<Task> tasks) {
        this.username = username;
        this.password = password;
        this.teamMemberProjects = teamMemberProjects;
        this.managerProjects = managerProjects;
        this.tasks = tasks;
    }

    public User(String username, String password) {
        this(username, password, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Project> getTeamMemberProjects() {
        return teamMemberProjects;
    }

    public void setTeamMemberProjects(List<Project> teamMemberProjects) {
        this.teamMemberProjects = teamMemberProjects;
    }

    public List<Project> getManagerProjects() {
        return managerProjects;
    }

    public void setManagerProjects(List<Project> managerProjects) {
        this.managerProjects = managerProjects;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTeamMemberProject(Project project) {
        this.teamMemberProjects.add(project);
    }

    public void addManagerProject(Project project) {
        this.managerProjects.add(project);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    @Override
    public String toString() {
        return username;
    }
}