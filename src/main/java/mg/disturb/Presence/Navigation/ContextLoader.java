package mg.disturb.Presence.Navigation;

import javafx.stage.Stage;

import java.util.HashMap;

public class ContextLoader {
    private static ContextLoader instance;
    private NavigationHistory history;
    private static final HashMap<String,Object> userContexts = new HashMap<>();
    private ContextLoader(NavigationHistory history){
        this.history = history;
    }

    public Object load(){
        return userContexts.get(history.getCurrentScreenName());
    }

    public Object load(String key){
        return userContexts.get(key);
    }

    public void save(String key,Object obj){
        userContexts.put(key, obj);
    }

    public void save(Object obj){
        save(history.getCurrentScreenName(),obj);
    }

    public void clear(String key){ userContexts.remove(key); }
    protected static ContextLoader getInstance(NavigationHistory history) {
        if(instance == null){
            instance = new ContextLoader(history);
        }
        return instance;
    }
}
