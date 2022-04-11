package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author BJ Bjostad
 */
public class ContactDAOImpl {

    public static ObservableList<Contact> getAllContacts(){
        try{
            ObservableList<Contact> contacts = FXCollections.observableArrayList();
            String getAllContactsSQL = "SELECT * FROM Contacts ";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(getAllContactsSQL);
            ResultSet results = pStatement.executeQuery();
            while(results.next()){
                int ID = results.getInt("Contact_ID");
                String name = results.getString("Contact_Name");
                String email = results.getString("Email");
                contacts.add(new Contact(ID,name,email));
            }
            return contacts;
        } catch (SQLException e) {
            //TODO Error handling
            System.out.println(e);
        }
        return null;
    }
}
