module cr.ac.una.unaplanilla {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires MaterialFX;
    requires atlantafx.base;
    requires java.base;
    requires java.logging;

    opens cr.ac.una.unaplanilla to javafx.fxml;
    opens cr.ac.una.unaplanilla.controller to javafx.fxml;
    opens cr.ac.una.unaplanilla.util to javafx.graphics, javafx.fxml;

    exports cr.ac.una.unaplanilla;
}