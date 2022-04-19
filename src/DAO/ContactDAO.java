package DAO;

import javafx.collections.ObservableList;
import model.Contact;

/**
 * @author BJ Bjostad
 */
public interface ContactDAO {

    /**
     * Get all Contacts from database
     * @return ObservableList<Contact>
     */
    ObservableList<Contact> getAllContacts();

    /**
     * retrieve Contact based on contact ID
     * @param selectedContact
     * @return Contact
     */
    Contact getContactByID(int selectedContact);
}
