package mg.disturb.Presence.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.Presence;
import mg.disturb.Presence.Navigation.Navigation;
import mg.disturb.Presence.Navigation.Navigator;
import mg.disturb.Presence.Service.EventService;
import mg.disturb.Presence.Service.PresenceService;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void navigateToEvenScreen(ActionEvent actionEvent) throws IOException {
        EventM eventM = new EventM();
        eventM.setEventName("Vody");
        eventM.setBeginTime(new Time(1,1,1));
        Navigator navigator = Navigation.getNavigator();
        try {
            navigator.navigateTo("events-list","Evenement",eventM);
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
}
