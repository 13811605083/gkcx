package com.lhcz.utils;

import java.util.Calendar;

/**
 * @author seifur
 */
public class IdcardUtil {

    /**
     * 获取年龄
     * @param idCard 身份证号
     * @return String
     */
    public static String getAge(String idCard){
        if(StringUtil.isNull(idCard)){
            return "0";
        }
        int year = 0, month = 0, day = 0;

        // 截取身份证号获得生日信息
        if(idCard.length() == 18){
            year = Integer.parseInt(idCard.substring(6, 10));
            month = Integer.parseInt(idCard.substring(10, 12));
            day = Integer.parseInt(idCard.substring(12,14));
        } else if(idCard.length() == 15){
            year = Integer.parseInt("19" + idCard.substring(6, 8));
            month = Integer.parseInt(idCard.substring(8, 10));
            day = Integer.parseInt(idCard.substring(10, 12));
        }

        // 当前时间
        int currentYear = 0, currentMonth = 0, currentDay = 0;
        Calendar date = Calendar.getInstance();
        currentYear =date.get(Calendar.YEAR);
        currentMonth = date.get(Calendar.MONTH) + 1;
        currentDay = date.get(Calendar.DAY_OF_MONTH);

        int age = 0;
        if(year != 0 && month != 0 && day != 0){
            if(month > currentMonth || (month == currentMonth && day > currentDay)){
                // 未过生日
                age = currentYear - year - 1;
            } else {
                // 已过生日
                age = currentYear - year;
            }
        }
        return String.valueOf(age);
    }

    /**
     * 获取出生日期
     * @param idCard 身份证号
     * @return String
     */
    public static String getBirthday(String idCard){
        int year = 0, month = 0, day = 0;

        // 截取身份证号获得生日信息
        if(idCard.length() == 18){
            year = Integer.parseInt(idCard.substring(6, 10));
            month = Integer.parseInt(idCard.substring(10, 12));
            day = Integer.parseInt(idCard.substring(12,14));
        } else if(idCard.length() == 15){
            year = Integer.parseInt("19" + idCard.substring(6, 8));
            month = Integer.parseInt(idCard.substring(8, 10));
            day = Integer.parseInt(idCard.substring(10, 12));
        }
        return year+"/"+month+"/"+day;
    }
}
