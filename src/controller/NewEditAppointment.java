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
import utils.Alert;
import utils.Time;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * NewEditAppointment Controller
 *
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

    /**
     * intialize NewEditAppointment scene
     * create database connection
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection.makeConnection();
        startTime.setItems(setBusinessHours());
        endTime.setItems(setBusinessHours());
        user.setItems(userDAO.getAllUsers());
        customer.setItems(customerDAO.getAllCustomers());
        populateTypeComboBox();
        contact.setItems(contactDAO.getAllContacts());
        setupEditOrNew();
    }

    /**
     * populate type combo box with types
     */
    private void populateTypeComboBox() {
        ObservableList<String> allTypes = FXCollections.observableArrayList();
        allTypes.addAll(
                "Required",
                "Important",
                "Optional",
                "Should be an email"
        );
        type.setItems(allTypes);
    }

    /**
     * Convert 8am-10pm Eastern Time to local system timezone in 15 minute increments
     * @return ObservableList<LocalTime> of business hours in local timezone
     */
    private ObservableList<LocalTime> setBusinessHours(){
        ObservableList<LocalTime> businessHours = FXCollections.observableArrayList();
        LocalTime startHours = LocalTime.of(8,00).minusHours(Time.offsetTo("GMT-05:00"));
        for(int i = 0; i<=56;i++){
            businessHours.add(startHours);
            startHours = startHours.plusMinutes(15);
        }
        return businessHours;
    }

    /**
     * Determine if creating a new Appointment or editing an existing Appointment
     * Change scene label to reflect New vs Edit
     * update fields with existing info if not new
     */
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

    /**
     * Check if Appointment is valid to be saved
     * Alert if Appointment is not valid
     * @param actionEvent
     */
    @FXML
    private void onSaveButton(ActionEvent actionEvent) {
        //TODO check for conflicting existing appointment
        if(validateInput()){
            if(validateAppointment()){
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
            } else {
                Alert.warn("Appointment Conflict",
                        "Appointment Conflict",
                        "This appointment conflicts with an existing appointment for "
                                + customer.getValue().getName());
            }
        }
    }

    /**
     * Save provided Appointment to database
     * update if existing
     * insert if new
     */
    private void saveAppointment(){
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
    }

    /**
     * Validate input from form
     * Alert if input is invalid
     * @return
     */
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
        if(getEndDateTime().isBefore(getStartDateTime())
                ||getEndDateTime().equals(getStartDateTime())){
            Alert.invalidInput("End Time is not after Start Time and");
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
     * lambda expression - easily/simply allows filtering appointments that conflict with current Appointment
     *
     * Check appointment date/time to make sure there is no conflict
     * only compares against existing appointments for selected customer
     * @return boolean
     */
    private boolean validateAppointment(){
        LocalDateTime startDateTime = getStartDateTime();
        LocalDateTime endDateTime = getEndDateTime();
        ObservableList<Appointment> conflictingAppointments;
        ObservableList<Appointment> allCustomerAppointments =
                appointmentDAO.getAllCustomerAppointments(customer.getValue().getID());
        conflictingAppointments = allCustomerAppointments.stream()
                .filter(a -> a.getID() != determineAppID())
                .filter(a -> ((startDateTime.isAfter(a.getStart()) || startDateTime.isEqual(a.getStart()))
                                && startDateTime.isBefore(a.getEnd()))
                        || (endDateTime.isAfter(a.getStart()) && (endDateTime.isBefore(a.getEnd())
                                || endDateTime.isEqual(a.getEnd())))
                        || (startDateTime.isBefore(a.getStart()) && endDateTime.isAfter(a.getEnd()))
                        || (startDateTime.isEqual(a.getStart()) && endDateTime.isEqual(a.getEnd())))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return conflictingAppointments.isEmpty();
    }

    /**
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

    /**
     * Determine correct Appointment ID
     * @return int Appointments ID if it already exists, 0 if it is new
     */
    private int determineAppID(){
        if (!isNew){
            return Integer.parseInt(appointmentID.getText());
        }
        return 0;
    }

    /**
     * Return to Appointment scene after confirming intention
     * @param actionEvent
     */
    @FXML
    private void returnToAppointmentsButton(ActionEvent actionEvent){
        if (utils.Alert.confirm("Return",
                "Return to Appointments?",
                "Are you sure you want to return to the Appointments menu without saving?")) {
            DBConnection.closeConnection();
            changeScene(actionEvent, "Appointments");
        }
    }

    /**
     * change scene
     * @param actionEvent
     * @param sceneName
     */
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
