package mg.disturb.Presence.CustomComponent;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import mg.disturb.Presence.Model.EventM;

import java.io.IOException;

public class EventCellFactory implements Callback<ListView, ListCell<EventM>> {

    @Override
    public ListCell<EventM> call(ListView param) {
        try {
            return new EventCell(param);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
