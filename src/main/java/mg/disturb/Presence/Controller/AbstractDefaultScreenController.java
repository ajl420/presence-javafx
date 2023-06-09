package mg.disturb.Presence.Controller;

import mg.disturb.Presence.Navigation.ContextLoader;
import mg.disturb.Presence.Navigation.Navigation;
import mg.disturb.Presence.Navigation.Navigator;

import java.io.IOException;

public abstract class AbstractDefaultScreenController {
    ContextLoader contextLoader = Navigation.getContextLoader();
    Navigator navigator = Navigation.getNavigator();
    public void forward() throws IOException {
        navigator.goBack();
    }
}
