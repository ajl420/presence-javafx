package mg.disturb.Presence.Service;

import mg.disturb.Presence.Model.Days;

import java.util.ArrayList;
import java.util.List;

public class DaysService {
    private static List<Days> days = new ArrayList<>();

    public static List<Days> getDays(){
        if (days.size() == 0){
            String[] daysLabel = {
                    "Di","Lu","Ma","Me","Je","Ve","Sa"
            };

            String[] daysName = {
                    "Dimanche","Lundi","Mardi",
                    "Mercredi","Jeudi","Vendredi","Samedi"
            };

            for (int i = 0; i < daysLabel.length; i++) {
                days.add(new Days((i+1),daysLabel[i],daysName[i]));
            }
        }

        return days;
    }

}
