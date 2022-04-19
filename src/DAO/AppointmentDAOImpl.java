package DAO;

import controller.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.Time;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author BJ Bjostad
 */
public class AppointmentDAOImpl implements AppointmentDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String getAllAppointmentsSQL = "SELECT * FROM APPOINTMENTS AS APPS " +
                "LEFT JOIN CONTACTS AS CONS ON APPS.CONTACT_ID = CONS.CONTACT_ID " +
                "LEFT JOIN USERS ON APPS.USER_ID = USERS.USER_ID " +
                "LEFT JOIN CUSTOMERS AS CUSTS ON APPS.CUSTOMER_ID = CUSTS.CUSTOMER_ID";
        try {
            PreparedStatement pStatement = DBConnection.getConnection()
                    .prepareStatement(getAllAppointmentsSQL);
            ResultSet results = pStatement.executeQuery();
            while (results.next()) {
                int ID = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                LocalDateTime start = Time.convertFromUTC(results.getTimestamp("Start").toLocalDateTime());
                LocalDateTime end = Time.convertFromUTC(results.getTimestamp("End").toLocalDateTime());
                int customerID = results.getInt("Customer_ID");
                String customerName = results.getString("Customer_Name");
                int userID = results.getInt("User_ID");
                String userName = results.getString("User_Name");
                int contactID = results.getInt("Contact_ID");
                String contactName = results.getString("Contact_Name");
                appointments.add(new Appointment(
                        ID, title, description, location,
                        type, start, end, customerID, customerName,
                        userID, userName, contactID, contactName));
            }
        } catch (SQLException e){
            //TODO error handling
            System.out.println(e);
        }
        return appointments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObservableList<Appointment> getAllCustomerAppointments(int custID){
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String getAllAppointmentsSQL = "SELECT * FROM APPOINTMENTS AS APPS " +
                "LEFT JOIN CONTACTS AS CONS ON APPS.CONTACT_ID = CONS.CONTACT_ID " +
                "LEFT JOIN USERS ON APPS.USER_ID = USERS.USER_ID " +
                "LEFT JOIN CUSTOMERS AS CUSTS ON APPS.CUSTOMER_ID = CUSTS.CUSTOMER_ID " +
                "WHERE CUSTS.Customer_ID = ?";
        try {
            PreparedStatement pStatement = DBConnection.getConnection()
                    .prepareStatement(getAllAppointmentsSQL);
            pStatement.setInt(1, custID);
            ResultSet results = pStatement.executeQuery();
            while (results.next()) {
                int ID = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                LocalDateTime start = Time.convertFromUTC(results.getTimestamp("Start").toLocalDateTime());
                LocalDateTime end = Time.convertFromUTC(results.getTimestamp("End").toLocalDateTime());
                int customerID = results.getInt("Customer_ID");
                String customerName = results.getString("Customer_Name");
                int userID = results.getInt("User_ID");
                String userName = results.getString("User_Name");
                int contactID = results.getInt("Contact_ID");
                String contactName = results.getString("Contact_Name");
                appointments.add(new Appointment(
                        ID, title, description, location,
                        type, start, end, customerID, customerName,
                        userID, userName, contactID, contactName));
            }
        } catch (SQLException e){
            //TODO error handling
            System.out.println(e);
        }
        return appointments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAppointment(Appointment appointment){

        String createAppointmentSQL = "INSERT INTO APPOINTMENTS(TITLE,DESCRIPTION,LOCATION,TYPE," +
                                   "START,END,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY," +
                                   "CUSTOMER_ID,USER_ID,CONTACT_ID) " +
                                   "VALUES(?,?,?,?,?,?,now(),?,now(),?,?,?,?)";
        try{
            PreparedStatement pStatement = DBConnection.getConnection()
                    .prepareStatement(createAppointmentSQL);
            pStatement.setString(1, appointment.getTitle());
            pStatement.setString(2, appointment.getDescription());
            pStatement.setString(3, appointment.getLocation());
            pStatement.setString(4, appointment.getType());
            pStatement.setTimestamp(5, Timestamp.valueOf(Time.convertToUTC(appointment.getStart())));
            pStatement.setTimestamp(6, Timestamp.valueOf(Time.convertToUTC(appointment.getEnd())));
            pStatement.setString(7, Login.currentUser.getUsername());
            pStatement.setString(8, Login.currentUser.getUsername());
            pStatement.setInt(9,appointment.getCustomerID());
            pStatement.setInt(10,appointment.getUserID());
            pStatement.setInt(11,appointment.getContactID());
            pStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            //TODO error handling
            System.out.println(e);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateAppointment(Appointment appointment) {
        String updateAppointmentSQL = "UPDATE APPOINTMENTS SET TITLE = ?,DESCRIPTION=?,LOCATION=?," +
                                      "TYPE=?,START=?, END=?,LAST_UPDATE=NOW(),LAST_UPDATED_BY=?," +
                                      "CUSTOMER_ID=?, USER_ID=?,CONTACT_ID=? " +
                                      "WHERE APPOINTMENT_ID = ?";
        try{
            PreparedStatement pStatement = DBConnection.getConnection()
                    .prepareStatement(updateAppointmentSQL);
            pStatement.setString(1, appointment.getTitle());
            pStatement.setString(2, appointment.getDescription());
            pStatement.setString(3, appointment.getLocation());
            pStatement.setString(4, appointment.getType());
            pStatement.setTimestamp(5, Timestamp.valueOf(Time.convertToUTC(appointment.getStart())));
            pStatement.setTimestamp(6, Timestamp.valueOf(Time.convertToUTC(appointment.getEnd())));
            pStatement.setString(7, Login.currentUser.getUsername());
            pStatement.setInt(8,appointment.getCustomerID());
            pStatement.setInt(9,appointment.getUserID());
            pStatement.setInt(10,appointment.getContactID());
            pStatement.setInt(11,appointment.getID());
            pStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            //TODO error handling
            System.out.println(e);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAppointment(int appointmentID) {
        String deleteAppointmentSQL = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        try{
            PreparedStatement pStatement = DBConnection.getConnection()
                    .prepareStatement(deleteAppointmentSQL);
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
