package mg.disturb.Presence.Dialog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.util.Callback;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Navigation.ContextLoader;
import mg.disturb.Presence.Navigation.Navigation;

import java.io.IOException;

public abstract class AbstractCustomDialog extends Dialog  {
    protected String fxmlFileName;
    protected ContextLoader cl = Navigation.getContextLoader();
    public abstract String _getClResultKey();
    public abstract String _getClFormKey();

    protected void loadFxml(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlFileName));
            loader.setController(this);

            DialogPane dialogPane = loader.load();
            setDialogPane(dialogPane);

        } catch (IOException | NullPointerException e){
            e.printStackTrace();
        }
    };

}
