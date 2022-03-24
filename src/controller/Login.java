package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class Login {
    @FXML
    private void onExitButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close");
        alert.setHeaderText("Close Application?");
        Optional<ButtonType> exit = alert.showAndWait();
        if (exit.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void onLogInButton(ActionEvent actionEvent) {
    }
}
