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
import java.util.ResourceBundle;

/**
 * Customer controller
 *
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

    /**
     * initialize customers scene
     * populate customer table and country combobox for use
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection.makeConnection();
        populateCustomerTable();
        populateCountryComboBox();
    }

    /**
     * Populate customer table with all existing customers
     */
    private void populateCustomerTable() {
        ObservableList<Customer> customers = customerDAO.getAllCustomers();
        customerTable.setItems(customers);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        firstLevelDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
    }

    /**
     * Populate country combobox with countries from database
     */
    private void populateCountryComboBox() {
        country.setItems(customerDAO.getAllCountries());
    }

    /**
     * Populate division with proper entries based on selected country
     * @param actionEvent
     */
    @FXML
    private void onCountrySelected(ActionEvent actionEvent) {
        String country = (String)this.country.getSelectionModel().getSelectedItem();
        firstLevelDivision.setItems((customerDAO.getCountryDivisions(country)));
    }

    /**
     * Populate update fields with selected customer
     * @param mouseEvent
     */
    @FXML
    private void onCustomerTableClicked(MouseEvent mouseEvent) {
        if(customerTable.getSelectionModel().getSelectedItem() != null){
            Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
            customerID.setText(String.valueOf(selectedCustomer.getID()));
            customerName.setText(selectedCustomer.getName());
            phoneNumber.setText(selectedCustomer.getPhoneNumber());
            address.setText(selectedCustomer.getAddress());
            postalCode.setText(selectedCustomer.getPostalCode());
            country.getSelectionModel().select(selectedCustomer.getCountry());
            firstLevelDivision.getSelectionModel().select(selectedCustomer.getDivision());
            saveButton.setText("Update Customer");
        }
    }

    /**
     * Create new customer or save changes to existing customer
     * confirm before saving
     * @param actionEvent
     */
    @FXML
    private void onSaveCustomerButton(ActionEvent actionEvent) {
        String divisionName = (String)firstLevelDivision.getSelectionModel().getSelectedItem();
        int divisionID = customerDAO.getDivisionID(divisionName);
        boolean isNew = (customerID.getText().charAt(0) == 'W');
        int id = 0;
        if(!isNew){
          id = Integer.parseInt(customerID.getText());
        }
        if(validateInput()){
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
        }
        populateCustomerTable();
    }

    /**
     * Validate input, present error for input if its invalid
     * @return true if all input is valid
     */
    private boolean validateInput(){
        boolean isValid = true;
        if(customerName.getText().isBlank()){
            Alert.invalidInput("Customer Name");
            isValid = false;
        }
        if(phoneNumber.getText().isBlank()){
            Alert.invalidInput("Phone Number");
            isValid = false;
        }
        if(address.getText().isBlank()){
            Alert.invalidInput("Address");
            isValid = false;
        }
        if(postalCode.getText().isBlank()){
            Alert.invalidInput("Postal Code");
            isValid = false;
        }
        if(country.getSelectionModel().isEmpty()){
            Alert.invalidInput("Country");
            isValid = false;
        }
        if(firstLevelDivision.getSelectionModel().isEmpty()){
            Alert.invalidInput("First-level Division");
            isValid = false;
        }
        return isValid;
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
                        "Customer "+selectedCustomerName+" has " + hasAppts + " actively assigned appointments. " +
                                "Cancel all appointments before deleting customer.");
            } else {
                if (Alert.confirm("Delete Customer",
                        "Delete Customer "+selectedCustomerName+"?",
                        "Are you sure?")){
                    if(customerDAO.deleteCustomer(selectedCustomerID)){
                        Alert.warn("Customer Deleted",
                                selectedCustomerName +" has been deleted.",
                                "Customer "+selectedCustomerID+" was successfully deleted.");
                    }
                    onNewCustomerButton(actionEvent);
                    populateCustomerTable();
                }
            }
        } else {
            Alert.warn("Invalid Selection",
                    "Invalid Selection",
                    "Please select a Customer.");
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

    /**
     * Alert and confirm before returning to appointment menu
     * @param actionEvent
     * @throws Exception
     */
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