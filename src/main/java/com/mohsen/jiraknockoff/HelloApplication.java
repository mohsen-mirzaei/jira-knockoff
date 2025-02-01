package com.mohsen.jiraknockoff;

import com.mohsen.jiraknockoff.db.DB;
import com.mohsen.jiraknockoff.user.CurrentUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600); // Adjusted dimensions
        stage.setTitle("Jira Knockoff");

        Image icon = new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("/jira-favicon.png")));
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

        DB database = DB.getInstance();
        CurrentUser currentUser = CurrentUser.getInstance();


    }
}