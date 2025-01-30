module com.mohsen.jiraknockoff {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.mohsen.jiraknockoff to javafx.fxml;
    exports com.mohsen.jiraknockoff;
    exports com.mohsen.jiraknockoff.user;
    exports com.mohsen.jiraknockoff.project;
    opens com.mohsen.jiraknockoff.project to javafx.fxml;
    exports com.mohsen.jiraknockoff.db;
}