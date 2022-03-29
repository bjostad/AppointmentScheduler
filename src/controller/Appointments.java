package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author BJ Bjostad
 */
public class Appointments implements Initializable {

    Stage stage;
    Parent scene;

    private void changeScene (ActionEvent actionEvent, String sceneName) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/"+sceneName+".fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void onNewAppointmentButton(ActionEvent actionEvent) {
    }

    @FXML
    private void onEditAppointmentButton(ActionEvent actionEvent) {
    }

    @FXML
    private void onDeleteButton(ActionEvent actionEvent) {
    }

    @FXML
    private void onCustomerButton(ActionEvent actionEvent) {
    }

    @FXML
    private void onReportsPageButton(ActionEvent actionEvent) {
    }
}
