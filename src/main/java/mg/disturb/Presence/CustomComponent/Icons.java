package mg.disturb.Presence.CustomComponent;

import javafx.scene.image.Image;

public class Icons {
    public static Image load(String iconName){
        String url = String.valueOf(Icons.class.getResource("Icons/"+iconName+".png"));
        return new Image(url);
    }
}
