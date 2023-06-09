package mg.disturb.Presence.Navigation;

import javafx.stage.Stage;

public class NavigationConfig {
    private Stage stage;
    private String principaleScreenName;

    public String getPrincipaleScreenName() {
        return principaleScreenName;
    }

    public NavigationConfig(
            Stage stage,
            String principalScreenName
    ) {
        this.stage = stage;
        this.principaleScreenName = principalScreenName;
    }

    protected Stage getStage() {
        return stage;
    }
}
