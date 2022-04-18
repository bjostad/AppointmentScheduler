package controller;

import DAO.*;
import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import utils.Alert;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Login implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private Label timeZoneLabel;
    @FXML
    private Label timeZoneSelectionLabel;
    @FXML
    private Button logInButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label titleLabel;

    private ResourceBundle language = ResourceBundle.getBundle("resources/language",Locale.getDefault());
    private static UserDAO userDAO = new UserDAOImpl();
    private static AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    public static User currentUser = null;

    /**
     * onExitButton
     * Confirm user wishes to exit and exit program
     * @param actionEvent
     */
    @FXML
    private void onExitButton(ActionEvent actionEvent) throws Exception {
        if (utils.Alert.confirm(language.getString("exitTitle"),language.getString("exitHeader"),language.getString("exitContent"))) {
            //close DB connection before exit
            DBConnection.closeConnection();
            System.exit(0);
        }
    }

    /**
     * Check username and password and load application
     * @param actionEvent
     * @throws SQLException
     * @throws Exception
     */
    @FXML
    private void onLogInButton(ActionEvent actionEvent) {
        String loginUserPassword = null;
        User loginUser = userDAO.getUser(usernameText.getText());
        if (loginUser != null){
            loginUserPassword = loginUser.getPassword();
        }
        if(passwordText.getText().equals(loginUserPassword)){
            currentUser = loginUser;
            checkUpcomingAppointment();
            DBConnection.closeConnection();
            logLoginAttempt(usernameText.getText(),true);
            changeScene(actionEvent, "Appointments");
        } else {
            utils.Alert.warn(language.getString("loginTitle"),language.getString("loginHeader"),language.getString("loginContent"));
            logLoginAttempt(usernameText.getText(),false);
        }
    }

    private void logLoginAttempt(String username, boolean loginSuccess){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try{
            FileWriter fwLog = new FileWriter("login_activity.txt",true);
            PrintWriter pwLog = new PrintWriter(fwLog);
            if(loginSuccess){
                pwLog.println(LocalDateTime.now().format(df)+" "+ OffsetDateTime.now().getOffset() +
                        " -   Successful login attempt by user: "+username);
            } else {
                pwLog.println(LocalDateTime.now().format(df)+" "+OffsetDateTime.now().getOffset()+
                        " - Unsuccessful login attempt by user: "+username);
            }
            fwLog.close();
        } catch (IOException e){
            Alert.warn("IO error",
                    "Unable to write to login activity log.",
                    e.toString());
        }
    }

    private void checkUpcomingAppointment(){
        ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
        ObservableList<Appointment> upcomingAppointments;
        upcomingAppointments = allAppointments.stream()
                .filter(s -> s.getStart().isAfter((LocalDateTime.now())))
                .filter(e -> e.getStart().isBefore(LocalDateTime.now().plusMinutes(16)))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        for(Appointment a:upcomingAppointments){
            Alert.warn("Upcoming Appointment",
                    "Appointment with "+a.getCustomerName()+" in " +
                            (LocalDateTime.now().until(a.getStart(), ChronoUnit.MINUTES))+" minutes.",
                    "Appointment "+a.getID()+" in "+
                            (LocalDateTime.now().until(a.getStart(), ChronoUnit.MINUTES))+" minutes at "+
                            a.getStart().toLocalTime()+" "+a.getStart().toLocalDate());
        }
        if(upcomingAppointments.isEmpty()){
            Alert.warn("Upcoming Appointment",
                    "No upcoming appointments.",
                    "There are no upcoming appointments within 15 minutes.");
        }
    }

    /**
     * changeScene
     * switch to another scene
     * @param actionEvent
     * @param sceneName
     */
    private void changeScene (ActionEvent actionEvent, String sceneName){
        try {
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/"+sceneName+".fxml"));
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e){
            //TODO error handling
            System.out.println(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DBConnection.makeConnection();
        } catch (Exception e) {
            //TODO error handling
        }
        timeZoneSelectionLabel.setText(ZoneId.systemDefault().toString());
        timeZoneLabel.setText(language.getString("timeZoneLabel"));
        logInButton.setText(language.getString("logInButton"));
        exitButton.setText(language.getString("exitButton"));
        usernameLabel.setText(language.getString("usernameLabel"));
        passwordLabel.setText(language.getString("passwordLabel"));
        titleLabel.setText(language.getString("titleLabel"));
    }
}
