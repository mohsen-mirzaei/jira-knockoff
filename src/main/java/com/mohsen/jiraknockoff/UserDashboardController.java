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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class UserDashboardController {

    @FXML
    private TableView<Task> todoTableView;

    @FXML
    private TableColumn<Task, String> todoTitleColumn;

    @FXML
    private TableColumn<Task, Task.Priority> todoPriorityColumn;

    @FXML
    private TableColumn<Task, Task.Status> todoStatusColumn;

    @FXML
    private TableView<Task> inProgressTableView;

    @FXML
    private TableColumn<Task, String> inProgressTitleColumn;

    @FXML
    private TableColumn<Task, Task.Priority> inProgressPriorityColumn;

    @FXML
    private TableColumn<Task, Task.Status> inProgressStatusColumn;

    @FXML
    private TableView<Task> doneTableView;

    @FXML
    private TableColumn<Task, String> doneTitleColumn;

    @FXML
    private TableColumn<Task, Task.Priority> donePriorityColumn;

    @FXML
    private TableColumn<Task, Task.Status> doneStatusColumn;

    @FXML
    private ListView<Project> projectListView;

    @FXML
    private ListView<Project> teamProjectsListView;

    @FXML
    private TextField projectNameField;

    @FXML
    private Label currentUserLabel;

    private CurrentUser currentUser = CurrentUser.getInstance();

    public void initialize() {
        // Set the current user's name to the label
        currentUserLabel.setText(currentUser.getUser().getUsername());

        // Initialize columns
        initializeColumns(todoTitleColumn, todoPriorityColumn, todoStatusColumn);
        initializeColumns(inProgressTitleColumn, inProgressPriorityColumn, inProgressStatusColumn);
        initializeColumns(doneTitleColumn, donePriorityColumn, doneStatusColumn);

        // Initialize project list view
        ObservableList<Project> managerProjects = FXCollections.observableArrayList(currentUser.getUser().getManagerProjects());
        projectListView.setItems(managerProjects);

        // Initialize team projects list view
        ObservableList<Project> teamProjects = FXCollections.observableArrayList(currentUser.getUser().getTeamMemberProjects());
        teamProjectsListView.setItems(teamProjects);

        // Set single-click event handlers
        projectListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Project selectedProject = projectListView.getSelectionModel().getSelectedItem();
                if (selectedProject != null) {
                    filterTasksByProject(selectedProject);
                }
            } else if (event.getClickCount() == 2) {
                Project selectedProject = projectListView.getSelectionModel().getSelectedItem();
                if (selectedProject != null) {
                    loadProjectPage(selectedProject);
                }
            }
        });

        teamProjectsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Project selectedProject = teamProjectsListView.getSelectionModel().getSelectedItem();
                if (selectedProject != null) {
                    filterTasksByProject(selectedProject);
                }
            } else if (event.getClickCount() == 2) {
                Project selectedProject = projectListView.getSelectionModel().getSelectedItem();
                if (selectedProject != null) {
                    loadProjectPage(selectedProject);
                }
            }
        });
    }

    private void filterTasksByProject(Project project) {
        ObservableList<Task> todoTasks = FXCollections.observableArrayList(
                project.getTasks().stream()
                        .filter(task -> task.getStatus() == Task.Status.TODO && task.getAssignedTo().equals(currentUser.getUser()))
                        .toList()
        );
        ObservableList<Task> inProgressTasks = FXCollections.observableArrayList(
                project.getTasks().stream()
                        .filter(task -> task.getStatus() == Task.Status.IN_PROGRESS && task.getAssignedTo().equals(currentUser.getUser()))
                        .toList()
        );
        ObservableList<Task> doneTasks = FXCollections.observableArrayList(
                project.getTasks().stream()
                        .filter(task -> task.getStatus() == Task.Status.DONE && task.getAssignedTo().equals(currentUser.getUser()))
                        .toList()
        );

        todoTableView.setItems(todoTasks);
        inProgressTableView.setItems(inProgressTasks);
        doneTableView.setItems(doneTasks);
    }

    private void loadProjectPage(Project project) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("project-page.fxml"));
            Parent root = loader.load();

            ProjectPageController controller = loader.getController();
            controller.setProject(project);

            Stage stage = (Stage) projectListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeColumns(TableColumn<Task, String> titleColumn, TableColumn<Task, Task.Priority> priorityColumn, TableColumn<Task, Task.Status> statusColumn) {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Task, Task.Status> call(TableColumn<Task, Task.Status> param) {
                return new TableCell<>() {
                    private final ComboBox<Task.Status> comboBox = new ComboBox<>();

                    {
                        comboBox.getItems().setAll(Task.Status.values());
                        comboBox.setOnAction(event -> {
                            Task task = getTableView().getItems().get(getIndex());
                            Task.Status newStatus = comboBox.getValue();
                            if (task.getStatus() != newStatus) {
                                moveTask(task, newStatus);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Task.Status item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            comboBox.setValue(item);
                            setGraphic(comboBox);
                        }
                    }
                };
            }
        });
    }

    private void moveTask(Task task, Task.Status newStatus) {
        ObservableList<Task> sourceList = switch (task.getStatus()) {
            case TODO -> todoTableView.getItems();
            case IN_PROGRESS -> inProgressTableView.getItems();
            case DONE -> doneTableView.getItems();
        };

        ObservableList<Task> targetList = switch (newStatus) {
            case TODO -> todoTableView.getItems();
            case IN_PROGRESS -> inProgressTableView.getItems();
            case DONE -> doneTableView.getItems();
        };

        sourceList.remove(task);
        task.setStatus(newStatus);
        targetList.add(task);
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

    @FXML
    private void handleLogout() {
        currentUser.setUser(null);

        // Load the login page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-page.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) projectListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}