package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author BJ Bjostad
 */
public class CustomerDAOImpl {

    public static ObservableList<Customer> getAllCustomer() throws SQLException, Exception {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String getAllCustomersSQLStatement = "SELECT * FROM CUSTOMERS AS CUSTS" +
                "LEFT JOIN FIRST_LEVEL_DIVISIONS AS FLD ON CUSTS.DIVISION_ID = FLD.DIVISION_ID" +
                "LEFT JOIN COUNTRIES AS CTRY ON FLD.COUNTRY_ID = CTRY.COUNTRY_ID";
        Query.sendQuery(getAllCustomersSQLStatement);
        ResultSet results = Query.getResults();
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
        }
        return customers;
    }
}
