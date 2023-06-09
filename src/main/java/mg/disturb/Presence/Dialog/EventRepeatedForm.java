package mg.disturb.Presence.Dialog;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import mg.disturb.Presence.Model.EventMRepeated;
import mg.disturb.Presence.Service.DaysService;
import mg.disturb.Presence.Service.EventService;
import mg.disturb.Presence.Utils.EventServiceUtils;
import mg.disturb.Presence.Utils.TextTransformer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EventRepeatedForm extends AbstractEventForm {
    @FXML
    private JFXComboBox eventRepeated;
    private int repeatedDayValue;

    public EventRepeatedForm() {
        this.fxmlFileName = "eventrepeated-forms.fxml";
        this.defaultId = "";
        loadFxml();
    }

    public static String getClFormKey(){
        return "EVENT_REPEATED_CL_FORM_KEY";
    }

    public static String getClResultKey(){
        return "EVENT_REPEATED_CL_RESULT_KEY";
    }
    @Override
    public String _getClResultKey() {
        return getClResultKey();
    }

    @Override
    public String _getClFormKey() {
        return getClFormKey();
    }

    @Override
    protected void createEvent() {
        eventM = EventService.createEventRepeated(
                TextTransformer.capitalize(eventName.getText()),
                beginTimeValue, endTimeValue, repeatedDayValue
        );
    }

    @Override
    protected void uptadeEvent() {
        eventM.setEventName(TextTransformer.capitalize(eventName.getText()));
        eventM.setBeginTime(beginTimeValue);
        eventM.setEndTime(endTimeValue);
        ((EventMRepeated) eventM).setRepeatedDayId(repeatedDayValue);
        EventService.update(eventM);
    }

    private void setDefaulOption(){
        List<String> days = DaysService.getDaysName();
        eventRepeated.setItems(FXCollections.observableArrayList(days));
    }

    protected void setDefaultValue(){
        super.setDefaultValue();
        if(eventM != null){
            repeatedDayValue = ((EventMRepeated) eventM).getRepeatedDayId();
            eventRepeated.setValue(
                    DaysService
                            .getDaysName()
                            .get(((EventMRepeated) eventM).getRepeatedDayId())
            );
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefaulOption();
        super.initialize(location,resources);
    }


    @Override
    public boolean verifyIfTaken() {
        return EventServiceUtils.isDateTaken(
                beginTimeValue,
                endTimeValue,
                repeatedDayValue,
                defaultId
        );
    }

    public void handleOnChangeReapeatedDay(){
        repeatedDayValue = DaysService.getIndexOf((String) eventRepeated.getValue());
    }

    @Override
    protected boolean hasSomeNullValue() {
        return (
                super.hasSomeNullValue() ||
                eventRepeated.getValue() == null
        );
    }
}
