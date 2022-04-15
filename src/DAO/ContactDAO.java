package DAO;

import javafx.collections.ObservableList;
import model.Contact;

/**
 * @author BJ Bjostad
 */
public interface ContactDAO {
    public ObservableList<Contact> getAllContacts();
    public Contact getContactByID(int selectedContact);
}
