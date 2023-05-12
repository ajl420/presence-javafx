package mg.disturb.Presence.Utils;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static int getDayOfWeek(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static  Calendar toCalendar(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c;
    }

    public static Date toDate(Calendar c){
        return c.getTime();
    }

    public static Date getFirstWeekDay(){
        int currentDayOfWeek = getCurrentWeekDay();
        Calendar currentC = Calendar.getInstance();
        currentC.add(Calendar.DAY_OF_WEEK,1-currentDayOfWeek);
        return toDate(currentC);
    }

    public static Date getLastWeekDay(){
        int currentDayOfWeek = getCurrentWeekDay();
        Calendar currentC = Calendar.getInstance();
        currentC.add(Calendar.DAY_OF_WEEK,7-currentDayOfWeek);
        return toDate(currentC);
    }

    public static int getCurrentWeekDay(){
        Date current = new Date();
        int dayOfWeek = getDayOfWeek(current);
        return dayOfWeek;
    }

    public static Time getCurrentTime(){
        Calendar currentDate = Calendar.getInstance(Locale.FRENCH);
        int h = currentDate.get(Calendar.HOUR_OF_DAY);
        int m = currentDate.get(Calendar.MINUTE);
        int s = currentDate.get(Calendar.SECOND);

        Time currentTime = new Time(h,m,s);
        return currentTime;
    }

    public static int compareTime(Time t1,Time t2){
        Calendar c1 = Calendar.getInstance();
        c1.setTime(t1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(t2);
        return c1.compareTo(c2);
    }

    public static Date getCurrentDate(){
        return new Date();
    }
}
