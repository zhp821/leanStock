package com.lean.stock.util;

import java.util.*;
import java.text.*;

public class DateUtil
{
    static SimpleDateFormat df;
    
    public static int daysBetween(final String smdate, final String bdate) throws ParseException {
        final Date date1 = DateUtil.df.parse(smdate);
        final Date date2 = DateUtil.df.parse(bdate);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        final long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        final long time2 = cal.getTimeInMillis();
        final long between_days = (time2 - time1) / 86400000L;
        return Integer.parseInt(String.valueOf(between_days));
    }
    
    public static Date addNumDaysToTime(final int num, final String date) throws ParseException {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.df.parse(date));
        calendar.add(5, num);
        return calendar.getTime();
    }
    
    public static String addNumDaysToTimeString(final int num, final String date) throws ParseException {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.df.parse(date));
        calendar.add(5, num);
        return DateUtil.df.format(calendar.getTime());
    }
    
    public static void main(final String[] args) throws ParseException {
        System.out.println(daysBetween("2017-7-31", "2017-9-13"));
    }
    
    static {
        DateUtil.df = new SimpleDateFormat("yyyy-MM-dd");
    }
}
