package mg.disturb.Presence.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import mg.disturb.Presence.CustomComponent.EventCellFactory;
import mg.disturb.Presence.Dialog.EventRepeatedForm;
import mg.disturb.Presence.Dialog.EventSingleDayForms;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.EventMRepeated;
import mg.disturb.Presence.Model.EventMSingleDay;
import mg.disturb.Presence.Service.EventService;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class EventListController extends AbstractDefaultScreenController implements Initializable {

    @FXML
    private ListView eventListView;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private JFXButton toggleMenuBtn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventListView.setCellFactory(new EventCellFactory());
        List<EventM> events = EventService.findAll();
        ObservableList<EventM> obsEventList = FXCollections.observableList(events);
        eventListView.setItems(obsEventList);

        toggleMenuBtn.setOnMousePressed(event -> {
            contextMenu.show(toggleMenuBtn,event.getScreenX(),event.getScreenY());
        });
    }



    public void openNewEventForm(){
        EventSingleDayForms eventSingleDayForms = new EventSingleDayForms();
        eventSingleDayForms.showAndWait().ifPresent(buttonType -> {
            ButtonBar.ButtonData buttonData = ((ButtonType)buttonType).getButtonData();
            if(
                    Objects.equals(ButtonBar.ButtonData.FINISH,
                            buttonData)
            ){
                EventMSingleDay eventM = (EventMSingleDay) contextLoader.load(EventSingleDayForms.getClResultKey());
                eventListView.getItems().add(eventM);
            }
        });
    }

    public void openNewEventForm2(){
        EventRepeatedForm eventRepeatedForm = new EventRepeatedForm();
        eventRepeatedForm.showAndWait().ifPresent(buttonType -> {
            ButtonBar.ButtonData buttonData = ((ButtonType)buttonType).getButtonData();
            if(
                    Objects.equals(ButtonBar.ButtonData.FINISH,
                            buttonData)
            ){
                EventMRepeated eventM = (EventMRepeated) contextLoader.load(EventRepeatedForm.getClResultKey());
                eventListView.getItems().add(eventM);
            }
        });
    }
}
