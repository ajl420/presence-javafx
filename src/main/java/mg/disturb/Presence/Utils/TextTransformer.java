package mg.disturb.Presence.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextTransformer {
    public static String capitalize(String input){
        Pattern p = Pattern.compile("\\b\\p{L}");
        Matcher m = p.matcher(input);

        StringBuffer res = new StringBuffer();
        while (m.find()){
            m.appendReplacement(res,m.group().toUpperCase());
        }
        m.appendTail(res);
        return res.toString();
    }
}
