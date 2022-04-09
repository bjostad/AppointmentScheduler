package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author BJ Bjostad
 */
public class AppointmentDAOImpl {

    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String getAllAppointmentsSQL = "SELECT * FROM APPOINTMENTS AS APPS " +
                "LEFT JOIN CONTACTS AS CONS ON APPS.CONTACT_ID = CONS.CONTACT_ID " +
                "LEFT JOIN USERS ON APPS.USER_ID = USERS.USER_ID " +
                "LEFT JOIN CUSTOMERS AS CUSTS ON APPS.CUSTOMER_ID = CUSTS.CUSTOMER_ID";
        PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(getAllAppointmentsSQL);
        ResultSet results = pStatement.executeQuery();
        while(results.next()){
            int ID = results.getInt("Appointment_ID");
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
            appointments.add(new Appointment(ID,title,description,location,type,start,end,customerID,customerName,userID,userName,contactID,contactName));
        }
        return appointments;
    }
}
