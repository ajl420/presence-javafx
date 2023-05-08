package com.example.demo;

import com.example.demo.Model.EventRepeated;
import com.example.demo.Model.EventSingleDay;
import com.example.demo.Model.Week;
import com.example.demo.Service.EventService;
import com.example.demo.Service.WeekService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
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
        WeekService.initData();
//        launch();
//        EventSingleDay eventSingleDay = EventService.createForOnlyOneDay(
//                "Cours Francais",
//                new Time(8,0,0),
//                new Time(10,0,0),
//                new Date(123,4,9)
//        );
        List<Week> allDays = WeekService.getAll();
        List<Week> repeatedDay = new ArrayList<>();
        repeatedDay.add(allDays.get(3));
        repeatedDay.add(allDays.get(1));

//
        EventRepeated eventRepeated = EventService.createEventRepeated(
                "Cours PHP",
                new Time(10,0,0),
                new Time(13,0,0),
                repeatedDay
        );
//
//        System.out.println(eventRepeated.getEventName());
    }

}