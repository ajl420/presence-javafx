package mg.disturb.Presence.Navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class Navigation {
    private static NavigationConfig config;
    private static Navigator navigator;
    private static ContextLoader contextLoader;

    public static ContextLoader getContextLoader(){
        if(contextLoader == null){
            contextLoader = ContextLoader.getInstance(
                    getHistory()
            );
        }
        return contextLoader;
    }

    public static NavigationHistory getHistory(){
        return NavigationHistory.getInstance(config);
    }

    public static Navigator getNavigator(){
        if(navigator == null){
            navigator = Navigator.getInstance(
                    config,
                    getContextLoader(),
                    getHistory()
            );
        }
        return navigator;
    }

    public static void configure(NavigationConfig confignav){
        config = confignav;
    }

    public static NavigationConfig getConfig() {
        return config;
    }
}
