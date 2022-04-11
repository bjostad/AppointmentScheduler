package DAO;

import controller.Appointments;
import controller.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
            LocalDateTime start = results.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = results.getTimestamp("End").toLocalDateTime();
            int customerID = results.getInt("Customer_ID");
            String customerName = results.getString("Customer_Name");
            int userID = results.getInt("User_ID");
            String userName = results.getString("User_Name");
            int contactID = results.getInt("Contact_ID");
            String contactName = results.getString("Contact_Name");
            appointments.add(new Appointment(
                    ID,title,description,location,
                    type,start,end,customerID,customerName,
                    userID,userName,contactID,contactName));
        }
        return appointments;
    }

    public static boolean addAppointment(Appointment appointment){
        try{
            String createCustomerSQL = "INSERT INTO APPOINTMENTS(TITLE,DESCRIPTION,LOCATION,TYPE," +
                                       "START,END,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY," +
                                       "CUSTOMER_ID,USER_ID,CONTACT_ID) " +
                                       "VALUES(?,?,?,?,now(),now(),now(),?,now(),?,?,?,?)";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(createCustomerSQL);
            pStatement.setString(1, appointment.getTitle());
            pStatement.setString(2, appointment.getDescription());
            pStatement.setString(3, appointment.getLocation());
            pStatement.setString(4,appointment.getType());
            pStatement.setString(5, Login.currentUser.getUsername());
            pStatement.setString(6, Login.currentUser.getUsername());
            pStatement.setInt(7,appointment.getCustomerID());
            pStatement.setInt(8,appointment.getUserID());
            pStatement.setInt(9,appointment.getContactID());
            pStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            //TODO error handling
            System.out.println(e);
        }
        return false;
    }

    public static boolean deleteAppointment(int appointmentID) {
        try{
            String deleteAppointmentSQL = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(deleteAppointmentSQL);
            pStatement.setString(1, String.valueOf(appointmentID));
            pStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            //TODO error handling
            System.out.println(e);
        }
        return false;
    }
}
