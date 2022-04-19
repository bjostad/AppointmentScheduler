package DAO;

import javafx.collections.ObservableList;
import model.Appointment;

/**
 * @author BJ Bjostad
 */
public interface AppointmentDAO {

    /**
     * retrieve all Appointments from the database
     * includes contact, user, and customer info
     * @return ObservableList<Appointment>
     */
    ObservableList<Appointment> getAllAppointments();

    /**
     * retrieve all Appointments for provided customer ID from the database
     * includes contact, user, and customer info
     * @param custID
     * @return ObservableList<Appointment>
     */
    ObservableList<Appointment> getAllCustomerAppointments(int custID);

    /**
     * inserts provided Appointment into database
     * @param appointment
     * @return true if successful
     */
    boolean addAppointment(Appointment appointment);

    /**
     * updates provided Appointment in database
     * @param appointment
     * @return true if successful
     */
    boolean updateAppointment(Appointment appointment);

    /**
     * deletes provided Appointment from database
     * @param appointmentID
     * @return true if successful
     */
    boolean deleteAppointment(int appointmentID);
}
