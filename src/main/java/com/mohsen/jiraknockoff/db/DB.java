package com.mohsen.jiraknockoff.db;

import com.mohsen.jiraknockoff.project.Project;
import com.mohsen.jiraknockoff.user.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DB implements Iterable<User> {
    private static DB instance;
    private List<User> users;

    private DB() {
        this.users = new ArrayList<>();
        this.addUser(new User("mohsen", "mohsen123"));
        this.findUserByUsername("mohsen").addManagerProject(new Project("kill self 2025", this.findUserByUsername("mohsen")));
    }

    public static DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Iterator<User> iterator() {
        return users.iterator();
    }

    public List<User> getAllUsers() {
        return users;
    }
}

