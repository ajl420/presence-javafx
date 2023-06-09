package mg.disturb.Presence.CustomComponent;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import mg.disturb.Presence.Model.Student;

import java.io.IOException;

public class StudentCellFactory implements Callback<ListView, ListCell<Student>> {
    @Override
    public ListCell<Student> call(ListView param) {
        try {
            return new StudentCell(param);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
