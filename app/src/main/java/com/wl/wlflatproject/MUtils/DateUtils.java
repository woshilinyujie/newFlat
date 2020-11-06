package com.wl.wlflatproject.MUtils;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Description: 对不同日期和时间的格式方法的封装
 */

public class DateUtils {


    private static DateUtils instance;
    /**
     * 农历月份
     */
    static String[] nlMonth = {"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    /**
     * 中文月名称
     */
    final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};

    private DateUtils() {

    }

    public static DateUtils getInstance() {
        if (instance == null) {
            synchronized (DateUtil.class) {
                if (instance == null) {
                    instance = new DateUtils();
                }
            }
        }
        return instance;
    }


    /**
     * 通过long类型的值返回当前的 星期几
     *
     * @param time
     * @param isCn 是否为中文
     * @return
     */
    public String getWeekday(long time, boolean isCn) {
        Calendar calen = Calendar.getInstance();
        calen.setTimeInMillis(time);
        int week = calen.get(Calendar.DAY_OF_WEEK);
        String result = isCn ? "星期一" : "Monday";
        switch (week) {
            case Calendar.SUNDAY:
                result = isCn ? "星期日" : "Sunday";
                break;
            case Calendar.MONDAY:
                result = isCn ? "星期一" : "Monday";
                break;
            case Calendar.TUESDAY:
                result = isCn ? "星期二" : "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                result = isCn ? "星期三" : "Wednesday";
                break;
            case Calendar.THURSDAY:
                result = isCn ? "星期四" : "Thursday";
                break;
            case Calendar.FRIDAY:
                result = isCn ? "星期五" : "Friday";
                break;
            case Calendar.SATURDAY:
                result = isCn ? "星期六" : "Saturday";
                break;
            default:
                result = isCn ? "星期一" : "Monday";
                break;
        }
        return result;
    }

    public String getWeekday2(long time) {
        Calendar calen = Calendar.getInstance();
        calen.setTimeInMillis(time);
        int week = calen.get(Calendar.DAY_OF_WEEK);
        String result = "周一";
        switch (week) {
            case Calendar.SUNDAY:
                result = "周日";
                break;
            case Calendar.MONDAY:
                result = "周一";
                break;
            case Calendar.TUESDAY:
                result = "周二";
                break;
            case Calendar.WEDNESDAY:
                result = "周三";
                break;
            case Calendar.THURSDAY:
                result = "周四";
                break;
            case Calendar.FRIDAY:
                result = "周五";
                break;
            case Calendar.SATURDAY:
                result = "周六";
                break;
            default:
                result = "周一";
                break;
        }
        return result;
    }

    public String getChinaMonthString(int month) {
        return nlMonth[month - 1];
    }

    public String getChinaDayString(int day) {
        String chineseTen[] = {"初", "十", "廿", "卅"};
        int n = day % 10 == 0 ? 9 : day % 10 - 1;
        if (day > 30)
            return "";
        if (day == 10)
            return "初十";
        else
            return chineseTen[day / 10] + chineseNumber[n];
    }


    /**
     * 判断两个时间是否属于同一天
     *
     * @param time1
     * @param time2
     * @return
     */
    public boolean isSameDay(long time1, long time2) {
        Calendar calen = Calendar.getInstance();
        calen.setTimeInMillis(time1);
        int day1 = calen.get(Calendar.DAY_OF_YEAR);
        calen.setTimeInMillis(time2);
        int day2 = calen.get(Calendar.DAY_OF_YEAR);
        return day1 == day2;
    }

    /**
     * 日期转为时间戳
     *
     * @param date
     * @param format
     * @return
     */
    public long date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param time
     * @return
     * @描述: 2017-02-23
     */
    public String getDayOrMonthOrYear(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(time));
    }

    /**
     * @param time
     * @return
     * @描述: 2017年02月23日
     */
    public String getDayOrMonthOrYear1(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年-MM月-dd日");
        return sdf.format(new Date(time));
    }

    /**
     * @param time
     * @return
     * @描述: 2017年02月23日
     */
    public String getDayOrMonthOrYear2(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date(time));
    }

    /**
     * @param time
     * @return
     * @描述: 2017/02/23
     */
    public String getDayOrMonthOrYear3(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(new Date(time));
    }

    /**
     * @param time
     * @return
     * @描述: 2017.02.23
     */
    public String getDayOrMonthOrYear4(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(new Date(time));
    }

    public String dateFormat(long time) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date(time));
    }

    /**
     * @param time
     * @return
     * @描述: 2017-02-23 06:26:12
     */
    public String dateFormat2(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date(time));
    }

    /**
     * @param time
     * @return
     * @描述: 2017年02月23日06点26分12秒
     */
    public String dateFormat3(long time) {
        return new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒", Locale.getDefault())
                .format(new Date(time));
    }


    /**
     * @param
     * @return
     * @描述: 2017-02-23 06:26:12  倒计时格式
     */
    public String dateFormat4(long mss) {
        long hours = mss / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return hours + ":" + minutes + ":"
                + seconds;
    }

    public String dateFormat5(long mss) {
        long hours = mss / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return hours + "时" + minutes + "分"
                + seconds + "秒";
    }

    /**
     * @param time
     * @return
     * @描述: 2017-02-23 06:26:12
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String dateFormat6(long time) {
        String format1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH : mm : ss"));
        return format1;
    }


    public String dateFormat7(long time) {
        return new SimpleDateFormat("MM-dd", Locale.getDefault())
                .format(new Date(time));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String dateFormat8(long time) {
        String format1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        return format1;
    }

    /**
     *
     * @return
     */
    public String dateFormat9(long time) {
        return new SimpleDateFormat("HH", Locale.getDefault())
                .format(new Date(time));
    }

    /**
     * 7.4
     *
     * @param time
     * @return
     */
    public String dateFormat10(long time) {
        return new SimpleDateFormat("M.d", Locale.getDefault())
                .format(new Date(time));
    }

    /**
     * 7.4
     * 日历专用
     *
     * @param date
     * @return
     */
    public String dateFormat11(String date) {
        long timeStamp = date2TimeStamp(date, "yyyy-MM-dd");
        return dateFormat10(timeStamp);
    }

    /**
     * 是不是晚上
     *
     * @return
     */
    public boolean isNight() {
        //获取系统时间
        Calendar c = Calendar.getInstance();
        //提取他的时钟值，int型
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour >= 18 && hour <= 24 || hour >= 0 && hour <= 6) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将时间戳转换成描述性时间（昨天、今天、明天）
     * 天气专用
     */
    public String descriptiveData(String date) {
        String descriptiveText = null;
        //当前时间
        Calendar currentTime = Calendar.getInstance();
        //要转换的时间
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(date2TimeStamp(date, "yyyy-MM-dd"));
        //年相同
        if (currentTime.get(Calendar.YEAR) == time.get(Calendar.YEAR)) {
            //获取一年中的第几天并相减，取差值
            switch (currentTime.get(Calendar.DAY_OF_YEAR) - time.get(Calendar.DAY_OF_YEAR)) {
                //当前比目标多一天，那么目标就是昨天
                case 1:
                    descriptiveText = "昨天";
                    break;
                //当前和目标是同一天，就是今天
                case 0:
                    descriptiveText = "今天";
                    break;
                //当前比目标少一天，就是明天
                case -1:
                    descriptiveText = "明天";
                    break;
                case -2:
                    descriptiveText = "后天";
                    break;
                default:
                    descriptiveText = dateFormat11(date);
                    break;
            }
        }
        return descriptiveText;
    }

    //获取本周的开始时间
    public Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取本月的开始时间
    public Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取某个日期的开始时间
    public Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //转时间戳 秒
    public long dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        ts = ts / 1000;
        return ts;
    }

    //获取今年是哪一年
    public Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    //获取本月是哪一月
    public int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }


    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public Date stringToDate(String strTime, String formatType)
            throws ParseException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static String getTime(int second) {
        if (second < 10) {
            return "00:00:0" + second;
        }
        if (second < 60) {
            return "00:00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "00:0" + minute + ":0" + second;
                }
                return "00:0" + minute + ":" + second;
            }
            if (second < 10) {
                return "00:" + minute + ":0" + second;
            }
            return "00:" + minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + minute + ":0" + second;
            }
            return "0" + hour + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour + minute + ":" + second;
    }
}
