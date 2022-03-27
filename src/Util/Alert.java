package Util;

import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * @author BJ Bjostad
 */
public class Alert {

    /**
     * Present warning alert
     * @param title
     * @param header
     * @param content
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
     * @param title
     * @param header
     * @param content
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
     * @param title
     * @param header
     * @param content
     */
    public static void info(String title, String header, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> exit = alert.showAndWait();
    }

}
