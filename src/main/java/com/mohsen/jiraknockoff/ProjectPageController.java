package com.mohsen.jiraknockoff;

import com.mohsen.jiraknockoff.db.DB;
import com.mohsen.jiraknockoff.project.Project;
import com.mohsen.jiraknockoff.project.Task;
import com.mohsen.jiraknockoff.user.CurrentUser;
import com.mohsen.jiraknockoff.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectPageController {
    @FXML
    private TableView<Task> taskTableView;

    @FXML
    private TableColumn<Task, String> taskNameColumn;

    @FXML
    private TableColumn<Task, Task.Priority> taskPriorityColumn;

    @FXML
    private TableColumn<Task, Task.Status> taskStatusColumn;

    @FXML
    private TableColumn<Task, User> taskAssignedToColumn;

    @FXML
    private TextField taskTitleField;

    @FXML
    private ComboBox<Task.Priority> taskPriorityComboBox;

    @FXML
    private ComboBox<Task.Status> taskStatusComboBox;

    @FXML
    private ComboBox<User> taskAssignedToComboBox;

    @FXML
    private ListView<User> projectUsersListView;

    @FXML
    private ComboBox<User> usersComboBox;

    private Project project;

    public void setProject(Project project) {
        this.project = project;
        initialize();
    }

    private void loadProjectUsers() {
        ObservableList<User> projectUsers = FXCollections.observableArrayList(project.getTeamMembers());
        projectUsersListView.setItems(projectUsers);
    }

    private void loadAllUsers() {
        // Assuming you have a method to get all users
        ObservableList<User> allUsers = FXCollections.observableArrayList(DB.getInstance().getAllUsers());
        usersComboBox.setItems(allUsers);
    }

    @FXML
    private void handleAddUser() {
        User selectedUser = usersComboBox.getValue();
        if (selectedUser != null && !project.getTeamMembers().contains(selectedUser)) {
            project.addTeamMember(selectedUser);
            projectUsersListView.getItems().add(selectedUser);
            selectedUser.addTeamMemberProject(project);
        }
        taskAssignedToComboBox.setItems(FXCollections.observableArrayList(project.getTeamMembers()));

    }

    public void initialize() {
        if (project == null) return;

        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskPriorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        taskAssignedToColumn.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));

        taskPriorityColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Task.Priority.values()));
        taskPriorityColumn.setOnEditCommit(event -> {
            Task task = event.getRowValue();
            task.setPriority(event.getNewValue());
            // Optionally, save the updated task to the database here
        });

        taskStatusColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Task.Status.values()));
        taskStatusColumn.setOnEditCommit(event -> {
            Task task = event.getRowValue();
            task.setStatus(event.getNewValue());
            // Optionally, save the updated task to the database here
        });

        taskAssignedToColumn.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(CurrentUser.getInstance().getUser().getTeamMemberProjects().stream()
                .flatMap(p -> p.getTeamMembers().stream())
                .distinct()
                .toArray(User[]::new))));
        taskAssignedToColumn.setOnEditCommit(event -> {
            Task task = event.getRowValue();
            task.setAssignedTo(event.getNewValue());
        });

        ObservableList<Task> tasks = FXCollections.observableArrayList(project.getTasks());
        taskTableView.setItems(tasks);
        taskTableView.setEditable(true);

        taskPriorityComboBox.setItems(FXCollections.observableArrayList(Task.Priority.values()));
        taskStatusComboBox.setItems(FXCollections.observableArrayList(Task.Status.values()));
        taskAssignedToComboBox.setItems(FXCollections.observableArrayList(project.getTeamMembers()));

        loadAllUsers();
        loadProjectUsers();
    }

    @FXML
    private void handleCreateTask() {
        String title = taskTitleField.getText();
        Task.Priority priority = taskPriorityComboBox.getValue();
        Task.Status status = taskStatusComboBox.getValue();
        User assignedTo = taskAssignedToComboBox.getValue();

        if (title != null && !title.isEmpty() && priority != null && status != null && assignedTo != null) {
            Task newTask = new Task(title, priority, status, assignedTo);
            project.addTask(newTask);
            taskTableView.getItems().add(newTask);
            taskTitleField.clear();
            taskPriorityComboBox.setValue(null);
            taskStatusComboBox.setValue(null);
            taskAssignedToComboBox.setValue(null);
        }
    }

    @FXML
    private void handleReturnToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user-dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) taskTableView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}