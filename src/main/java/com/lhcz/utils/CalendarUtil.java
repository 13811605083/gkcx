package com.lhcz.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 41008
 */
@Slf4j
public class CalendarUtil {

    private static final SimpleDateFormat NIAN_YUE = new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat NIAN_YUE_RI = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat NIAN_YUE_RI_SHI_FEN = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat NIAN_YUE_RI_SHI_FEN_MIAO = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 得到某个月的最后一天
     * @param format 格式
     * @param date Date
     * @return String
     */
    public static String getLastDayOfMonth(Date date,String format){
        Calendar  calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format(calendar.getTime(),format);
    }

    /**
     * 按传入的格式格式化日期-因为线程不安全加上synchronized
     * @param format String
     * @param date Date
     * @return String
     */
    public synchronized static String format(Date date, String format){
        if(date == null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 按传入的格式格式化日期-因为线程不安全加上synchronized
     * @param sdf SimpleDateFormat格式
     * @param date Date
     * @return String
     */
    public synchronized static String format(Date date, SimpleDateFormat sdf){
        if(date == null){
            return "";
        }
        return sdf.format(date);
    }

    /**
     * 格式化日期为"yyyy-MM-dd"
     * @param date 日期
     * @return String
     */
    public static String format(Date date){
        return format(date,NIAN_YUE_RI);
    }

    /**
     * 格式化日期为"yyyy-MM-dd HH:mm"
     * @param date 日期
     * @return String
     */
    public static String formatToHm(Date date){
        return format(date,NIAN_YUE_RI_SHI_FEN);
    }

    /**
     * 格式化日期为"yyyy-MM-dd HH:mm:ss"
     * @param date 日期
     * @return String
     */
    public static String formatToHms(Date date){
        return format(date,NIAN_YUE_RI_SHI_FEN_MIAO);
    }

    /**
     * 获取系统当前时间"yyyy-MM-dd HH:mm:ss"
     * @return String
     */
    public static String getCurrentTime(){
        return format(new Date(),NIAN_YUE_RI_SHI_FEN_MIAO );
    }

    /**
     * 获取系统当前时间"yyyy-MM-dd"
     * @return String
     */
    public static String getCurrentDate(){
        return format(new Date(),NIAN_YUE_RI );
    }

    /**
     * 获取系统当前时间"yyyy-MM"
     * @return String
     */
    public static String getCurrentMonth(){
        return format(new Date(),NIAN_YUE );
    }

    /**
     * 将日期字符串按指定格式转换为日期类型
     * @param dateString String
     * @param format String
     * @return Date
     */
    public synchronized static Date toDate(String dateString,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }


    /**
     * 获取当前季度.
     * @return int
     */
    public static int getQuarter(){
        int month =  getMonth();
        switch(month){
            case 4:
            case 5:
            case 6:
                return 2;
            case 7:
            case 8:
            case 9:
                return 3;
            case 10:
            case 11:
            case 12:
                return 4;
            default:
                return 1;
        }
    }

    /**
     * 返回java.sql.Date类型的实例
     * @param utilDate (java.util.Date实例)
     * @return java.sql.Date
     */
    public static java.sql.Date getSqlDate(Date utilDate){
        if(utilDate == null) {
            return null;
        }
        return new java.sql.Date(utilDate.getTime());
    }

    /**
     * 返回java.util.Date类型的实例
     * @param sqlDate (java.sql.Date实例)
     * @return java.util.Date
     */
    public static Date getUtilDate(java.sql.Date sqlDate){
        if(sqlDate == null) {
            return null;
        }
        return new Date(sqlDate.getTime());
    }

    /**
     * 获得当前年
     * @return int
     */
    public static int getYear(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获得当前月
     * @return int
     */
    public static int getMonth(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前的下一天,非日期直接返回
     * @param s yyyy-MM-dd格式日期
     * @return String
     */
    public static String getNextDay(String s){
        try{
            Date date = toDate(s,"yyyy-MM-dd");
            if(date != null){
                long l = date.getTime() + 24*60*60*1000;
                Date newDate = new Date(l);
                return format(newDate);
            }
        }catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }


    /**
     * 获取星期
     * @param week int
     * @return String
     */
    public static String getWeekDay(int week){
        switch(week){
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
            case 1:
            default:
                return "周日";
        }
    }

    /**
     * 计算时间差
     * @param start 开始时间
     * @param end 结束时间
     * @return String
     */
    public static String getTimeDifference(long start,long end){
        long oneDay = 24*60*60*1000;
        long oneHour = 60*60*1000;
        long oneMinute = 60*1000;
        long oneSecond = 1000;
        long l = end - start;

        long d = l/oneDay;
        l = l%oneDay;
        long h = l/oneHour;
        l = l%oneHour;
        long m = l/oneMinute;
        l = l%oneMinute;
        long s = l/oneSecond;
        l = l%oneSecond;
        return String.format( "%d天%d时%d分%d秒%d毫秒", d, h, m, s, l);
    }

    public static void main(String[] args) {
        long l = 1620662400000L;
        Date d = new Date(l);
        System.out.println(CalendarUtil.format(d));

        long start = System.currentTimeMillis();
        long end = start + 12321712986L;
        System.out.println(getTimeDifference(start,end));
    }
}
