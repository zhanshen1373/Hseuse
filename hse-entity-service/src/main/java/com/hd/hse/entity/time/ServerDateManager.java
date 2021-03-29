package com.hd.hse.entity.time;

import android.os.SystemClock;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by yangning on 2018/9/12 14:29.
 * 服务器时间
 */
public class ServerDateManager {
    /**
     * 同步服务器时间的开关
     */
    public static boolean isServerDate;
    /**
     * 服务器时间
     */
    public static Long serverDate;
    /**
     * 本地时间（与服务器时间相对应）
     */
    public static Long localDate;

    /**
     * 设置启用同步服务器时间的开关
     *
     * @param isServerDate
     */
    public static void setIsServerDate(boolean isServerDate) {
        ServerDateManager.isServerDate = isServerDate;
    }

    /**
     * 设置服务器时间
     *
     * @param serverdate
     */
    public static void setServerDate(Long serverdate) {
        serverDate = serverdate;
        localDate = SystemClock.elapsedRealtime();
    }

    /**
     * 得到当前时间 毫秒数(时间同步的总入口)
     *
     * @return long
     */
    public static Long getCurrentTimeMillis() {
        if (isServerDate && serverDate != null) {
            return SystemClock.elapsedRealtime() - localDate + serverDate;
        } else {
            return System.currentTimeMillis();
        }
    }

    /**
     * 得到当前时间
     *
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").format(getCurrentDate());
    }

    /**
     * 得到当前时间
     *
     * @return Date
     */
    public static Date getCurrentDate() {
        return new Date(getCurrentTimeMillis());
    }

    /**
     * 年
     *
     * @return
     */
    public static String getYear() {
        return getYearMonthDay()[0];
    }

    /**
     * 月
     *
     * @return
     */
    public static String getMonth() {
        return getYearMonthDay()[1];
    }

    /**
     * 日
     *
     * @return
     */
    public static String getDay() {
        return getYearMonthDay()[2];
    }

    /**
     * 年月日集合
     *
     * @return
     */
    private static String[] getYearMonthDay() {
        return new SimpleDateFormat(
                "yyyy-MM-dd").format(getCurrentDate())
                .split("-");
    }

    /**
     * 得到星期
     *
     * @return
     */
    public static String getWeek() {
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        return dateFm.format(getCurrentDate());
    }


}
