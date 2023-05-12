package mg.disturb.Presence;

import mg.disturb.Presence.Model.EventRepeated;
import mg.disturb.Presence.Model.EventSingleDay;
import mg.disturb.Presence.Model.Student;
import mg.disturb.Presence.Networking.PresenceServer;
import mg.disturb.Presence.Service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mg.disturb.Presence.ServiceTest.EventServiceTest;
import mg.disturb.Presence.ServiceTest.PresenceServiceTest;
import mg.disturb.Presence.ServiceTest.StudentServiceTest;
import mg.disturb.Presence.Utils.Validation;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.logging.Level;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
//        launch();
//        EventServiceTest.createEvents();
//        StudentServiceTest.createStudent();
//        EventServiceTest.getNearestEvent();
//        PresenceServiceTest.setToPresente();
//        PresenceServiceTest.setToAbsent();
//        PresenceServiceTest.getCurrentPresence();
//        PresenceServiceTest.allPresentStudent();
//        PresenceServiceTest.allAbsentStudent();
//        PresenceServiceTest.allAbsentStudent();
//        PresenceServiceTest.getHistory();
        PresenceRemoteService.startServer(addr ->{
            System.out.println("Server is lisening on "+ addr +" ...");
        });

        PresenceRemoteService.listenToMessage(message -> {
            Student s1 = StudentService.findById(message);
            System.out.println(message);
            System.out.println(s1);
        });
    }

}