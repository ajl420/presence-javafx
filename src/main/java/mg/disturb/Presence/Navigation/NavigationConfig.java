package mg.disturb.Presence.Navigation;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationConfig {
    private Stage stage;
    private Scene scene;
    private String principaleScreenName;
    public NavigationConfig(
            Stage stage,
            Scene scene,
            String principalScreenName
    ) {
        this.stage = stage;
        this.scene = scene;
        this.principaleScreenName = principalScreenName;
    }
    public String getPrincipaleScreenName() {
        return principaleScreenName;
    }
    public Stage getStage() {
        return stage;
    }
    public Scene getScene() {
        return scene;
    }
}
