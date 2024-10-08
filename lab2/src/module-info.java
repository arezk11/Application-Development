module Workshop2pizza {
    requires javafx.controls;
    requires javafx.fxml;

    opens controller to javafx.fxml;
    exports application;
    exports controller;
}
