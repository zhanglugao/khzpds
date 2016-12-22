package com.khzpds.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** 
 * DateUtil
 * <p>Title: 日期工具处理类</p>
 * <p>Description: 日期各种转换工具</p>
 * <p>Date: 2011-7-23</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author huangchaoshi
 * @version 1.0
 */
public class DateUtil {
    private final static TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
    public final static String DEFAULTFORMAT = "yyyy-MM-dd";
    public final static String DATEFORMAT = "yyyy年MM月dd日";
    public final static String MONTHFORMAT = "yyyy-MM";
    public final static String YEARFORMAT = "yyyy";
    public final static String SECONDFORMAT="yyyy-MM-dd HH:mm:ss";
    public final static String SMSDATE="yyyyMMddHHmmss";
    private static final Log log = LogFactory.getLog(DateUtil.class);
    /**
     * @param msel
     *            毫秒时间
     * @return 毫秒时间 所对应的"yyyy-MM-dd HH:mm"格式的日期
     */

    public static String formatTime(long msel) {
        Date date = new Date(msel);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    public static String formatTime2(long msel) {
        Date date = new Date(msel);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    /**
     * @param msel
     *            毫秒时间
     * @return 毫秒时间 所对应的"yyyy-MM-dd"格式的日期
     */
    public static String formatDate(long msel) {
        Date date = new Date(msel);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    public static String formatDate2String(Date date) {
        if(date==null)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(SECONDFORMAT);
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    /***
     * 根据传入进来的format格式化日期 如果format为null 返回 yyyy-MM-dd HH:mm:ss格式
     * @param date
     * @param format
     * @return
     */
    public static String formatDate2String(Date date,String format) {
        if(date==null)
            return null;
        SimpleDateFormat formatter =null;
        if(format!=null){
	        formatter = new SimpleDateFormat(format);
        }else{
        	formatter = new SimpleDateFormat(SECONDFORMAT);
        }
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    public static String formatDate2(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    /**
     * @param strDate
     * @param strOldFormat
     * @return
     */
    public static String formatDate(String strDate, String strOldFormat,
            String strNewFormat) {
        Date date = getDate(strDate, strOldFormat);
        SimpleDateFormat formatter = new SimpleDateFormat(strNewFormat);
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    /**
     * 得到系统当前的年
     *
     * @return
     */
    public static int getCurrentYear() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 得到系统当前的月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到系统当前的日期
     *
     * @return
     */
    public static int getCurrentDay() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static long getCurrentMSEL() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.getTimeInMillis();
    }

    /**
     * 得到系统的当前时间 YYYYMMDD
     *
     * @return
     */
    public static int getThisday() {
        StringBuffer sb = new StringBuffer(8);
        sb.append(getCurrentYear());
        int iMonth = getCurrentMonth();
        if (String.valueOf(iMonth).length() == 1)
            sb.append("0" + iMonth);
        else
            sb.append(iMonth);
        int iDay = getCurrentDay();
        if (String.valueOf(iDay).length() == 1)
            sb.append("0" + iDay);
        else
            sb.append(iDay);
        return new Integer(sb.toString()).intValue();
    }

    public static int getDayDef(int year, int month, int day) {
        StringBuffer sb = new StringBuffer(8);
        sb.append(year);
        int iMonth = month;
        int iDay = day;
        if (String.valueOf(iMonth).length() == 1)
            sb.append("0" + iMonth);
        else
            sb.append(iMonth);
        if (String.valueOf(iDay).length() == 1)
            sb.append("0" + iDay);
        else
            sb.append(iDay);
        return new Integer(sb.toString()).intValue();
    }

    public static int getDayDef(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getDayDef(year, month, day);
    }

    /**
     * 将一个字符串的日期描述转换为java.util.Date对象
     *
     * @param strDate
     *            字符串的日期描述
     * @param format
     *            字符串的日期格式，比如:“yyyy-MM-dd HH:mm”
     * @return 字符串转换的日期对象java.util.Date
     */
    public static Date getDate(String strDate, String format) {
        if (strDate == null || strDate.trim().equals("")) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(timeZone);
        Date date;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    /**
     * 将一个字符串的日期描述转换为java.util.Date对象
     * @param strDate 支持"yyyy"和"yyyy-MM-dd"格式
     * @return
     */
    public static Date getDate(String strDate){
        Date destDate=null;
        if(strDate != null && strDate.trim().length() > 0){
            if(strDate.trim().length() == 4){
                destDate=DateUtil.getDate(strDate, DateUtil.YEARFORMAT);
            }else if(strDate.trim().length() == 10){
            	destDate=DateUtil.getDate(strDate, DateUtil.DEFAULTFORMAT);
            }else{
                destDate=DateUtil.getDate(strDate, DateUtil.SECONDFORMAT);
            }           
        }
        return destDate;
    }
    
    public static long getMSEL(int i) {
        String original = "20040501";
        if (i > 0) {
            original = Integer.toString(i);
        }
        StringBuffer temp = new StringBuffer();
        temp.append(original).append("000000");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        long rs = 0;
        Date date;
        try {
            date = formatter.parse(temp.toString());
        } catch (ParseException e) {
            //log.info("data formate exception " + e.getMessage());
            date = new Date();
        }
        long l = date.getTime();
        return l;
    }

    /**
     * 将字符串型的时间转换为毫秒数，格式默认为yyyy-MM-dd HH:mm
     *
     * @param Date
     * @return
     */
    public static long getMSEL(String strDate) {
        Date date = getDate(strDate, "yyyy-MM-dd HH:mm");
        if (date == null) {
            return 0l;
        } else {
            return date.getTime();
        }
    }

    /**
     * 将字符串型的时间转换为毫秒数，格式默认为yyyyMMdd
     *
     * @param Date
     * @return long
     */
    public static long getMSELByFormat(String strDate) {
        Date date = getDate(strDate, "yyyyMMdd");
        if (date == null) {
            return 0l;
        } else {
            return date.getTime();
        }
    }

    public static long getMSEL(String strDate, String format) {
        Date date = getDate(strDate, format);
        if (date == null) {
            return 0l;
        } else {
            return date.getTime();
        }
    }



    /**
     *
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @return 毫秒数
     */
    public static long getTimeperiodMSEL(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime().getTime();
    }

    /**
     *
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @return 毫秒数
     */
    public static long getTimeperiodMSELInit(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year, month, day, 23, 59, 59);
        return calendar.getTime().getTime();
    }

    /**
     *
     * @return 当天指定格式的日期
     */
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(new Date());
    }
    public static String getCurrentdate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(new Date());
    }

    public static String getCurrenttime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    /**
     *
     * @param day
     *            推移的天数
     * @return 以当前时间加天数的日期
     */
    public static String addCurrentDate(int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }

    /**
     *
     * @param day
     *            推移的天数
     * @return date时间加天数的日期
     */
    public static String addDate(String date, int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(getDate(date, "yyyy-MM-dd"));
        calendar.add(GregorianCalendar.DAY_OF_MONTH, day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }
    
    public static Date addDate(Date date,int day){
    	 Calendar calendar=Calendar.getInstance();   
    	 calendar.setTime(date);
    	 calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+day);
    	 return calendar.getTime();
    }

    // public static String addCurrentMonth(int month)
    // {
    // GregorianCalendar calendar = new GregorianCalendar();
    // calendar.setTime(new Date());
    // calendar.add(GregorianCalendar., day);
    // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    // return format.format(calendar.getTime());
    // }

    public static void get7dayFormCur() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 3);
        int i = calendar.get(GregorianCalendar.DAY_OF_WEEK);
    }

    /**
     * 得到本周的第一天
     *
     * @return
     */
    public static int getCurrentFirstWeekDay() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int day = cal.getMinimum(GregorianCalendar.DAY_OF_WEEK);
        return day;
    }

    /**
     * 得到本周的最后一天
     *
     * @return
     */
    public static int getCurrentLastWeekDay() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int day = cal.getMaximum(GregorianCalendar.DAY_OF_WEEK);
        return day;
    }

    /**
     * 得到当月的第一天
     *
     * @return
     */
    public static String getCurrentFirstDay4Month() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int day = cal.getMinimum(GregorianCalendar.DAY_OF_MONTH);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(day);
    }

    /**
     * 得到当月的第一天
     *
     * @return
     */
    public static int getCurrentFirstMonthDay() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int day = cal.getMinimum(GregorianCalendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 得到当月的最后一天
     *
     * @return
     */
    public static String getCurrentLastDay4Month() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int day = cal.getMaximum(GregorianCalendar.DAY_OF_MONTH);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(day);
    }

    /**
     * 得到当月的最后一天
     *
     * @return
     */
    public static int getCurrentLastMonthDay() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int day = cal.getMaximum(GregorianCalendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 将指定的毫秒转换为YYYYMMDD的格式
     *
     * @param msel
     * @return
     */
    public static int formatMSEL(long msel) {
        Date date = new Date(msel);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        StringBuffer sb = new StringBuffer();
        sb.append(year);
        if (String.valueOf(month).length() == 1)
            sb.append("0" + month);
        else
            sb.append(month);
        if (String.valueOf(day).length() == 1)
            sb.append("0" + day);
        else
            sb.append(day);
        return new Integer(sb.toString()).intValue();
    }

    public static String getTomorrow(String strDate) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = formatter.parse(strDate);
        long temp = date.getTime() + 24 * 3600 * 1000;
        return formatDate(temp);
    }
    
    public static String getYesterday(){
        Date nowDate=new Date();
        long temp = nowDate.getTime() - 24 * 3600 * 1000;
        return formatDate(temp);
    }

    public static String getCurMonday() {
        GregorianCalendar calendar = new GregorianCalendar();
        
        calendar.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(calendar.getTime());
    }

    public static String getCurSunday() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.SUNDAY);
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 7);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(calendar.getTime());
    }

    public static int getCurrentDay4Week() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static long[] getDayFromNow(Date dateIn){
        long[] ret=new long[0];

        if(dateIn!=null){
            ret=new long[3];
            long dateInL=dateIn.getTime();
            Date nowDate=new Date();
            long nowDateL=nowDate.getTime();

            long space=0;
            if(nowDateL>dateInL){
                space=nowDateL-dateInL;
            }else{
                space=dateInL-nowDateL;
            }

            long min=space/(60*1000);
            long day=min/(24*60);
            long hour=(min-day*24*60)/60;
            min=min-day*24*60-hour*60;

            ret[0]=day;
            ret[1]=hour;
            ret[2]=min;
            //ret=day+"天"+hour+"小时"+min+"分钟";
        }

        return ret;
    }
    
    /**
     * 所给日期按照指定格式转换为字符串
     * @param date
     * @param format
     * @return String
     */
    public static String date2String(Date date,String format){
        if(date == null){
            return "";
        }
        if(format == null){
            format = "yyyy-MM-dd";
        }else if(format.equals("")){
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    
    public static String getFirstDay4Quarter(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH)+1;
        String firstMonthOfQuarter = "";
        if(month>0&&month<4)
            firstMonthOfQuarter = "01";
        else if(month>3&&month<7)
            firstMonthOfQuarter = "04";
        else if(month>6&&month<10)
            firstMonthOfQuarter = "07";
        else
            firstMonthOfQuarter = "10";
        return getCurrentYear()+"-"+firstMonthOfQuarter+"-"+"01";
    }
    
    public static String getLastDay4Quarter(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH)+1;
        String firstMonthOfQuarter = "";
        if(month>0&&month<4)
            firstMonthOfQuarter = "03";
        else if(month>3&&month<7)
            firstMonthOfQuarter = "06";
        else if(month>6&&month<10)
            firstMonthOfQuarter = "09";
        else
            firstMonthOfQuarter = "12";
        return getCurrentYear()+"-"+firstMonthOfQuarter+"-"+getLastDay4Month(Integer.parseInt(firstMonthOfQuarter));
    }

    /**
     * 得到当月的第一天
     *
     * @return
     */
    public static int getFirstDay4Month(int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month-1);
        return calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到指定月的最后一天
     *
     * @return
     */
    public static int getLastDay4Month(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month-1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到指定日所在周的第一天
     *
     * @return
     */
    public static String getMonday4Week(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cal.getTime());
    }

    /**
     * 得到指定日所在周的最后一天
     *
     * @return
     */
    public static String getSunday4Week(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.SUNDAY);
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 7);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(calendar.getTime());
    }

    /**
     * 得到指定月的第一天
     *
     * @return
     */
    public static String getFirstDay4Month(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        int day = getFirstDay4Month(cal.get(GregorianCalendar.MONTH)+1);
        cal.set(GregorianCalendar.DAY_OF_MONTH,day);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cal.getTime());
    }

    /**
     * 得到指定月的最后一天
     *
     * @return
     */
    public static String getLastDay4Month(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        int day = getLastDay4Month(cal.get(GregorianCalendar.MONTH)+1);
        cal.set(GregorianCalendar.DAY_OF_MONTH, day);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cal.getTime());
    }

    /**
     * 得到指定年的第一天
     *
     * @return
     */
    public static String getFirstDay4Year(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        int day = cal.getMinimum(GregorianCalendar.DAY_OF_YEAR);
        cal.set(GregorianCalendar.MONTH, 0);
        cal.set(GregorianCalendar.DAY_OF_MONTH, day);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cal.getTime());
    }

    /**
     * 得到指定年的最后一天
     *
     * @return
     */
    public static String getLastDay4Year(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(GregorianCalendar.MONTH, 11);
        int day = cal.getMaximum(GregorianCalendar.DAY_OF_MONTH);
        cal.set(GregorianCalendar.DAY_OF_MONTH, day);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cal.getTime());
    }
    
    public static int getLastYear(){
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.YEAR)-1;
    }
    
    public static int getLastLastYear(){
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.YEAR)-2;
    }
    
    /**
     * 提前给定日期的号
     * @param date
     * @return
     */
    public static int getDayOfDate(Date date){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);     
    }
    /**
     * 得到n年后的今天
     * @param date
     * @return
     */
    public static Date get_Date(int n){
    	Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, +n);
        date = calendar.getTime();
        return date;
    }
    
    /**
	 * 根据用户生日计算年龄
	 */
	public static int getAgeByBirthday(Date birthday) {
		if(birthday == null) return 0;
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) {
			throw new IllegalArgumentException("The birthDay is before Now.");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}
	/**
	* @Title:验证字符串是否是日期类型
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param str
	* @param @param formatStr
	* @param @return  参数说明 
	* @return boolean    返回类型 
	* @throws
	 */
	public static boolean isValidDate(String str,String formatStr) {
	      boolean convertSuccess=true;
	       SimpleDateFormat format = new SimpleDateFormat(formatStr);
	       try {
	          format.setLenient(false);
	          format.parse(str);
	       } 
	       catch (ParseException e) {
	           // log.error(e.getMessage(),e);
               // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
	           convertSuccess=false;
	       } 
	       return convertSuccess;
	}
	
	/**
     * 
    * @Title: getYear 
    * @Description: 获取年份
    * @param @return  参数说明 
    * @return List<Integer>    返回类型 
    * @throws
     */
    public static List<Integer> getYear() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String sdate = sdf.format(date);
        int today = Integer.parseInt(sdate);
        int before = today-10;
        int after = today+10;
        List<Integer> dateList = new ArrayList<Integer>();
        for(int i=before;i<=after;i++) {
            dateList.add(i);
        }
        return dateList;
    }
    /**
    * @Title: 根据指定的日期字符串获取对应的星期几
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param date
    * @param @return  参数说明 
    * @return int    返回类型 
    * @throws
     */
    public static String getWeekDay(String date) {
        Date sd= getDate(date,"yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(sd);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }
	
    public static void main(String[] args){
//      log.info(getCurrentYear());
//      log.info(getLastYear());
//      log.info(getLastLastYear());
        log.info(getDayOfDate(DateUtil.getDate("2011-09-25 00:00:25")));
//      log.info(getSunday4Week(DateUtil.getDate("2011-9-2")));
//      long date = getMSEL("2005年1月1日","yyyy年MM月dd日");
//      log.info(formatMSEL(date));
//      Date date = getDate("20081","yyyymmdd");
//      log.info(getFirstDay4Month(DateUtil.getDate("2011-8-2")));
//      log.info(getLastDay4Month(DateUtil.getDate("2011-8-2")));
//      log.info(getFirstDay4Quarter(DateUtil.getDate("2011-8-2")));
//      log.info(getLastDay4Quarter(DateUtil.getDate("2011-8-2")));
//      Date date = new Date();
//      log.info(date);
//      String aa = date2String(date,DateUtil.SMSDATE);
//      log.info(aa);
//      log.info(getDate(aa,SECONDFORMAT));
        //log.info(date2String(date,"yyyy年mm月dd日"));
    }
}
