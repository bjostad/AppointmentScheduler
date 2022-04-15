package DAO;

import javafx.collections.ObservableList;
import model.Appointment;

/**
 * @author BJ Bjostad
 */
public interface AppointmentDAO {
    public ObservableList<Appointment> getAllAppointments();
    public boolean addAppointment(Appointment appointment);
    public boolean updateAppointment(Appointment appointment);
    public boolean deleteAppointment(int appointmentID);
}
