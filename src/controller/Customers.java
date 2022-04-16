package controller;

import DAO.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import utils.Alert;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author BJ Bjostad
 */
public class Customers implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> idColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private TableColumn<Customer, String> countryColumn;
    @FXML
    private TableColumn<Customer, String> firstLevelDivisionColumn;
    @FXML
    private TextField customerID;
    @FXML
    private TextField customerName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;
    @FXML
    private TextField postalCode;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox<String> country;
    @FXML
    private ComboBox<String> firstLevelDivision;

    CustomerDAO customerDAO = new CustomerDAOImpl();
    AppointmentDAO appointmentDAO = new AppointmentDAOImpl();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DBConnection.makeConnection();
            populateCustomerTable();
            populateCountryComboBox();
        } catch (Exception e) {
            //TODO real error handling
        }
    }

    private void populateCustomerTable() {
        try {
            ObservableList<Customer> customers = customerDAO.getAllCustomers();
            System.out.println("customer"+ customers);
            customerTable.setItems(customers);
            idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            firstLevelDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        } catch (Exception e) {
            //TODO real error handling
            System.out.println("Populate customer table error: "+e);
        }
    }

    private void populateCountryComboBox() {
        try {
            country.setItems(customerDAO.getAllCountries());
        } catch (Exception e) {
            //TODO real error handling
            System.out.println(e);
        }
    }

    @FXML
    private void onCountrySelected(ActionEvent actionEvent) {
        try {
            String country = (String)this.country.getSelectionModel().getSelectedItem();
            firstLevelDivision.setItems((customerDAO.getCountryDivisions(country)));
        } catch (Exception e){
            //TODO error handling
        }
    }

    @FXML
    private void onCustomerTableClicked(MouseEvent mouseEvent) {
        try{
            Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
            customerID.setText(String.valueOf(selectedCustomer.getID()));
            customerName.setText(selectedCustomer.getName());
            phoneNumber.setText(selectedCustomer.getPhoneNumber());
            address.setText(selectedCustomer.getAddress());
            postalCode.setText(selectedCustomer.getPostalCode());
            country.getSelectionModel().select(selectedCustomer.getCountry());
            firstLevelDivision.getSelectionModel().select(selectedCustomer.getDivision());
            saveButton.setText("Update Customer");

        } catch (Exception e){
            //TODO actual error handling
            System.out.println(e);
        }
    }


    @FXML
    private void onSaveCustomerButton(ActionEvent actionEvent) {
        try{
            String divisionName = (String)firstLevelDivision.getSelectionModel().getSelectedItem();
            int divisionID = customerDAO.getDivisionID(divisionName);
            boolean isNew = (customerID.getText().charAt(0) == 'W');
            int id = 0;
            if(!isNew){
              id = Integer.parseInt(customerID.getText());
            }
            Customer customer = new Customer(id,
                    customerName.getText(),
                    address.getText(),
                    postalCode.getText(),
                    phoneNumber.getText(),
                    divisionID,
                    divisionName,
                    0,
                    null);
            if(isNew){
                if(Alert.confirm("Create New Customer",
                        "Create New Customer "+customer.getName()+"?",
                        "Are you sure you want to create a new customer?")){
                    customerDAO.createNewCustomer(customer);
                }

            } else {
                if(Alert.confirm("Update Customer",
                        "Update Customer "+customer.getName()+"?",
                        "Are you sure you want to update?")){
                    customerDAO.updateCustomer(customer);
                }
            }
            populateCustomerTable();
        } catch (Exception e){
            //TODO error handling
            System.out.println(e);
        }
    }

    /**
     * Setup customer form for creation of a new customer
     * @param actionEvent
     */
    @FXML
    private void onNewCustomerButton(ActionEvent actionEvent) {
        customerID.setText("Will be assigned when created");
        customerName.setText("");
        phoneNumber.setText("");
        address.setText("");
        postalCode.setText("");
        country.getSelectionModel().clearSelection();
        firstLevelDivision.getSelectionModel().clearSelection();
        saveButton.setText("Create New Customer");
    }

    /**
     * Remove selected customer
     * verify customer has no currently assigned appointments
     * @param actionEvent
     */
    @FXML
    private void onDeleteCustomerButton(ActionEvent actionEvent) {
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            int hasAppts = 0;
            String selectedCustomerName = customerTable.getSelectionModel().getSelectedItem().getName();
            int selectedCustomerID = customerTable.getSelectionModel().getSelectedItem().getID();
            ObservableList<Appointment> appointments = appointmentDAO.getAllAppointments();
            for(Appointment a:appointments){
                if(a.getCustomerID() == selectedCustomerID){
                    hasAppts++;
                }
            }
            if(hasAppts > 0) {
                Alert.warn("Action Cancelled",
                        "Unable To Delete "+ selectedCustomerName,
                        "Customer "+selectedCustomerName+" has " + hasAppts + " actively assigned appointment. " +
                                "Cancel all appointments before deleting customer.");
            } else {
                if (Alert.confirm("Delete Customer",
                        "Delete Customer "+selectedCustomerName+"?",
                        "Are you sure?")){
                    try {
                        customerDAO.deleteCustomer(selectedCustomerID);
                        onNewCustomerButton(actionEvent);
                        populateCustomerTable();
                    } catch (Exception e){
                        //TODO error handling
                        System.out.println(e);
                    }
                }
            }
        } else {
            Alert.warn("Invalid Selection",
                    "Invalid Selection",
                    "Please select a Customer.");
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
    private void returnToAppointmentsButton(ActionEvent actionEvent) throws Exception {
        if (Alert.confirm("Return",
                "Return to Appointments?",
                "Are you sure you want to return to the Appointment menu?")){
            DBConnection.closeConnection();
            changeScene(actionEvent,"Appointments");
        }
    }
}