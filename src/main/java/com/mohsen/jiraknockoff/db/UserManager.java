package com.mohsen.jiraknockoff.db;

import com.mohsen.jiraknockoff.user.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserManager implements Iterable<User> {
    private List<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
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
}