package com.example.demo.Utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static int getDayOfWeek(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DAY_OF_WEEK);
    }
}
