package com.mohsen.jiraknockoff;

import com.mohsen.jiraknockoff.project.Project;
import com.mohsen.jiraknockoff.project.Task;
import com.mohsen.jiraknockoff.user.CurrentUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class UserDashboardController {
    @FXML
    private ListView<Project> projectListView;

    @FXML
    private TableView<Task> taskTableView;

    @FXML
    private TableColumn<Task, String> taskTitleColumn;

    @FXML
    private TableColumn<Task, Task.priority> taskPriorityColumn;

    @FXML
    private TableColumn<Task, Task.taskStatus> taskStatusColumn;

    private CurrentUser currentUser = CurrentUser.getInstance();

    public void initialize() {
        // Initialize project list view
        ObservableList<Project> projects = FXCollections.observableArrayList(currentUser.getUser().getTeamMemberProjects());
        projectListView.setItems(projects);

        projectListView.setOnMouseClicked(this::handleProjectClick);

        // Initialize task table view
        taskTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskPriorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<Task> tasks = FXCollections.observableArrayList(currentUser.getUser().getTasks());
        taskTableView.setItems(tasks);
    }

    private void handleProjectClick(MouseEvent event) {
        Project selectedProject = projectListView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            // Handle the project click event (e.g., open project details)
            System.out.println("Selected project: " + selectedProject.getName());
        }
    }
}