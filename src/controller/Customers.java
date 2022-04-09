package controller;

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
import java.util.ResourceBundle;

/**
 * @author BJ Bjostad
 */
public class Customers implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private TableView customerTable;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn phoneNumberColumn;
    @FXML
    private TableColumn addressColumn;
    @FXML
    private TableColumn postalCodeColumn;
    @FXML
    private TableColumn countryColumn;
    @FXML
    private TableColumn firstLevelDivisionColumn;
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
    private Button saveCustomerButton;
    @FXML
    private Button addNewCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private ComboBox Country;
    @FXML
    private ComboBox firstLevelDivision;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DBConnection.makeConnection();
            populateCustomerTable();



        } catch (Exception e) {

        }
    }

    private void populateCustomerTable() {
        try {
            ObservableList<Customer> customers = CustomerDAOImpl.getAllCustomer();
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
            System.out.println("Populate customer table error: "+e);
        }
    }

    private void populateGeographicAreaComboBoxes(){

    }

    private void changeScene (ActionEvent actionEvent, String sceneName) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/"+sceneName+".fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onCustomerTableClicked(MouseEvent mouseEvent) {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        customerID.setText(String.valueOf(selectedCustomer.getID()));
        customerName.setText(selectedCustomer.getName());
        phoneNumber.setText(selectedCustomer.getPhoneNumber());
        address.setText(selectedCustomer.getAddress());
        postalCode.setText(selectedCustomer.getPostalCode());
    }

    @FXML
    private void saveCustomerButton(ActionEvent actionEvent) {
    }

    @FXML
    private void addNewCustomerButton(ActionEvent actionEvent) {
    }

    @FXML
    private void deleteCustomerButton(ActionEvent actionEvent) {
    }

    @FXML
    private void returnToAppointmentsButton(ActionEvent actionEvent) throws Exception {
        DBConnection.closeConnection();
        changeScene(actionEvent,"Appointments");
    }
}
