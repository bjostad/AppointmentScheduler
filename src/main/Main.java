package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    /**
     * load JavaFX stage and scene
     * @param mainStage
     * @throws Exception
     */
    @Override
    public void start(Stage mainStage) throws Exception {
        ResourceBundle language = ResourceBundle.getBundle("resources/language",Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        mainStage.setTitle(language.getString("titleLabel"));
        mainStage.setScene(new Scene(root, 350, 350));
        mainStage.show();
    }

    /**
     * launch JavaFX
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //Uncomment line below to test french locale
        //Locale.setDefault(new Locale("fr"));
        launch(args);
    }
}
