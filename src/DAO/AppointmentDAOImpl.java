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
        String getAllAppointmentsSQLStatement = "SELECT * FROM APPOINTMENTS";
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
            int userID = results.getInt("User_ID");
            int contactID = results.getInt("Contact_ID");
            appointments.add(new Appointment(id,title,description,location,type,start,end,customerID,userID,contactID));
        }
        return appointments;
    }
}
