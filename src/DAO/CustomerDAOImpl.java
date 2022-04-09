package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author BJ Bjostad
 */
public class CustomerDAOImpl {

    public static ObservableList<Customer> getAllCustomer() throws SQLException, Exception {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String getAllCustomersSQL = "SELECT * FROM CUSTOMERS " +
                "LEFT JOIN FIRST_LEVEL_DIVISIONS ON CUSTOMERS.DIVISION_ID = FIRST_LEVEL_DIVISIONS.DIVISION_ID " +
                "LEFT JOIN COUNTRIES ON FIRST_LEVEL_DIVISIONS.COUNTRY_ID = COUNTRIES.COUNTRY_ID";
        PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(getAllCustomersSQL);
        ResultSet results = pStatement.executeQuery();
        while(results.next()){
            int ID = results.getInt("Customer_ID");
            String name = results.getString("Customer_Name");
            String address = results.getString("Address");
            String postalCode = results.getString("Postal_Code");
            String phoneNumber = results.getString("Phone");
             int divisionID = results.getInt("Division_ID");
             String division = results.getString("Division");
             int countryID = results.getInt("Country_ID");
             String country = results.getString("Country");
             customers.add(new Customer(ID,name,address,postalCode,phoneNumber,divisionID,division,countryID,country));
        }
        return customers;
    }
}
