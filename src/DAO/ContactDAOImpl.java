package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author BJ Bjostad
 */
public class ContactDAOImpl implements ContactDAO{

    /**
     * Get all Contacts from database
     * @return ObservableList<Contact>
     */
    @Override
    public ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String getAllContactsSQL = "SELECT * FROM CONTACTS ";
        try{
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

    /**
     * retrieve Contact based on contact ID
     * @param selectedContact
     * @return Contact
     */
    @Override
    public Contact getContactByID(int selectedContact){
        String getAllContactsSQL = "SELECT * FROM CONTACTS WHERE CONTACT_ID = ? ";
        try{
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(getAllContactsSQL);
            pStatement.setInt(1,selectedContact);
            ResultSet results = pStatement.executeQuery();
            while(results.next()){
                int ID = results.getInt("Contact_ID");
                String name = results.getString("Contact_Name");
                String email = results.getString("Email");
                return new Contact(ID,name,email);
            }
        } catch (SQLException e) {
            //TODO Error handling
            System.out.println(e);
        }
        return null;
    }
}
