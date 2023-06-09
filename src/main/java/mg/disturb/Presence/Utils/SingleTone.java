package mg.disturb.Presence.Utils;

import mg.disturb.Presence.Navigation.NavigationConfig;

public class SingleTone {
    protected static SingleTone instance;
    protected SingleTone(){};

    public static SingleTone getInstance() {
        if(instance == null){
            instance = new SingleTone();
        }
        return instance;
    }
}
