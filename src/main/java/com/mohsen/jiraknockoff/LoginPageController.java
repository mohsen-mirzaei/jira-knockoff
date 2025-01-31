package com.mohsen.jiraknockoff;

import com.mohsen.jiraknockoff.db.DB;
import com.mohsen.jiraknockoff.project.Project;
import com.mohsen.jiraknockoff.project.Task;
import com.mohsen.jiraknockoff.user.CurrentUser;
import com.mohsen.jiraknockoff.user.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label welcomeText;

    DB database = DB.getInstance();


    @FXML
    protected void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = database.findUserByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                welcomeText.setText("Welcome, " + username + "!");
            } else {
                welcomeText.setText("Incorrect password!");
            }
        } else {
            database.addUser(user = new User(username, password));
            welcomeText.setText("Account created!");
        }

        //////
        Project prj = new Project("Project 1", user);
        prj.addTask(new Task("Task 1", Task.Priority.LOW, Task.Status.TODO, user));
        user.addTeamMemberProject(prj);
        user.addTask(new Task("Task 1", Task.Priority.LOW, Task.Status.TODO, user));
        //////

        CurrentUser currentUser = CurrentUser.getInstance();
        currentUser.setUser(user);
        loadUserDashboard();
    }

    private void loadUserDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/mohsen/jiraknockoff/user-dashboard.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}