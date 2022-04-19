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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Appointment;
import utils.Alert;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Reports Controller
 *
 * @author BJ Bjostad
 */
public class Reports implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private TextArea reportsTextArea;
    @FXML
    private ComboBox contactComboBox;
    @FXML
    private ComboBox monthComboBox;
    @FXML
    private ComboBox typeComboBox;

    private static AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private static ContactDAO contactDAO = new ContactDAOImpl();

    public ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * lambda expression - easily/simply allows filtering of appointments to specified type and month
     *
     * output number of appointments by selected month and type
     * @param actionEvent
     */
    @FXML
    private void onCustomerAppointmentsButton(ActionEvent actionEvent) {
        allAppointments = appointmentDAO.getAllAppointments();
        ObservableList<Appointment> matchingAppointments;
        if(monthComboBox.getValue() != null){
            if(typeComboBox.getValue() != null){
                matchingAppointments = allAppointments.stream()
                        .filter(a -> a.getType().equals(typeComboBox.getValue())
                                && a.getStart().toLocalDate().getMonthValue()
                                == monthComboBox.getSelectionModel().getSelectedIndex()+1)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                reportsTextArea.setText("There are "+matchingAppointments.size()
                        +" appointments of type "+typeComboBox.getValue()
                        +" in "+monthComboBox.getValue());
            } else {
                Alert.invalidInput("Type");
            }
        } else {
            Alert.invalidInput("Month");
        }
    }

    /**
     * lambda expression - easily/simply allows filtering of Appointments to only those for specified contact
     *
     * Output schedule of all appointments for the selected contact
     * @param actionEvent
     */
    @FXML
    private void onScheduleByContactButton(ActionEvent actionEvent) {
        reportsTextArea.clear();
        allAppointments = appointmentDAO.getAllAppointments();
        ObservableList<Appointment> matchingAppointments = FXCollections.observableArrayList();
        if(contactComboBox.getValue() != null){
            matchingAppointments = allAppointments.stream()
                    .filter(a -> a.getContactName().equals(contactComboBox.getValue().toString()))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            for(Appointment a:matchingAppointments){
                reportsTextArea.appendText(a.getID()
                        +" | "+a.getStart()
                        +" | "+a.getEnd()
                        +" | "+a.getTitle()
                        +" | "+a.getDescription()
                        +" | "+a.getLocation()
                        +" | "+a.getContactName()
                        +" | "+a.getType()
                        +" | "+a.getCustomerName()
                        +" | "+a.getUserName()
                        +"\n");
            }
        } else {
            Alert.invalidInput("Contact");
        }
    }

    /**
     * lambda expression - easily/simply allows filtering of Appointments to only those for a specific contact
     *
     * Outputs distinct sorted list of all customers for a specific contact
     * @param actionEvent
     */
    @FXML
    private void onContactCustomers(ActionEvent actionEvent) {
        reportsTextArea.clear();
        allAppointments = appointmentDAO.getAllAppointments();
        if(contactComboBox.getValue() != null){
            ObservableList<String> contactsCustomer = allAppointments.stream()
                    .filter(a -> a.getContactName().equals(contactComboBox.getValue().toString()))
                    .map(Appointment::getCustomerName)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            for(String s:contactsCustomer){
                reportsTextArea.appendText(s+"\n");
            }
        } else {
            Alert.invalidInput("Contact");
        }
    }

    /**
     * Populate contact combo box
     */
    private void populateContactComboBox() {
        contactComboBox.setItems(contactDAO.getAllContacts());
    }

    /**
     * Populate type combo box
     */
    private void populateTypeComboBox() {
        ObservableList<String> allTypes = FXCollections.observableArrayList();
        allTypes.addAll(
                "Required",
                "Important",
                "Optional",
                "Should be an email"
        );
        typeComboBox.setItems(allTypes);
    }

    /**
     * Populate month combo box
     */
    private void populateMonthComboBox() {

        ObservableList<String> allMonths = FXCollections.observableArrayList();
        allMonths.addAll(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        );
        monthComboBox.setItems(allMonths);
    }

    /**
     * Return to appointments menu
     * @param actionEvent
     */
    @FXML
    private void returnToAppointmentsButton(ActionEvent actionEvent) {
        if (utils.Alert.confirm("Return",
                "Return to Appointments?",
                "Are you sure you want to return to the Appointments menu?")) {
            DBConnection.closeConnection();
            changeScene(actionEvent, "Appointments");
        }
    }

    /**
     * change scene
     * @param actionEvent
     * @param sceneName
     */
    private void changeScene (ActionEvent actionEvent, String sceneName) {
        try {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/" + sceneName + ".fxml"));
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * intialize reports
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            DBConnection.makeConnection();
            populateContactComboBox();
            populateTypeComboBox();
            populateMonthComboBox();
    }
}
