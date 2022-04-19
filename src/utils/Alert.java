package utils;

import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * @author BJ Bjostad
 */
public class Alert {

    /**
     * Present warning alert
     * @param title title text
     * @param header header text
     * @param content content text
     */
    public static void warn(String title, String header, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> exit = alert.showAndWait();
    }

    /**
     * Present confirmation alert and check if user clicks ok
     * @param title title text
     * @param header header text
     * @param content content text
     * @return boolean true if OK clicked
     */
    public static boolean confirm(String title, String header, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }

    /**
     * Present informational alert
     * @param title title text
     * @param header header text
     * @param content content text
     */
    public static void info(String title, String header, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> exit = alert.showAndWait();
    }

    /**
     * Present invalid input alert
     * @param input input name
     */
    public static void invalidInput(String input){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Input for "+input+" is not valid.");
        alert.setContentText("Please correct the input for "+input+" and try again.");
        Optional<ButtonType> exit = alert.showAndWait();
    }

}
