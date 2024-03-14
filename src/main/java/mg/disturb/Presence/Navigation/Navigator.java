package mg.disturb.Presence.Navigation;

import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mg.disturb.Presence.CustomComponent.Icons;
import mg.disturb.Presence.Utils.SingleTone;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Navigator extends SingleTone {
    private Stage stage;

    private Scene scene;
    private ContextLoader contextLoader;
    private NavigationHistory navigationHistory;

    private Stage getStage(){
        return stage;
    }
    private Navigator(
            NavigationConfig config,
            ContextLoader contextLoader,
            NavigationHistory navigationHistory
    ){
        super();
        this.stage = config.getStage();
        this.scene = config.getScene();
        this.contextLoader = contextLoader;
        this.navigationHistory = navigationHistory;
    }

    public static Navigator getInstance(
            NavigationConfig config,
            ContextLoader contextLoader,
            NavigationHistory navigationHistory
    ){
        if (instance == null){
            instance = new Navigator(config,contextLoader,navigationHistory);
        }
        return (Navigator) instance;
    }

    public void navigateTo(
            String sceneName,
            String title,
            Object context
    ) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(Navigation.class.getResource(sceneName+".fxml"));
        navigationHistory.setCurrentScreenName(sceneName);
        contextLoader.save(context);
        Parent root = fxmlLoader.load();
//        Scene scene = new Scene(root);
        scene.setRoot(root);
        getStage().setTitle(title);
//        getStage().setScene(scene);
    }

    public void navigateTo(
            String sceneName,
            String title
    ) throws IOException {
        navigateTo(sceneName,title,null);
    }

    public void goBack() throws IOException {
        navigateTo(navigationHistory.getLastScreenName(), "Menu");
    }
}
