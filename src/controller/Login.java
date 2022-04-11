package controller;

import DAO.DBConnection;
import DAO.UserDAOImpl;
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
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

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

    public static User currentUser = null;

    /**
     * Confirm user wishes to exit and exit program
     * @param actionEvent
     */
    @FXML
    private void onExitButton(ActionEvent actionEvent) throws Exception {
        if (util.Alert.confirm(language.getString("exitTitle"),language.getString("exitHeader"),language.getString("exitContent"))) {
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
    private void onLogInButton(ActionEvent actionEvent) throws SQLException, Exception {
        String loginUserPassword = null;
        try{
            User loginUser = UserDAOImpl.getUser(usernameText.getText());
            if (loginUser != null){
                loginUserPassword = loginUser.getPassword();
            }
            if(passwordText.getText().equals(loginUserPassword)){
                currentUser = loginUser;
                DBConnection.closeConnection();
                changeScene(actionEvent, "Appointments");
            } else {
                util.Alert.warn(language.getString("loginTitle"),language.getString("loginHeader"),language.getString("loginContent"));
            }
        } catch (SQLException e) {
            //TODO error handling
            System.out.println(e);
        }
    }

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
