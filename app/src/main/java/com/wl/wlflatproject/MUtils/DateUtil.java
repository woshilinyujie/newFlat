package com.wl.wlflatproject.MUtils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by WLPC on 2017/3/31.
 */

public class DateUtil {
    public static long getTime(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        try {
            Date date = sdf.parse(string);
            time = date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return time;
    }

    public static long getCurrentTime() {
        //		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //		fmt.setTimeZone(TimeZone.getDefault());
        //		String utcTime = fmt.format(new Date());
        //		return utcTime;
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTime(date);
        return cal.getTimeInMillis();
    }

    public static long getTodayZeroTime() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTime(date);
        cal.set(Calendar.HOUR, -12);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String utcTime = fmt.format(cal.getTimeInMillis());
        Log.d("aaa", "getLastThreeDayTime  utcTime  " + utcTime);
        return cal.getTimeInMillis();
    }

    public static long getOneDayMillis(int num) {
        return 1000L*num * 24 * 60 * 60;
    }

    public static long getLastThreeDayTime() {
        long zeroTime = getTodayZeroTime();

        //		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //		fmt.setTimeZone(TimeZone.getDefault());
        //		String utcTime = fmt.format(time);

        return zeroTime - getOneDayMillis(2);
    }

    public static long getLastOneWeekTime() {
        long zeroTime = getTodayZeroTime();

        return zeroTime - getOneDayMillis(6);
    }


    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }
}
