package mg.disturb.Presence.Dialog;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.EventMRepeated;
import mg.disturb.Presence.Model.EventMSingleDay;
import mg.disturb.Presence.Navigation.ContextLoader;
import mg.disturb.Presence.Navigation.Navigation;
import mg.disturb.Presence.Service.EventService;
import mg.disturb.Presence.Utils.DateUtils;
import mg.disturb.Presence.Utils.EventServiceUtils;
import mg.disturb.Presence.Utils.TextTransformer;

import javax.naming.Context;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class EventSingleDayForms extends AbstractEventForm {

    @FXML
    private JFXDatePicker eventDate;
    private Date eventDateValue;


    public EventSingleDayForms(){
        this.fxmlFileName = "eventsingleday-forms.fxml";
        this.defaultId = "";
        loadFxml();
    }


    @Override
    protected void setDefaultValue() {
        super.setDefaultValue();
        if(eventM != null){
            eventDate.setValue(DateUtils.toLocalDate(((EventMSingleDay) eventM).getEventDate()));
            eventDateValue = ((EventMSingleDay) eventM).getEventDate();
        }
    }

    public static String getClFormKey(){
        return "EVENT_SINGLEDAY_CL_FORM_KEY";
    }

    public static String getClResultKey(){
        return "EVENT_SINGLEDAY_CL_RESULT_KEY";
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
        eventM = EventService.createForOnlyOneDay(
                TextTransformer.capitalize(eventName.getText()),
                beginTimeValue, endTimeValue,
                (java.sql.Date) eventDateValue
        );
    }

    @Override
    protected void uptadeEvent() {
        eventM.setEventName(TextTransformer.capitalize(eventName.getText()));
        eventM.setBeginTime(beginTimeValue);
        eventM.setEndTime(endTimeValue);
        ((EventMSingleDay) eventM).setEventDate(eventDateValue);
        EventService.update(eventM);
    }

    @Override
    public boolean verifyIfTaken() {
        return EventServiceUtils.isDateTaken(
                beginTimeValue,
                endTimeValue,
                (java.sql.Date) eventDateValue,
                defaultId
        );
    }

    public void handleDateOnChange(){
        if(eventDate.getValue() == null){
            eventDateValue = null;
        } else {
            LocalDate localDate = eventDate.getValue();
            eventDateValue = DateUtils.toSqlDate(localDate);
        }
    }

    @Override
    protected boolean hasSomeNullValue() {
        return (
                super.hasSomeNullValue() ||
                eventDateValue == null
        );
    }
}
