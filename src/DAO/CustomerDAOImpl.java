package DAO;

import controller.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author BJ Bjostad
 */
public class CustomerDAOImpl {

    public static ObservableList<Customer> getAllCustomers(){
        try{
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
        } catch (SQLException e) {
            //TODO Error handling
            System.out.println(e);
        }
        return null;
    }

    public static ObservableList<String> getAllCountries() throws SQLException {
        ObservableList<String> countries = FXCollections.observableArrayList();
        String getAllCountriesSQL = "SELECT COUNTRY FROM COUNTRIES";
        PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(getAllCountriesSQL);
        ResultSet results = pStatement.executeQuery();
        while(results.next()){
            countries.add(results.getString("COUNTRY"));
        }
        return countries;
    }

    public static int getCountryID(String country){
        try {
          String getCountryIDSQL = "SELECT COUNTRY_ID FROM COUNTRIES WHERE COUNTRY = ?";
          PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(getCountryIDSQL);
          pStatement.setString(1,country);
          ResultSet result = pStatement.executeQuery();
          while(result.next()) {
              return result.getInt("Country_ID");
          }
        } catch (SQLException e) {
            //TODO error handling
            System.out.println("Error getting country ID: "+e);
        }
        return 0;
    }

    public static int getDivisionID(String division){
        try {
            String getDivisionyIDSQL = "SELECT DIVISION_ID FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION = ?";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(getDivisionyIDSQL);
            pStatement.setString(1,division);
            ResultSet result = pStatement.executeQuery();
            while(result.next()) {
                return result.getInt("Division_ID");
            }
        } catch (SQLException e) {
            //TODO error handling
            System.out.println("Error getting divsion ID: "+e);
        }
        return 0;
    }

    public static ObservableList<String> getCountryDivisions(String country) {
        try{
            int countryID = getCountryID(country);
            ObservableList<String> divisions = FXCollections.observableArrayList();
            String getAllDivisionsSQL = "SELECT DIVISION FROM FIRST_LEVEL_DIVISIONS WHERE COUNTRY_ID = ?";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(getAllDivisionsSQL);
            pStatement.setInt(1,countryID);
            ResultSet results = pStatement.executeQuery();
            while(results.next()){
                divisions.add(results.getString("DIVISION"));
            }
            return divisions;
        } catch (SQLException e) {
            //TODO error handling
            System.out.println(e);
        }
        return null;
    }

    public static boolean updateCustomer(Customer customer){
        try{
            String updateCustomerSQL = "UPDATE CUSTOMERS SET CUSTOMER_NAME=?,ADDRESS=?,POSTAL_CODE=?,PHONE=?,LAST_UPDATE=NOW(),LAST_UPDATED_BY=?,DIVISION_ID=? WHERE CUSTOMER_ID = ?";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(updateCustomerSQL);
            pStatement.setString(1,customer.getName());
            pStatement.setString(2,customer.getAddress());
            pStatement.setString(3,customer.getPostalCode());
            pStatement.setString(4,customer.getPhoneNumber());
            pStatement.setString(5, Login.currentUser.getUsername());
            pStatement.setInt(6,customer.getDivisionID());
            pStatement.setInt(7,customer.getID());
            pStatement.executeUpdate();
            pStatement.close();
            return true;

        } catch (SQLException e) {
            //TODO error handling
            System.out.println(e);
        }
        return false;
    }

    public static boolean createNewCustomer(Customer customer){
        try{
            String createCustomerSQL = "INSERT INTO CUSTOMERS(CUSTOMER_NAME,ADDRESS,POSTAL_CODE,PHONE," +
                                       "CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,DIVISION_ID) " +
                                       "VALUES(?,?,?,?,NOW(),?,NOW(),?,?)";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(createCustomerSQL);
            pStatement.setString(1,customer.getName());
            pStatement.setString(2,customer.getAddress());
            pStatement.setString(3,customer.getPostalCode());
            pStatement.setString(4,customer.getPhoneNumber());
            pStatement.setString(5, Login.currentUser.getUsername());
            pStatement.setString(6, Login.currentUser.getUsername());
            pStatement.setInt(7,customer.getDivisionID());
            pStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            //TODO error handling
            System.out.println(e);
        }
        return false;
    }

    public static boolean deleteCustomer(String customerID){
        try{
            String deleteCustomerSQL = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(deleteCustomerSQL);
            pStatement.setString(1,(customerID));
            pStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            //TODO error handling
            System.out.println(e);
        }
        return false;
    }
}
