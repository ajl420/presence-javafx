package mg.disturb.Presence.Dialog;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.EventMRepeated;
import mg.disturb.Presence.Navigation.ContextLoader;
import mg.disturb.Presence.Navigation.Navigation;
import mg.disturb.Presence.Utils.DateUtils;
import java.net.URL;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class AbstractEventForm extends AbstractCustomDialog implements Initializable {
    @FXML
    protected JFXTextField eventName;
    @FXML
    protected JFXTimePicker beginTime;
    @FXML
    protected JFXTimePicker endTime;
    @FXML
    protected Label errorMess;
    @FXML
    protected Label dialogHeadTitle;
    protected EventM eventM;
    protected Time beginTimeValue;
    protected Time endTimeValue;
    protected String defaultId;

    public abstract boolean verifyIfTaken();
    protected abstract void createEvent();
    protected abstract void uptadeEvent();
    private void onConfirm() {
        if(eventM == null){
            createEvent();
        } else {
            uptadeEvent();
        }
        cl.save(_getClResultKey(),eventM);
    }

    protected void setDefaultValue(){
        eventM = (EventM) cl.load(_getClFormKey());
        cl.clear(_getClFormKey());
        if (eventM != null){
            defaultId = eventM.getEventId();
            beginTimeValue = eventM.getBeginTime();
            endTimeValue = eventM.getEndTime();;
            dialogHeadTitle.setText("Modifier un evenement");
            eventName.setText(eventM.getEventName());
            beginTime.setValue(eventM.getBeginTime().toLocalTime());
            endTime.setValue(eventM.getEndTime().toLocalTime());
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefaultValue();

        setOnCloseRequest(event -> {
            ButtonType buttonType = (ButtonType) getResult();
            if (buttonType != null){
                if(
                        Objects.equals(ButtonBar.ButtonData.FINISH,
                                buttonType.getButtonData())
                ){
                    if(hasSomeNullValue()){
                        event.consume();
                        errorMess.setText("Tous les champs sont obligatoires");
                    } else if (verifyIfTaken()){
                        event.consume();
                        errorMess.setText("Date et heure deja occupe");
                    } else {
                        onConfirm();
                    }
                }
            }
        });
    }

    protected boolean hasSomeNullValue(){
        return (
                eventName.getText().equals("") ||
                beginTimeValue == null ||
                endTimeValue == null
        );
    }
    public void keepTimeDiffMin(){
        if(beginTime.getValue() == null){
            beginTimeValue = null;
        } else {
            beginTimeValue = DateUtils.toSqlTime(beginTime.getValue());
            if(
                    endTime.getValue() != null &&
                            beginTime.getValue().compareTo(endTime.getValue()) == 1
            ){
                endTime.setValue(beginTime.getValue());
            }
        }
    }

    public void keepTimeDiffMax(){
        if(endTime.getValue() == null){
            endTimeValue = null;
        } else {
            endTimeValue = DateUtils.toSqlTime(endTime.getValue());
            if(
                    beginTime.getValue() != null &&
                            beginTime.getValue().compareTo(endTime.getValue()) == 1
            ){
                beginTime.setValue(endTime.getValue());
            }
        }
    }
}
