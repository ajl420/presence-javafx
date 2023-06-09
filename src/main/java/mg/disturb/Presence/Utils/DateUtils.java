package mg.disturb.Presence.Utils;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static String[] daysFr = {
            "Dimanche", "Lundi", "Mardi",
            "Mercredi", "Jeudi", "Vendredi", "Samedi"
    };

    private static String[] monthFr = {
            "Janvier", "Fevrier", "Mars","Avril",
            "Mai", "Juin", "Julliet", "Aout",
            "Septembre", "Octobre", "Novembre", "Decembre"
    };
    public static int getDayOfWeek(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DAY_OF_WEEK)-1;
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

    public static Time toSqlTime(LocalTime localTime){
        return new Time(
                localTime.getHour(),
                localTime.getMinute(),
                localTime.getSecond()
        );
    }

    public static java.sql.Date toSqlDate(LocalDate localDate){
        return new java.sql.Date(
                localDate.getYear()-1900,
                localDate.getMonthValue()-1,
                localDate.getDayOfMonth()
        );
    }

    public static LocalDate toLocalDate(Date date){
        return LocalDate.of(
                date.getYear()+1900,
                date.getMonth()+1,
                date.getDay()
        );
    }

    public static String toFrString(Date date){
        Calendar c = toCalendar(date);
        String convertedDate = "";
        convertedDate += daysFr[c.get(Calendar.DAY_OF_WEEK)-1] + ", ";
        convertedDate += c.get(Calendar.DAY_OF_MONTH) + " ";
        convertedDate += monthFr[c.get(Calendar.MONTH)] + " ";
        convertedDate += c.get(Calendar.YEAR);
        return convertedDate;
    }
}
