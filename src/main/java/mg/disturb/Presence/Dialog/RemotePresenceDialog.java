package mg.disturb.Presence.Dialog;

import com.jfoenix.controls.JFXBadge;
import com.sun.net.httpserver.HttpServer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import mg.disturb.Presence.Networking.PresenceServer;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.ResourceBundle;

public class RemotePresenceDialog extends AbstractCustomDialog implements Initializable {
    @FXML
    private Label infoMess;
    @FXML
    private Label errorMess;

    public RemotePresenceDialog() {
        this.fxmlFileName = "remote-presence.fxml";
        loadFxml();
    }
    public static String getClFormKey(){
        return "REMOTE_PRESENCE_CL_KEY";
    }
    public static String getClResultKey(){
        return "REMOTE_PRESENCE_CL_RESULT_KEY";
    }
    @Override
    public String _getClResultKey() {
        return getClResultKey();
    }
    @Override
    public String _getClFormKey() {
        return getClFormKey();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String adrr;
        try {
            adrr = PresenceServer.getAddress().getHostAddress();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        infoMess.setText("Connectez-vous sur l'adresse "+ adrr);
        setOnCloseRequest(event -> {
            ButtonType buttonType = (ButtonType) getResult();
            if (buttonType != null){
                if(
                        Objects.equals(ButtonBar.ButtonData.FINISH,
                                buttonType.getButtonData())
                ){
                    try {
                        HttpServer httpServer = PresenceServer.startServer();
                        cl.save(getClResultKey(),httpServer);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
