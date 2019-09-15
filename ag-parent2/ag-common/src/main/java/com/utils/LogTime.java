package com.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogTime {
    public static String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }
}
