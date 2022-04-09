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
    private String customerName;
    private int userID;
    private String userName;
    private int contactID;
    private String contactName;

    public Appointment(int ID, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, int customerID, String customerName,
                       int userID, String userName, int contactID, String contactName) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.customerName = customerName;
        this.userID = userID;
        this.userName = userName;
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public Timestamp getStart() {
        return start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }

    public int getContactID() {
        return contactID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getUserName() {
        return userName;
    }

    public String getContactName() {
        return contactName;
    }
}
