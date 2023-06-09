package mg.disturb.Presence.CustomComponent;

import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.EventMRepeated;
import mg.disturb.Presence.Model.EventMSingleDay;
import mg.disturb.Presence.Model.Student;
import mg.disturb.Presence.Service.DaysService;

public class AbstractStudentCell extends AbstractCustomCell<Student> {
    @FXML
    protected Label fullNameStud;
    @FXML
    protected Label numInscript;
    protected Student student;

    @Override
    protected void updateItem(Student item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null){
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            prefWidthProperty().bind(listView.widthProperty());
            String fullName = item.getNameStud().toUpperCase() +" "+
                    item.getFstNameStud();
            fullNameStud.setText(fullName);
            numInscript.setText(item.getNumInscri());
            student = item;
            listIndex = listView.getItems().indexOf(item);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
    @Override
    public void modifItem() {

    }

    @Override
    public void deleteItem() {

    }
}
