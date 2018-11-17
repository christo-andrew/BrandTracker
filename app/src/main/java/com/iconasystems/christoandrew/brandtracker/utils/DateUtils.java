package com.iconasystems.christoandrew.brandtracker.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String toString(Date date, Context context){
        String pattern = "dd MMMM yyyy HH:mm";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern, Locale.getDefault());

        return simpleDateFormat.format(date);
    }
}
