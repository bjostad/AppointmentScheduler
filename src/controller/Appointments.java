package controller;

import DAO.AppointmentDAO;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;
import utils.Alert;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
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

    private static AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private static Appointment selectedAppointment = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DBConnection.makeConnection();
            populateAppointmentTable();

        } catch (Exception e) {
            //TODO error handling
            System.out.println(e);
        }
    }

    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    private void populateAppointmentTable(){
        try{
            ObservableList<Appointment> appointments = appointmentDAO.getAllAppointments();
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

    @FXML
    private void onAppointmentSelected(MouseEvent mouseEvent) {
        try{
            selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        } catch (Exception e) {
            //TODO error handling
            System.out.println(e);
        }
        //TODO remove along with fxml element
    }

    @FXML
    private void onNewAppointmentButton(ActionEvent actionEvent) {
        try {
            selectedAppointment = null;
            DBConnection.closeConnection();
            changeScene( actionEvent, "NewEditAppointment");

        } catch (Exception e) {
            //TODO error handling
            System.out.println(e);
        }
    }

    @FXML
    private void onEditAppointmentButton(ActionEvent actionEvent) {
        try{
            selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
            if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
                try {
                    DBConnection.closeConnection();
                    changeScene( actionEvent, "NewEditAppointment");

                } catch (Exception e) {
                    //TODO error handling
                    System.out.println(e);
                }
            } else {
                Alert.warn("Invalid Selection",
                        "Invalid Selection",
                        "Please select an appointment.");
            }
        } catch (Exception e) {
            //TODO error handling
            System.out.println(e);
        }
    }

    @FXML
    private void onDeleteAppointmentButton(ActionEvent actionEvent) {
        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            if (Alert.confirm("Cancel Appointment",
                    "Cancel Appointment",
                    "Are you sure you want to cancel this appointment?")){
                try {
                    if(appointmentDAO.deleteAppointment(selectedAppointment.getID())){
                        //TODO add type to message
                        Alert.info("Appointment Deleted",
                                "Appointment Deleted Successfully",
                                "Appointment "+selectedAppointment.getID()+
                                        " with type "+selectedAppointment.getType()+" has been deleted.");
                    }
                    populateAppointmentTable();
                } catch (Exception e){
                    //TODO error handling
                    System.out.println(e);
                }
            }
        } else {
            Alert.warn("Invalid Selection",
                    "Invalid Selection",
                    "Please select an appointment.");
        }
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
        if (utils.Alert.confirm("Exit",
                "Close Application",
                "Are you sure you want to close the application?")) {
            //close DB connection before exit
            DBConnection.closeConnection();
            System.exit(0);
        }
    }
}
