package DAO;

import javafx.collections.ObservableList;
import model.Customer;

/**
 * @author BJ Bjostad
 */
public interface CustomerDAO {
    public ObservableList<Customer> getAllCustomers();
    public Customer getCustomerByID(int selectedCustomerID);
    public ObservableList<String> getAllCountries();
    public int getCountryID(String country);
    public int getDivisionID(String division);
    public ObservableList<String> getCountryDivisions(String country);
    public boolean updateCustomer(Customer customer);
    public boolean createNewCustomer(Customer customer);
    public boolean deleteCustomer(int customerID);
}
