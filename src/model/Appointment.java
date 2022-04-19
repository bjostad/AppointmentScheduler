package model;

import java.time.LocalDateTime;

/**
 * @author BJ Bjostad
 */
public class Appointment {

    private int ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerID;
    private String customerName;
    private int userID;
    private String userName;
    private int contactID;
    private String contactName;

    /**
     * Appointment constuctor
     * @param ID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param customerName
     * @param userID
     * @param userName
     * @param contactID
     * @param contactName
     */
    public Appointment(int ID, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, int customerID, String customerName,
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

    /**
     * get Appointment ID
     * @return int
     */
    public int getID() {
        return ID;
    }

    /**
     * get Appointment title
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * get Appointment description
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * get Appointment location
     * @return String
     */
    public String getLocation() {
        return location;
    }

    /**
     * get Appointment type
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * get Appointment start date and time
     * @return localDateTime
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * get Appointment end data and time
     * @return String localDateTime
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * get Appointment Customer ID
     * @return int
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * get Appointment User ID
     * @return int
     */
    public int getUserID() {
        return userID;
    }

    /**
     * get Appointment Contact ID
     * @return int
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * get Appointment User name
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * get Appointment Customer name
     * @return String
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * get Appointment Contact name
     * @return String
     */
    public String getContactName(){
        return contactName;
    }
}