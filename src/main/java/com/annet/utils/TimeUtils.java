package com.annet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: jh
 * @Date: 2019/11/6 13:48
 * @Version: 1.0
 * @Description: 时间工具类
 */
public class TimeUtils {


    /**
     * 日期（yyyy-MM-dd）转化为时间戳
     *
     * @param stamp 日期
     * @return 时间戳
     */
    public static long timeToStamp(String stamp) {
        Date d = new Date();
        long timeStamp;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            d = sf.parse(stamp);
            // 日期转换为时间戳
        } catch (ParseException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        timeStamp = d.getTime();
        return timeStamp;
    }

    /**
     * 时间戳转化为日期（yyyy-MM-dd）
     *
     * @param stamp 时间戳
     * @return 日期
     */
    public static String stampToTime(String stamp) {
        String sd;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sd = sdf.format(new Date(Long.parseLong(stamp)));
        return sd;
    }

    /**
     * 日期转字符串（yyyy-MM-dd HH:mm:ss）
     *
     * @param time 日期
     * @return  日期字符串
     */
    public static String timeToString(Date time) {
        String sd;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sd = sdf.format(time);
        return sd;
    }

    /**
     * 日期转化时间集合
     *
     * @param startDate (2019-09-10)
     * @param endDate   (2019-09-14)
     * @return 日期时间集合
     */
    public static List<String> conversionDateList(String startDate, String endDate) {
        List<String> dateList = new ArrayList<>();
        long startStamp = timeToStamp(startDate);
        long endStamp = timeToStamp(endDate);
        for (long startTime = startStamp; startTime <= endStamp; startTime += (24 * 60 * 60 * 1000)) {
            String stamp = TimeUtils.stampToTime("" + startTime);
            dateList.add(stamp);
        }
        return dateList;
    }

    public static Map<String,String> dateMap(List<String> originalDates,List<String> multiplexDates){
        Map<String,String> map= new LinkedHashMap<>();
        for(int i=0;i<=originalDates.size()-1;i++){
            System.out.println();
            map.put(originalDates.get(i),multiplexDates.get(i));
        }
        return map;
    }


    /**
     * 年月日日期转化星期
     * @param datetime 年月日字符串
     * @return 星期
     */
    public static String dateToWeek(String datetime)  {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
            if (w < 0)
                w = 0;
            System.out.println(weekDays[w]);//星期二
            return weekDays[w];
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param date 小时分钟
     * @param longDate 时间长度
     * @return 小时分钟时间
     */
    public static String addMinute(String date,int longDate){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date parse = sdf.parse(date);
            Date afterDate = new Date(parse.getTime() + 60000*longDate);
            System.out.println(afterDate);
            String format = sdf.format(afterDate);
            System.out.println(format);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



    /**
     * @param startTime 开始时间（格式08:00）
     * @param endTime 结束时间（格式10:00）
     * @return 时间差（分钟单位）
     */
    public static int calculateTime(String startTime,String endTime){
        String[] start = startTime.split(":");
        String[] end = endTime.split(":");
        int startMin = Integer.parseInt(start[0])*60+Integer.parseInt(start[1]);
        int endMin = Integer.parseInt(end[0])*60+Integer.parseInt(end[1]);
        return endMin-startMin;
    }
    /**
     * @param birthDay 生日
     * @return 年龄
     */
    public static int getAgeByBirth(Date birthDay) {
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        //age++;
        return age;
    }

    /**
     * 将指定的日期字符串转换成日期
     * @param dateStr 日期字符串
     * @param pattern 格式
     * @return 日期对象
     */
    public static Date parseDate(String dateStr, String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            throw  new RuntimeException("日期转化错误");
        }

        return date;
    }
}
