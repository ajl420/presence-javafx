package mg.disturb.Presence.CustomComponent;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Navigation.ContextLoader;
import mg.disturb.Presence.Navigation.Navigation;

import java.io.IOException;

public abstract class AbstractCustomCell<R> extends ListCell<R> {
    protected String fxmlFileName;
    protected int listIndex;
    protected void loadFxml() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFileName));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
    protected ListView<R> listView;
    protected ContextLoader contextLoader = Navigation.getContextLoader();
    public abstract void modifItem();
    public abstract void deleteItem();

    @Override
    protected void updateItem(R item, boolean empty) {
        super.updateItem(item, empty);
    }
}
