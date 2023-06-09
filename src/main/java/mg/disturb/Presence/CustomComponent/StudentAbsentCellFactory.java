package mg.disturb.Presence.CustomComponent;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import mg.disturb.Presence.Model.Presence;
import mg.disturb.Presence.Model.Student;

import java.io.IOException;

public class StudentAbsentCellFactory implements Callback<ListView<Student>, ListCell<Student>> {
    private Presence presence;
    private ListView parallelListView;
    public StudentAbsentCellFactory(Presence presence,ListView listView){
        this.presence = presence;
        this.parallelListView = listView;
    }
    @Override
    public ListCell<Student> call(ListView param) {
        try {
            return new StudentAbsentCell(param,parallelListView,presence);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
