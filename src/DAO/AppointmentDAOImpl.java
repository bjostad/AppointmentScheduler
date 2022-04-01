package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author BJ Bjostad
 */
public class AppointmentDAOImpl {

    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String getAllAppointmentsSQLStatement = "SELECT * FROM APPOINTMENTS LEFT JOIN CONTACTS ON APPOINTMENTS.CONTACT_ID = CONTACTS.CONTACT_ID LEFT JOIN USERS ON APPOINTMENTS.USER_ID = USERS.USER_ID left join CUSTOMERS ON APPOINTMENTS.CUSTOMER_ID = CUSTOMERS.CUSTOMER_ID";
        Query.sendQuery(getAllAppointmentsSQLStatement);
        ResultSet results = Query.getResults();
        while(results.next()){
            int id = results.getInt("Appointment_ID");
            String title = results.getString("Title");
            String description = results.getString("Description");
            String location = results.getString("Location");
            String type = results.getString("Type");
            Timestamp start = results.getTimestamp("Start");
            Timestamp end = results.getTimestamp("End");
            int customerID = results.getInt("Customer_ID");
            String customerName = results.getString("Customer_Name");
            int userID = results.getInt("User_ID");
            String userName = results.getString("User_Name");
            int contactID = results.getInt("Contact_ID");
            String contactName = results.getString("Contact_Name");
            appointments.add(new Appointment(id,title,description,location,type,start,end,customerID,customerName,userID,userName,contactID,contactName));
        }
        return appointments;
    }
}
