package DAO;

import javafx.collections.ObservableList;
import model.Appointment;

/**
 * @author BJ Bjostad
 */
public interface AppointmentDAO {
    public ObservableList<Appointment> getAllAppointments();
    public ObservableList<Appointment> getAllCustomerAppointments(int customerID);
    public boolean addAppointment(Appointment appointment);
    public boolean updateAppointment(Appointment appointment);
    public boolean deleteAppointment(int appointmentID);
}
