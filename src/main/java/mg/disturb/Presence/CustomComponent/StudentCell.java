package mg.disturb.Presence.CustomComponent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import mg.disturb.Presence.Dialog.StudentForm;
import mg.disturb.Presence.Model.EventMRepeated;
import mg.disturb.Presence.Model.EventMSingleDay;
import mg.disturb.Presence.Model.Student;
import mg.disturb.Presence.Service.DaysService;
import mg.disturb.Presence.Service.StudentService;

import java.io.IOException;
import java.util.Objects;

public class StudentCell extends AbstractStudentCell {
    public StudentCell(ListView param) throws IOException {
        fxmlFileName = "student-cell.fxml";
        loadFxml();
        listView = param;
    }

    @Override
    public void modifItem() {
        contextLoader.save(StudentForm.getClFormKey(),student);
        StudentForm studentForm = new StudentForm();
        studentForm.showAndWait().ifPresent(buttonType->{
            ButtonBar.ButtonData buttonData = ((ButtonType)buttonType).getButtonData();
            if(
                    Objects.equals(ButtonBar.ButtonData.FINISH,
                            buttonData)
            ){
                Student studentU = (Student) contextLoader.load(StudentForm.getClResultKey());
                listView.getItems().set(listIndex,studentU);
            }
        });
    }

    @Override
    public void deleteItem() {
        String numInsc = student.getNumInscri();
        StudentService.delete(student);
        listView.getItems().removeIf(student1 -> Objects.equals(student1.getNumInscri(),numInsc));
    }
}
