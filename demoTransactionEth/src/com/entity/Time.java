package com.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Time {
    String iso;
    long epoch;

    public Time() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(tz);
        Calendar cal = Calendar.getInstance();
        iso = dateFormat.format(cal.getTime());
        this.epoch = cal.getTimeInMillis()/1000;
        System.out.println(iso);
    }
    public static String getTimeUTC(){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(tz);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }


}
