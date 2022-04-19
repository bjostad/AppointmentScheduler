package controller;

import DAO.AppointmentDAO;
import DAO.AppointmentDAOImpl;
import DAO.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import utils.Alert;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Appointments controller
 *
 * @author BJ Bjostad
 */
public class Appointments implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startDateTimeColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endDateTimeColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    @FXML
    private TableColumn<Appointment, String> contactColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;
    @FXML
    private TableColumn<Appointment, Integer> userIdColumn;
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private RadioButton filterAll;
    @FXML
    private RadioButton filterSevenDays;
    @FXML
    private RadioButton filterMonth;

    private static AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private static Appointment selectedAppointment = null;

    /**
     * Create new database connection
     * Populate appointment table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            DBConnection.makeConnection();
            populateAppointmentTable();
    }

    /**
     * Get selected Appointment
     * @return Appointment
     */
    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    /**
     * Obtain filtered appointment list and populate table
     */
    private void populateAppointmentTable(){
        ObservableList<Appointment> filteredAppointments = filterAppointments();
        appointmentTable.setItems(filteredAppointments);
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
    }

    /**
     * lambda expression - easily/simply allows filtering appointments to next week or month depending on selection
     *
     * Filter Appointments based on selected filter radio button
     * @return ObservableList<Appointment> Appointments
     */
    private ObservableList<Appointment> filterAppointments(){
        ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
        if(filterSevenDays.isSelected()){
            return allAppointments.stream()
                    .filter(s -> s.getStart().isAfter((LocalDateTime.now())))
                    .filter(e -> e.getStart().toLocalDate()
                            .isBefore(LocalDateTime.now().toLocalDate().plusDays(8)))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        } else if(filterMonth.isSelected()){
            return allAppointments.stream()
                    .filter(s -> s.getStart().isAfter((LocalDateTime.now())))
                    .filter(a -> a.getStart().toLocalDate()
                            .isBefore(LocalDateTime.now().toLocalDate().plusMonths(1).plusDays(1)))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        } else {
            return allAppointments;
        }
    }

    /**
     * Clear selectedAppointment and load NewEditAppointment
     * @param actionEvent
     */
    @FXML
    private void onNewAppointmentButton(ActionEvent actionEvent) {
        selectedAppointment = null;
        DBConnection.closeConnection();
        changeScene( actionEvent, "NewEditAppointment");
    }

    /**
     * Set selectedAppointment and load NewEditAppointment.
     * @param actionEvent
     */
    @FXML
    private void onEditAppointmentButton(ActionEvent actionEvent) {
        selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            DBConnection.closeConnection();
            changeScene( actionEvent, "NewEditAppointment");
        } else {
            Alert.warn("Invalid Selection",
                    "Invalid Selection",
                    "Please select an appointment.");
        }
    }

    /**
     * Set selectedAppointment and delete it after confirmation
     * @param actionEvent
     */
    @FXML
    private void onDeleteAppointmentButton(ActionEvent actionEvent) {
        selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            if (Alert.confirm("Cancel Appointment",
                "Cancel Appointment",
                "Are you sure you want to cancel this appointment?")){
                if(appointmentDAO.deleteAppointment(selectedAppointment.getID())){
                    Alert.info("Appointment Canceled",
                            "Appointment Canceled Successfully",
                            "Appointment "+selectedAppointment.getID()+
                                    " of type "+selectedAppointment.getType()+" has been canceled.");
                }
                populateAppointmentTable();
            }
        } else {
            Alert.warn("Invalid Selection",
                    "Invalid Selection",
                    "Please select an appointment.");
        }
    }

    /**
     * Load Customers scene
     * @param actionEvent
     */
    @FXML
    private void onCustomerButton(ActionEvent actionEvent) {
        DBConnection.closeConnection();
        changeScene( actionEvent, "Customers");
    }

    /**
     * Load Reports Scene
     * @param actionEvent
     */
    @FXML
    private void onReportsButton(ActionEvent actionEvent) {
        DBConnection.closeConnection();
        changeScene( actionEvent, "Reports");
    }

    /**
     * repopulate appointmentTable to show all Appointments
     * @param actionEvent
     */
    @FXML
    private void onFilterAll(ActionEvent actionEvent) {
        populateAppointmentTable();
    }

    /**
     * repopulate appointmentTable to show the next weeks Appointments
     * @param actionEvent
     */
    @FXML
    private void onFilterSevenDays(ActionEvent actionEvent) {
        populateAppointmentTable();
    }

    /**
     * repopulate appointmentTable to show the next months Appointments
     * @param actionEvent
     */
    @FXML
    private void onFilterMonth(ActionEvent actionEvent) {
        populateAppointmentTable();
    }

    /**
     * Close application after confirming intent
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    private void onExitButton(ActionEvent actionEvent) throws Exception {
        if (utils.Alert.confirm("Exit",
                "Close Application",
                "Are you sure you want to close the application?")) {
            //close DB connection before exit
            DBConnection.closeConnection();
            System.exit(0);
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
