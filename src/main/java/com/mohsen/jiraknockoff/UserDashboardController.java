package com.mohsen.jiraknockoff;

import com.mohsen.jiraknockoff.project.Project;
import com.mohsen.jiraknockoff.project.Task;
import com.mohsen.jiraknockoff.user.CurrentUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class UserDashboardController {
    @FXML
    private ListView<Project> projectListView;

    @FXML
    private ListView<Project> teamProjectsListView;

    @FXML
    private TextField projectNameField;

    private CurrentUser currentUser = CurrentUser.getInstance();

    public void initialize() {
        // Initialize project list view
        ObservableList<Project> managerProjects = FXCollections.observableArrayList(currentUser.getUser().getManagerProjects());
        projectListView.setItems(managerProjects);

        projectListView.setOnMouseClicked(this::handleProjectClick);

        // Initialize team projects list view
        ObservableList<Project> teamProjects = FXCollections.observableArrayList(currentUser.getUser().getTeamMemberProjects());
        teamProjectsListView.setItems(teamProjects);

        teamProjectsListView.setOnMouseClicked(this::handleTeamProjectClick);
    }

    @FXML
    private void handleCreateProject() {
        String projectName = projectNameField.getText();
        if (!projectName.isEmpty()) {
            Project newProject = new Project(projectName, currentUser.getUser());
            currentUser.getUser().addManagerProject(newProject);
            projectListView.getItems().add(newProject);
            projectNameField.clear();
        }
    }

    private void handleProjectClick(MouseEvent event) {
        Project selectedProject = projectListView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            try {
                FXMLLoader loader;
                Parent root;
                if (selectedProject.getManager().equals(currentUser.getUser())) {
                    loader = new FXMLLoader(getClass().getResource("project-page.fxml"));
                    root = loader.load();
                    ProjectPageController controller = loader.getController();
                    controller.setProject(selectedProject);
                } else {
                    loader = new FXMLLoader(getClass().getResource("task-list-page.fxml"));
                    root = loader.load();
                    TaskListPageController controller = loader.getController();
                    ObservableList<Task> userTasks = FXCollections.observableArrayList(
                            selectedProject.getTasks().stream()
                                    .filter(task -> task.getAssignedTo().equals(currentUser.getUser()))
                                    .toList()
                    );
                    controller.setTasks(userTasks);
                }

                Stage stage = (Stage) projectListView.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleTeamProjectClick(MouseEvent event) {
        Project selectedProject = teamProjectsListView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("task-list-page.fxml"));
                Parent root = loader.load();
                TaskListPageController controller = loader.getController();
                ObservableList<Task> userTasks = FXCollections.observableArrayList(
                        selectedProject.getTasks().stream()
                                .filter(task -> task.getAssignedTo().equals(currentUser.getUser()))
                                .toList()
                );
                controller.setTasks(userTasks);

                Stage stage = (Stage) teamProjectsListView.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}