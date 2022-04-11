package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author BJ Bjostad
 */
public class NewAppointment implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private TextField appointmentID;
    @FXML
    private DatePicker date;
    @FXML
    private TextField startTime;
    @FXML
    private TextField endTime;
    @FXML
    private TextField title;
    @FXML
    private TextField description;
    @FXML
    private TextField location;
    @FXML
    private ComboBox<Contact> contact;
    @FXML
    private ComboBox<String> type;
    @FXML
    private ComboBox<Customer> customer;
    @FXML
    private ComboBox<User> user;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DBConnection.makeConnection();
            populateUserComboBox();
            populateCustomerComboBox();
            populateTypeComboBox();
            populateContactComboBox();

        } catch (Exception e) {
            //TODO error handling
            e.printStackTrace();
        }

    }

    private void populateUserComboBox() {
        try {
            user.setItems(UserDAOImpl.getAllUsers());
        } catch (Exception e) {
            //TODO real error handling
            System.out.println(e);
        }
    }

    private void populateCustomerComboBox() {
        try {
            customer.setItems(CustomerDAOImpl.getAllCustomers());
        } catch (Exception e) {
            //TODO real error handling
            System.out.println(e);
        }
    }

    private void populateContactComboBox() {
        try {
            contact.setItems(ContactDAOImpl.getAllContacts());
        } catch (Exception e) {
            //TODO real error handling
            System.out.println(e);
        }
    }

    private void populateTypeComboBox() {
        try {
            ObservableList<String> allTypes = FXCollections.observableArrayList();
            allTypes.addAll(
                    "Required",
                    "Important",
                    "Optional",
                    "Should be an email"
            );
            type.setItems(allTypes);
        } catch (Exception e) {
            //TODO real error handling
            System.out.println(e);
        }
    }



    @FXML
    private void returnToAppointmentsButton(ActionEvent actionEvent) throws Exception {
        DBConnection.closeConnection();
        changeScene(actionEvent,"Appointments");
    }

    @FXML
    private void onSaveButton(ActionEvent actionEvent) {
        //TODO check for existing appointment
        try{
            Appointment createdAppointment = new Appointment( -1,
                    title.getText(),
                    description.getText(),
                    location.getText(),
                    type.getSelectionModel().getSelectedItem(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    customer.getSelectionModel().getSelectedItem().getID(),
                    customer.getSelectionModel().getSelectedItem().getName(),
                    user.getSelectionModel().getSelectedItem().getID(),
                    user.getSelectionModel().getSelectedItem().getUsername(),
                    contact.getSelectionModel().getSelectedItem().getID(),
                    contact.getSelectionModel().getSelectedItem().getName()
            );
            AppointmentDAOImpl.addAppointment(createdAppointment);
        } catch (Exception e){
            System.out.println(e);
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
}
