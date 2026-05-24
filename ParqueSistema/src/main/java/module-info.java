module com.techpark {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.techpark to javafx.fxml;
    opens com.techpark.control to javafx.fxml, javafx.base;
    opens com.techpark.model to javafx.base;

    exports com.techpark;
    exports com.techpark.control;
    exports com.techpark.model;
}