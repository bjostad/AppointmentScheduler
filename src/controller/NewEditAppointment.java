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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utils.Alert;
import utils.Time;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author BJ Bjostad
 */
public class NewEditAppointment implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private Label appointmentLabel;
    @FXML
    private TextField appointmentID;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<LocalTime> startTime;
    @FXML
    private ComboBox<LocalTime> endTime;
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

    private static UserDAO userDAO = new UserDAOImpl();
    private static ContactDAO contactDAO = new ContactDAOImpl();
    private static CustomerDAO customerDAO = new CustomerDAOImpl();
    private static AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private Appointment selectedAppointment = Appointments.getSelectedAppointment();
    private boolean isNew;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DBConnection.makeConnection();
            startTime.setItems(setBusinessHours());
            endTime.setItems(setBusinessHours());
            populateUserComboBox();
            populateCustomerComboBox();
            populateTypeComboBox();
            populateContactComboBox();

            setupEditOrNew();

        } catch (Exception e) {
            //TODO error handling
            e.printStackTrace();
        }

    }

    private void populateUserComboBox() {
        try {
            user.setItems(userDAO.getAllUsers());
        } catch (Exception e) {
            //TODO real error handling
            System.out.println(e);
        }
    }

    private void populateCustomerComboBox() {
        try {
            customer.setItems(customerDAO.getAllCustomers());
        } catch (Exception e) {
            //TODO real error handling
            System.out.println(e);
        }
    }

    private void populateContactComboBox() {
        try {
            contact.setItems(contactDAO.getAllContacts());
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

    private ObservableList<LocalTime> setBusinessHours(){
        ObservableList<LocalTime> businessHours = FXCollections.observableArrayList();
        LocalTime startHours = LocalTime.of(8,00).minusHours(Time.offsetTo("America/New_York"));
        for(int i = 0; i<=56;i++){
            businessHours.add(startHours);
            startHours = startHours.plusMinutes(15);
        }
        return businessHours;
    }

    private void setupEditOrNew(){
        if(Appointments.getSelectedAppointment() != null){

            appointmentLabel.setText("Edit Appointment");
            isNew = false;

            appointmentID.setText(String.valueOf(selectedAppointment.getID()));
            date.setValue(selectedAppointment.getStart().toLocalDate());
            startTime.getSelectionModel().select(selectedAppointment.getStart().toLocalTime());
            endTime.getSelectionModel().select(selectedAppointment.getEnd().toLocalTime());
            title.setText(selectedAppointment.getTitle());
            description.setText(selectedAppointment.getDescription());
            location.setText(selectedAppointment.getLocation());
            contact.getSelectionModel().select(contactDAO.getContactByID(selectedAppointment.getContactID()));
            type.getSelectionModel().select(selectedAppointment.getType());
            customer.setValue(customerDAO.getCustomerByID(selectedAppointment.getCustomerID()));
            user.setValue(userDAO.getUser(selectedAppointment.getUserName()));
        } else {
            appointmentLabel.setText("New Appointment");
            isNew = true;
            appointmentID.setText("Will be assigned when created");
        }
    }

    @FXML
    private void onSaveButton(ActionEvent actionEvent) {
        //TODO check for conflicting existing appointment
        if(validateInput()){
            try{
                if(Alert.confirm("Save Appointment",
                        "Save Appointment?",
                        "Are you sure you want to save the appointment?")){
                    saveAppointment();
                    changeScene(actionEvent,"Appointments");
                }

            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    private void saveAppointment(){
        try {
            model.Appointment createdAppointment = new model.Appointment(
                    determineAppID(),
                    title.getText(),
                    description.getText(),
                    location.getText(),
                    type.getSelectionModel().getSelectedItem(),
                    getStartDateTime(),
                    getEndDateTime(),
                    customer.getSelectionModel().getSelectedItem().getID(),
                    customer.getSelectionModel().getSelectedItem().getName(),
                    user.getSelectionModel().getSelectedItem().getID(),
                    user.getSelectionModel().getSelectedItem().getUsername(),
                    contact.getSelectionModel().getSelectedItem().getID(),
                    contact.getSelectionModel().getSelectedItem().getName());
            if (isNew) {
                appointmentDAO.addAppointment(createdAppointment);
            } else {
                appointmentDAO.updateAppointment(createdAppointment);
            }
        } catch (Exception e){
            //TODO capture and alert proper invalid entries
            System.out.println(e);
        }
    }

    private boolean validateInput(){
        boolean isValid = true;
        if(date.getValue() == null){
            Alert.invalidInput("Date");
            isValid = false;
        }
        if(startTime.getSelectionModel().isEmpty()){
            Alert.invalidInput("Start Time");
            isValid = false;
        }
        if(endTime.getSelectionModel().isEmpty()){
            Alert.invalidInput("End Time");
            isValid = false;
        }
        if(title.getText().isBlank()){
            Alert.invalidInput("Title");
            isValid = false;
        }
        if(description.getText().isBlank()){
            Alert.invalidInput("Description");
            isValid = false;
        }
        if(location.getText().isBlank()){
            Alert.invalidInput("Location");
            isValid = false;
        }
        if(contact.getValue() == null){
            Alert.invalidInput("Contact");
            isValid = false;
        }
        if(type.getValue() == null){
            Alert.invalidInput("Type");
            isValid = false;
        }
        if(customer.getValue() == null){
            Alert.invalidInput("Customer");
            isValid = false;
        }
        if(user.getValue() == null){
            Alert.invalidInput("User");
            isValid = false;
        }
        return isValid;
    }

    /**
     * getStartDateTime
     * convert input date and time into localDateTime
     * correct day if time selected is the next day
     * @return LocalDateTime
     */
    private LocalDateTime getStartDateTime(){
        LocalDateTime startDateTime = LocalDateTime.of(date.getValue(),startTime.getValue());
        if(startTime.getValue().isBefore(LocalTime.of(8,00)
                .minusHours(Time.offsetTo("America/New_York")))){
            startDateTime = LocalDateTime.of(date.getValue().plusDays(1),startTime.getValue());
        }
        return startDateTime;
    }

    /**
     * getEndDateTime
     * convert input date and time to localDateTime
     * correct day if time selected is the next day
     * @return localDateTime
     */
    private LocalDateTime getEndDateTime(){
        LocalDateTime endDateTime = LocalDateTime.of(date.getValue(),endTime.getValue());
        if(endTime.getValue().isBefore(LocalTime.of(8,00)
                .minusHours(Time.offsetTo("America/New_York")))){
            endDateTime = LocalDateTime.of(date.getValue().plusDays(1),endTime.getValue());
        }
        return endDateTime;
    }

    private int determineAppID(){
        if (!isNew){
            return Integer.parseInt(appointmentID.getText());
        }
        return 0;
    }

    @FXML
    private void returnToAppointmentsButton(ActionEvent actionEvent) throws Exception {
        if (utils.Alert.confirm("Return",
                "Return to Appointments?",
                "Are you sure you want to return to the Appointments menu without saving?")) {
            DBConnection.closeConnection();
            changeScene(actionEvent, "Appointments");
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
