package mg.disturb.Presence.CustomComponent;

import javafx.scene.control.ListView;
import mg.disturb.Presence.Model.Presence;
import mg.disturb.Presence.Model.Student;
import mg.disturb.Presence.Service.PresenceService;

import java.io.IOException;
import java.util.Objects;

public class StudentPresentCell extends AbstractStudentCell{
    private Presence presence;
    private ListView<Student> parallelListView;
    public StudentPresentCell(ListView param, ListView parallelListView, Presence presence) throws IOException {
        fxmlFileName = "student-present-cell.fxml";
        loadFxml();
        this.presence = presence;
        this.listView = param;
        this.parallelListView = parallelListView;
    }

    public void setToAbsent(){
        String numInsc = student.getNumInscri();
        PresenceService.setToAbsent(presence, student);
        parallelListView.getItems().add(student);
        listView.getItems().removeIf(student1 -> Objects.equals(student1.getNumInscri(),numInsc));
    }
}
