package mg.disturb.Presence.CustomComponent;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import mg.disturb.Presence.Model.Presence;
import mg.disturb.Presence.Model.Student;

import java.io.IOException;

public class StudentPresentCellFactory implements Callback<ListView<Student>, ListCell<Student>> {
    private Presence presence;
    private ListView parallelListView;
    public StudentPresentCellFactory(Presence presence,ListView listView){
        this.presence = presence;
        this.parallelListView = listView;
    }
    @Override
    public ListCell<Student> call(ListView param) {
        try {
            return new StudentPresentCell(param,parallelListView,presence);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
