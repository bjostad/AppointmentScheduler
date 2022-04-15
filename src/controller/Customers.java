package controller;

import DAO.CustomerDAO;
import DAO.CustomerDAOImpl;
import DAO.DBConnection;
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
import model.Customer;

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
    private Button createNewCustomerButton;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox<String> country;
    @FXML
    private ComboBox<String> firstLevelDivision;

    CustomerDAO customerDAO = new CustomerDAOImpl();


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
            if(customerID.getText().charAt(0) == 'W'){
                Customer customer = new Customer(0,
                        customerName.getText(),
                        address.getText(),
                        postalCode.getText(),
                        phoneNumber.getText(),
                        divisionID,
                        divisionName,
                        0,
                        null);
                customerDAO.createNewCustomer(customer);
            } else {
                Customer customer = new Customer(Integer.parseInt(customerID.getText()),
                        customerName.getText(),
                        address.getText(),
                        postalCode.getText(),
                        phoneNumber.getText(),
                        divisionID,
                        divisionName,
                        0,
                        null);
                customerDAO.updateCustomer(customer);
            }
            populateCustomerTable();
        } catch (Exception e){
            //TODO error handling
            System.out.println(e);
        }
    }

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

    @FXML
    private void onDeleteCustomerButton(ActionEvent actionEvent) {
        //TODO check for appointments under customer, do not delete customer if appointments exist
        try {
            customerDAO.deleteCustomer(customerID.getText());
            onNewCustomerButton(actionEvent);
            populateCustomerTable();
        } catch (Exception e){
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
    private void returnToAppointmentsButton(ActionEvent actionEvent) throws Exception {
        DBConnection.closeConnection();
        changeScene(actionEvent,"Appointments");
    }
}