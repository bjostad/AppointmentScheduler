package controller;

import DAO.AppointmentDAOImpl;
import DAO.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author BJ Bjostad
 */
public class Appointments implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private TableColumn appointmentIdColumn;
    @FXML
    private TableColumn startDateTimeColumn;
    @FXML
    private TableColumn endDateTimeColumn;
    @FXML
    private TableColumn titleColumn;
    @FXML
    private TableColumn descriptionColumn;
    @FXML
    private TableColumn locationColumn;
    @FXML
    private TableColumn contactColumn;
    @FXML
    private TableColumn typeColumn;
    @FXML
    private TableColumn customerIdColumn;
    @FXML
    private TableColumn userIdColumn;
    @FXML
    private TableView appointmentTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            DBConnection.makeConnection();
            ObservableList<Appointment> appointments = AppointmentDAOImpl.getAllAppointments();
            System.out.println(appointments);

            appointmentTable.setItems(appointments);
            appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
            startDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            endDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        } catch (Exception e) {
            //TODO error handling
        }
    }

    private void changeScene (ActionEvent actionEvent, String sceneName){
        try {
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/"+sceneName+".fxml"));
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e){
            //TODO error handling
            System.out.println(e);
        }
    }

    @FXML
    private void onNewAppointmentButton(ActionEvent actionEvent) {
    }

    @FXML
    private void onEditAppointmentButton(ActionEvent actionEvent) {
    }

    @FXML
    private void onDeleteAppointmentButton(ActionEvent actionEvent) {
    }

    @FXML
    private void onCustomerButton(ActionEvent actionEvent) throws IOException {
        try {
            DBConnection.closeConnection();
            changeScene( actionEvent, "Customers");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void onReportsButton(ActionEvent actionEvent) {
    }

    @FXML
    private void onExitButton(ActionEvent actionEvent) throws Exception {
        if (util.Alert.confirm("Exit","Close Application","Are you sure you want to close the application?")) {
            //close DB connection before exit
            DBConnection.closeConnection();
            System.exit(0);
        }
    }
}
