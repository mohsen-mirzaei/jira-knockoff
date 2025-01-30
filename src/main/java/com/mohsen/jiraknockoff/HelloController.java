package com.mohsen.jiraknockoff;

import com.mohsen.jiraknockoff.db.UserManager;
import com.mohsen.jiraknockoff.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label welcomeText;

    private UserManager userManager;


    @FXML
    protected void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userManager.findUserByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                welcomeText.setText("Welcome, " + username + "!");
            } else {
                welcomeText.setText("Incorrect password!");
            }
        } else {
            userManager.addUser(new User(username, password));
            welcomeText.setText("Account created!");
        }
    }
}