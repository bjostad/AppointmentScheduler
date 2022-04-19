package DAO;

import javafx.collections.ObservableList;
import model.Customer;

/**
 * @author BJ Bjostad
 */
public interface CustomerDAO {
    /**
     * get all Customers from database
     * @return ObservableList<Customer>
     */
    ObservableList<Customer> getAllCustomers();

    /**
     * get Customer by provided customer id
     * @param selectedCustomerID id of customer
     * @return Customer
     */
    Customer getCustomerByID(int selectedCustomerID);

    /**
     * retrieve list of all countries from database
     * @return ObservableList<String>
     */
    ObservableList<String> getAllCountries();

    /**
     * retrieve country ID from provided country name
     * @param country country name
     * @return int country ID
     */
    int getCountryID(String country);

    /**
     * retrieve list of all divisions of provided country
     * @param country country name
     * @return ObservableList<String> divisions
     */
    ObservableList<String> getCountryDivisions(String country);

    /**
     * retrieve division ID from provided division name
     * @param division division name
     * @return int division ID
     */
    int getDivisionID(String division);

    /**
     * update provided Customer in the database
     * @param customer Customer
     * @return true if successful
     */
    boolean updateCustomer(Customer customer);

    /**
     * inserts provided Customer into database
     * @param customer Customer
     * @return true if successful
     */
    boolean createNewCustomer(Customer customer);

    /**
     * delete Customer by provided customer ID
     * @param customerID customer ID
     * @return true if successful
     */
    boolean deleteCustomer(int customerID);
}
