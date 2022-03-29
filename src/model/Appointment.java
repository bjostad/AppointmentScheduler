package model;

import java.sql.Timestamp;

/**
 * @author BJ Bjostad
 */
public class Appointment {

    private int ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int customerID;
    private int userID;
    private int contactID;

    private Appointment(int ID, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    private int getID() {
        return ID;
    }

    private String getTitle() {
        return title;
    }

    private String getDescription() {
        return description;
    }

    private String getLocation() {
        return location;
    }

    private String getType() {
        return type;
    }

    private Timestamp getStart() {
        return start;
    }

    private Timestamp getEnd() {
        return end;
    }

    private int getCustomerID() {
        return customerID;
    }

    private int getUserID() {
        return userID;
    }

    private int getContactID() {
        return contactID;
    }
}
