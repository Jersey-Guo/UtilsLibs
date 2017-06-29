package com.utils_library.dateutil;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by guojiadong
 * on 2016/12/22.
 */

public class DateUtil {
    public final static String DATE_PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }


    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType) throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }
    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式

    /**
     * 得到时间戳方法 yyyy-MM-dd HH:mm:ss
     */
    public static String getTimeStr(long time) {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD_HH_MM, Locale.getDefault());
        if (time <= 0) {
            return null;
        }
        return mDateFormat.format(new Date(time));
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date;
        date = formatter.parse(strTime);
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType) throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * @param time 毫秒
     * @return yyyy-MM-dd
     */
    public static String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * @param time yyyy-MM-dd
     * @return 毫秒
     * @since 2015年6月12日
     */
    public static long getStringToDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException ignored) {

        }
        return date.getTime();
    }

    /**
     * @param time 毫秒
     * @return yyyy年MM月dd日
     */
    public static String getDateYMDString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * 时间戳转calendar
     *
     * @param time 时间戳
     * @return
     */
    public static Calendar getCalendar(long time) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(time);
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 由时间戳转年份
     *
     * @param time
     * @return
     */
    public static int getYearForTime(long time) {
        Calendar calendar = getCalendar(time);
        if (calendar == null) {
            return 0;
        }
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 由时间戳转月份
     *
     * @param time
     * @return
     */
    public static int getMonthForTime(long time) {
        Calendar calendar = getCalendar(time);
        if (calendar == null) {
            return 0;
        }
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 由时间戳转天
     *
     * @param time
     * @return
     */
    public static int getDayForTime(long time) {
        Calendar calendar = getCalendar(time);
        if (calendar == null) {
            return 0;
        }
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @param time 毫秒
     * @return yyyy-MM-dd HH:mm
     */
    public static String getDatehmToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = new Date(time);
        return sf.format(d);
    }


    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendlyTime(String sdate) {
        try {
            if (TextUtils.isEmpty(sdate)) {
                return "";
            }
            Date time = stringToDate(sdate, "yyyy-MM-dd HH:mm:ss");
            if (time == null) {
                return "Unknown";
            }
            String ftime = "";
            Calendar cal = Calendar.getInstance();

            // 判断是否是同一天
            String curDate = dateFormater.get().format(cal.getTime());
            String paramDate = dateFormater.get().format(time);
            if (curDate.equals(paramDate)) {
                int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
                if (hour == 0)
                    ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
                else
                    ftime = hour + "小时前";
                return ftime;
            }

            long lt = time.getTime() / 86400000;
            long ct = cal.getTimeInMillis() / 86400000;
            int days = (int) (ct - lt);
            if (days == 0) {
                int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
                if (hour == 0)
                    ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
                else
                    ftime = hour + "小时前";
            } else if (days == 1) {
                ftime = "昨天";
            } else if (days == 2) {
                ftime = "前天";
            } else if (days > 2 && days <= 10) {
                ftime = days + "天前";
            } else if (days > 10) {
                ftime = dateFormater.get().format(time);
            }
            return ftime;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 描述:获取上一个月的第一天.
     *
     * @return
     */
    public static String getPerFirstDayOfMonth() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return dft.format(calendar.getTime());
    }

    /**
     * 描述:获取上个月的最后一天.
     *
     * @return
     */
    public static String getLastMaxMonthDate() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }

}
