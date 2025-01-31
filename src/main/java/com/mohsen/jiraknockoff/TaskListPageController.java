package com.mohsen.jiraknockoff;

import com.mohsen.jiraknockoff.project.Task;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TaskListPageController {

    @FXML
    private TableView<Task> taskTableView;

    @FXML
    private TableColumn<Task, String> titleColumn;

    @FXML
    private TableColumn<Task, Task.Priority> priorityColumn;

    @FXML
    private TableColumn<Task, Task.Status> statusColumn;

    public void setTasks(ObservableList<Task> tasks) {
        taskTableView.setItems(tasks);
    }

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}