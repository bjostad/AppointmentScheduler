package controller;

import DAO.UserDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import model.User;

import java.sql.SQLException;
import java.util.Optional;

public class Login {
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;

    @FXML
    private void onExitButton(ActionEvent actionEvent) {
        if (Util.Alert.confirm("Close","Close Application","Are you sure you want to close the application?")) {
            System.exit(0);
        }
    }

    @FXML
    private void onLogInButton(ActionEvent actionEvent) throws SQLException, Exception {
        String loginUserPassword = null;
        try{
            User loginUser = UserDAOImpl.getUser(usernameText.getText());
            if (loginUser != null){
                loginUserPassword = loginUser.getPassword();
            }
            if(passwordText.getText().equals(loginUserPassword)){

            } else {
                Util.Alert.warn("Invalid Login","Invalid Login","Username or password incorrect.");
            }
        } catch (SQLException e) {

        }

    }
}
