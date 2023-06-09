package mg.disturb.Presence.Navigation;

import mg.disturb.Presence.Utils.SingleTone;

import java.util.HashMap;
import java.util.Objects;

public class NavigationHistory {
    private static NavigationHistory instance;
    private final HashMap<String, Object> historyContext = new HashMap<>();

    public String getCurrentScreenName(){
        return (String) historyContext.get("current-screen-name");
    }

    private void setLastScreenName(String currentScreem){
        historyContext.put("last-screen-name",currentScreem);
    }

    public String getLastScreenName(){
        return (String) historyContext.get("last-screen-name");
    }

    public void setCurrentScreenName(String newCurrentScreenName){
        setLastScreenName(getCurrentScreenName());
        historyContext.put("current-screen-name",newCurrentScreenName);
    }

    public static NavigationHistory getInstance(NavigationConfig config){
        if (instance == null){
            instance = new NavigationHistory(config);
        }
        return instance;
    }

    private NavigationHistory(NavigationConfig config){
        super();
        setCurrentScreenName(config.getPrincipaleScreenName());
    }
}
