package mg.disturb.Presence.CustomComponent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import mg.disturb.Presence.Dialog.EventRepeatedForm;
import mg.disturb.Presence.Dialog.EventSingleDayForms;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.EventMRepeated;
import mg.disturb.Presence.Model.EventMSingleDay;
import mg.disturb.Presence.Service.DaysService;
import mg.disturb.Presence.Service.EventService;

import java.io.IOException;
import java.util.Objects;

public class EventCell extends AbstractCustomCell<EventM> {
    @FXML
    private Label eventName;
    @FXML
    private Label betweenTime;
    @FXML
    private Label dateOrRepeated;
    private EventM eventm;

    public EventCell(ListView<EventM> param) throws IOException {
        fxmlFileName = "event-cell.fxml";
        loadFxml();
        listView = param;
    }

    protected void updateItem(EventM item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null){
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            prefWidthProperty().bind(listView.widthProperty());
            listIndex = listView.getItems().indexOf(item);
            eventm = item;
            eventName.setText(item.getEventName());
            String timePresent = "entre: " + item.getBeginTime().toString().replaceAll(":00$","") +
                    " ~ " + item.getEndTime().toString().replaceAll(":00$","");
            betweenTime.setText(timePresent);

            if (item.getClass() == EventMSingleDay.class){
                dateOrRepeated.setText("le: "+((EventMSingleDay) item).getEventDate());
            } else {
                dateOrRepeated
                        .setText("tous les: "+
                                DaysService.getDaysName().get(((EventMRepeated) item).getRepeatedDayId()));
            }
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    public void deleteItem(){
        String id = eventm.getEventId();
        EventService.delete(eventm);
        EventService.clearNearestEvent();
        listView.getItems().removeIf(eventM -> Objects.equals(eventM.getEventId(), id));
    }

    public void modifItem(){
        if(eventm.getClass() == EventMSingleDay.class){
            contextLoader.save(EventSingleDayForms.getClFormKey(),eventm);
            EventSingleDayForms eventSingleDayForms = new EventSingleDayForms();
            eventSingleDayForms.showAndWait().ifPresent(buttonType -> {
                ButtonBar.ButtonData buttonData = ((ButtonType)buttonType).getButtonData();
                if(
                        Objects.equals(ButtonBar.ButtonData.FINISH,
                                buttonData)
                ){
                    EventMSingleDay eventM =
                            (EventMSingleDay) contextLoader.load(EventSingleDayForms.getClResultKey());
                    listView.getItems().set(listIndex,eventM);
                }
            });
        } else {
            contextLoader.save(EventRepeatedForm.getClFormKey(),eventm);
            EventRepeatedForm eventRepeatedForm = new EventRepeatedForm();
            eventRepeatedForm.showAndWait().ifPresent(buttonType -> {
                ButtonBar.ButtonData buttonData = ((ButtonType)buttonType).getButtonData();
                if(
                        Objects.equals(ButtonBar.ButtonData.FINISH,buttonData)
                ){
                    EventMRepeated eventM =
                            (EventMRepeated) contextLoader.load(EventRepeatedForm.getClResultKey());
                    listView.getItems().set(listIndex,eventM);
                }
            });
        }
        EventService.clearNearestEvent();
    }
}
