module com.mohsen.jiraknockoff {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.mohsen.jiraknockoff to javafx.fxml;
    exports com.mohsen.jiraknockoff;
}