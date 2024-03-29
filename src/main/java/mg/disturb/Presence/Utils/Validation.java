package mg.disturb.Presence.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static boolean isMatch(String reg,String str){
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isFound(String reg,String str){
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static boolean isValidName(String name){
        return !isFound("[^a-zA-Z\\s]",name);
    }
    public static boolean isValidNumInscript(String numInscript){
        return isMatch("\\d{3}I\\d{2}",numInscript);
    }

    public static boolean isLocalIp(String ip){
        return isMatch("(19\\d|10)(\\.\\d*){3}",ip);
    }
}
