package mg.disturb.Presence;

import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.Student;
import mg.disturb.Presence.Navigation.Navigation;
import mg.disturb.Presence.Navigation.NavigationConfig;
import mg.disturb.Presence.Networking.ServerCallBack;
import mg.disturb.Presence.Service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
import mg.disturb.Presence.ServiceTest.EventServiceTest;
import mg.disturb.Presence.Utils.Validation;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("principal-menu.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Navigation.configure(
                new NavigationConfig(
                        stage,
                        scene,
                        "principal-menu"
                )
        );

        stage.setTitle("Menu Principale");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("javafx.fxml.FXMLLoader").setLevel(Level.OFF);
        launch(args);
    }
}