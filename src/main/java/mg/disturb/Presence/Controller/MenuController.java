package mg.disturb.Presence.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.Presence;
import mg.disturb.Presence.Navigation.Navigation;
import mg.disturb.Presence.Navigation.Navigator;
import mg.disturb.Presence.Service.EventService;
import mg.disturb.Presence.Service.PresenceService;
import mg.disturb.Presence.Utils.DateUtils;
import mg.disturb.Presence.Utils.EventServiceUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    public Label chronoLabel;
    public JFXButton doPresenceBtn;
    public Label titleInfo;
    public Label eventName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Presence presence = PresenceService.getCurrentPresence();
        EventM nearestEvent = EventService.getNearestEvent();
        if(presence != null){
            EventM eventM = presence.getEvent();
            eventName.setText("Nom: "+eventM.getEventName());
            beginChrono(eventM);
        }
        else if (nearestEvent != null) {
            eventName.setText("Nom: "+nearestEvent.getEventName());
            beginChrono(nearestEvent);
            System.out.println(nearestEvent);
        }
        else {
            titleInfo.setText("Aucun evenement");
            eventName.setText("Aucun evenement prevu pour aujourd'hui.");
            chronoLabel.setText("");
            doPresenceBtn.setDisable(true);
        }
    }

    private void beginChrono(EventM eventM){
        Time beginTime = eventM.getBeginTime();
        Time endTime = eventM.getEndTime();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Time middleTime;
                        if(!EventServiceUtils.isActiveEvent(eventM)){
                            middleTime = new Time(
                                    beginTime.getHours()-DateUtils.getCurrentTime().getHours(),
                                    beginTime.getMinutes()-DateUtils.getCurrentTime().getMinutes(),
                                    beginTime.getSeconds()-DateUtils.getCurrentTime().getSeconds()
                            );
                            titleInfo.setText("Presence iminante");
                            chronoLabel.setText("Debut dans: "+middleTime.toString());
                            doPresenceBtn.setDisable(true);
                        } else if (EventServiceUtils.isDelayedEvent(eventM)) {
                            titleInfo.setText("Aucun evenement");
                            eventName.setText("Plus aucun evenement prevu pour aujourd'hui.");
                            chronoLabel.setText("");
                            doPresenceBtn.setDisable(true);
                        } else {
                            middleTime = new Time(
                                    endTime.getHours()-DateUtils.getCurrentTime().getHours(),
                                    endTime.getMinutes()-DateUtils.getCurrentTime().getMinutes(),
                                    endTime.getSeconds()-DateUtils.getCurrentTime().getSeconds()
                            );
                            titleInfo.setText("Presence en cours");
                            chronoLabel.setText("Fin dans: "+middleTime.toString());
                            doPresenceBtn.setDisable(false);
                        }

                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void navigateToEvenScreen(ActionEvent actionEvent) throws IOException {
        Navigator navigator = Navigation.getNavigator();
        try {
            navigator.navigateTo("events-list","Evenement");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void navigateToStudentList() throws IOException {
        Navigation.getNavigator().navigateTo("students-list","Etudiant");
    }

    public void doPresence(ActionEvent actionEvent) throws IOException {
        Presence currentPresence = PresenceService.getCurrentPresence();
        EventM eventM = EventService.getNearestEvent();
        if(currentPresence != null){
            Navigation.getNavigator().navigateTo("do-presence","Faire la presence",currentPresence);
        } else {
            System.out.println(eventM);
        }
    }

    public void closeApp(ActionEvent actionEvent) {
        Platform.exit();
    }
}
