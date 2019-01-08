package com.hhh.quality.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

  public static final String SHORT_DATE_FORMAT_STR = "yyyy-MM-dd";
  public static final String LONG_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
  public static final String SHORT_DATE_FORMAT_STR_HOUR = "yyyy-MM-dd HH";
  /**
   * 日期格式 精确到秒 ：yyyyMMddHHmmss
   */
  public static final String DATE_FORMDATE_TO_SECOND = "yyyyMMddHHmmss";
  public static final DateFormat SHORT_DATE_FORMAT = new SimpleDateFormat(SHORT_DATE_FORMAT_STR);
  public static final DateFormat LONG_DATE_FORMAT = new SimpleDateFormat(LONG_DATE_FORMAT_STR);
  public static final DateFormat LONG_DATE_FORMAT_HOUR = new SimpleDateFormat(SHORT_DATE_FORMAT_STR_HOUR);
  public static final String EARLY_TIME = "00:00:00";
  public static final String EARLY_TIME_HOUR = ":00:00";
  public static final String TEN_TIME = "09:59:59";
  public static final String LATE_TIME = "23:59:59";
  private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);

  /**
   * 得到某个日期在这一天中时间最早的日期对象
   */
  public static Date getEarlyInTheDay(Date date) {
    String dateString = SHORT_DATE_FORMAT.format(date) + " " + EARLY_TIME;
    try {
      return LONG_DATE_FORMAT.parse(dateString);
    } catch (ParseException e) {
      throw new RuntimeException("parser date error.", e);
    }
  }

  public static Date firstDayOfNextQuarter(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    int curMonth = calendar.get(2);
    int firstMonthOfNextQuarter = curMonth / 3 * 3 + 3;

    calendar.add(2, firstMonthOfNextQuarter - curMonth);
    calendar.set(5, 1);

    return calendar.getTime();
  }

  public static int getMonthIntervals(Date date1, Date date2) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date1);
    int month1 = calendar.get(1) * 12 + calendar.get(2);

    calendar.setTime(date2);
    int month2 = calendar.get(1) * 12 + calendar.get(2);

    return month1 - month2;
  }

  public static int getYearIntervals(Date date1, Date date2) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date1);
    int year1 = calendar.get(1);

    calendar.setTime(date2);
    int year2 = calendar.get(1);

    return year1 - year2;
  }

  public static int getQuarterIntervals(Date date1, Date date2) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date1);
    int month1 = calendar.get(1) * 12 + calendar.get(2);

    calendar.setTime(date2);
    int month2 = calendar.get(1) * 12 + calendar.get(2);

    return month1 / 3 - month2 / 3;
  }

  public static long getDayIntervals(Date date1, Date date2) {
    return (date1.getTime() - 28800L) / 86400000L - (date2.getTime() - 28800L) / 86400000L;
  }

  public static boolean isSameDay(Date date1, Date date2) {
    return getIntDate(date1.getTime()) == getIntDate(date2.getTime());
  }

  public static boolean isSameMonth(Date date1, Date date2) {
    return getMonthIntervals(date1, date2) == 0;
  }

  public static boolean isSameYear(Date date1, Date date2) {
    return getYearIntervals(date1, date2) == 0;
  }

  public static boolean isSameQuarter(Date date1, Date date2) {
    return getQuarterIntervals(date1, date2) == 0;
  }

  public static int getYear(long time) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);

    return calendar.get(1);
  }

  public static int getYearAndMon(long time) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);

    return calendar.get(1) * 100 + calendar.get(2) + 1;
  }

  public static int getYearAndQuarter(long time) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);

    return calendar.get(1) * 100 + calendar.get(2) / 3 + 1;
  }

  public static int getIntDate(long time) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);

    return calendar.get(1) * 10000 + (calendar.get(2) + 1) * 100 + calendar.get(5);
  }

  static Logger logger = LoggerFactory.getLogger(DateUtils.class);

  private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

  private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

  private final static SimpleDateFormat sdfYearMonth = new SimpleDateFormat("yyyy-MM");

  private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

  private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private final static SimpleDateFormat sdfHours = new SimpleDateFormat("HH");

  private final static SimpleDateFormat sdfMinue = new SimpleDateFormat("mm");

  public static final String YYYY_MM_DD_SLASH = "yyyy/MM/dd";

  public static final String YYYY_MM_DD = "yyyy-MM-dd";

  public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

  public static final String YYYYMMDD = "yyyyMMdd";

  public static final String YYYYMMDD_CN = "yyyy年MM月dd日";

  public static final String YYYYMMDDHHMMSS_CN = "yyyy年MM月dd日 HH时mm分ss秒";

  public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmssSSS";

  public static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY; // 中国周一是一周的第一天

  /**
   * 获取YYYY格式
   */
  public static String getYear() {
    return sdfYear.format(new Date());
  }

  /**
   * 获取YYYY-MM-DD格式
   */
  public static String getDay() {
    return sdfDay.format(new Date());
  }

  /**
   * 获取YYYY-MM-DD格式
   */
  public static String getYearMonth() {
    return sdfYearMonth.format(new Date());
  }

  /**
   * 获取YYYYMMDD格式
   */
  public static String getDays() {
    return sdfDays.format(new Date());
  }

  /**
   * HH
   * @return
   */
  public static String gethours() {
    return sdfHours.format(new Date());
  }

  /**
   * mm
   * @return
   */
  public static String getMinue() {
    return sdfMinue.format(new Date());
  }

  /**
   * 获取YYYY-MM-DD HH:mm:ss格式
   */
  public static String getTime() {
    return sdfTime.format(new Date());
  }

  /**
   * @return boolean
   * @throws @Title: compareDate
   * @Description: TODO(日期比较 ， 如果s > = e 返回true 否则返回false)
   */
  public static boolean compareDate(String s, String e) {
    if (fomatDate(s) == null || fomatDate(e) == null) {
      return false;
    }
    return fomatDate(s).getTime() >= fomatDate(e).getTime();
  }

  /**
   * 格式化日期
   */
  public static Date fomatDate(String date) {
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return fmt.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 校验日期是否合法
   */
  public static boolean isValidDate(String s) {
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    try {
      fmt.parse(s);
      return true;
    } catch (Exception e) {
      // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
      return false;
    }
  }

  /**
   * @param startTime
   * @param endTime
   * @return
   */
  public static int getDiffYear(String startTime, String endTime) {
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    try {
      // long aa=0;
      int years = (int) (
          ((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
              / 365);
      return years;
    } catch (Exception e) {
      // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
      return 0;
    }
  }

  /**
   * <li>功能描述：时间相减得到天数
   *
   * @return long
   * @author Administrator
   */
  public static long getDaySub(String beginDateStr, String endDateStr) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date beginDate = null;
    Date endDate = null;
    try {
      beginDate = format.parse(beginDateStr);
      endDate = format.parse(endDateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    long day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
    return day;
  }

  public static long getDaySub(Date beginDate, Date endDate) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return getDaySub(format.format(beginDate), format.format(endDate));
  }

  /**
   * 得到n天之后的日期
   */
  public static String getAfterDayDate(String days) {
    int daysInt = Integer.parseInt(days);

    Calendar canlendar = Calendar.getInstance(); // java.util包
    canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
    Date date = canlendar.getTime();

    SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateStr = sdfd.format(date);

    return dateStr;
  }

  /**
   * 得到n天之后的日期
   */
  public static String getAfterDay(String days) {
    int daysInt = Integer.parseInt(days);

    Calendar canlendar = Calendar.getInstance(); // java.util包
    canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
    Date date = canlendar.getTime();

    SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
    String dateStr = sdfd.format(date);

    return dateStr;
  }

  //月份最后一天
  public static String getLastDay(String days) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try{
      Date dateOne = dateFormat.parse(days);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dateOne);
      calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
      Date date = calendar.getTime();
      String dateStr = dateFormat.format(date);
      return dateStr;
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 得到n天之后是周几
   */
  public static String getAfterDayWeek(String days) {
    int daysInt = Integer.parseInt(days);
    Calendar canlendar = Calendar.getInstance(); // java.util包
    canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
    Date date = canlendar.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("E");
    String dateStr = sdf.format(date);
    return dateStr;
  }

  /**
   * 时间格式化
   */
  public static String format(Date date, String pattern) {
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    return format.format(date);
  }

  /**
   * 获取上一个月最后一天
   */
  public static Date lastMonthEnd(Date date) {
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(date);

    calendar.set(Calendar.DAY_OF_MONTH, 1);

    calendar.add(Calendar.DAY_OF_MONTH, -1);

    return calendar.getTime();
  }
  /**
   * 获取当前时间前一天
   */
  public static String lastDay() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    date = calendar.getTime();
    return sdf.format(date);
  }

  /**
   * 获取当前时间前一天0:00:00
   */
  public static String lastDayMor() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    date = calendar.getTime();
    return sdf.format(date) + " 00:00:00";
  }

  /**
   * 获取上一年最后一天
   */
  public static Date lastYearEnd(Date date) {
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(date);

    calendar.set(Calendar.DAY_OF_MONTH, 1);

    calendar.set(Calendar.MONTH, 0);

    calendar.add(Calendar.DAY_OF_MONTH, -1);

    return calendar.getTime();
  }

  /**
   * 获取date日期所在月份第一天
   */
  public static Date firstDayOfMonth(Date date) {
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(date);

    calendar.set(Calendar.DAY_OF_MONTH, 1);
    return calendar.getTime();
  }

  /**
   * 获取date日期所在年份第一天
   */
  public static Date firstDayOfYear(Date date) {
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(date);

    calendar.set(Calendar.DAY_OF_MONTH, 1);

    calendar.set(Calendar.MONTH, 0);
    return calendar.getTime();
  }

  /**
   * 比较两个时间大小
   *
   * @return 0相等 1 d1大于 d2 -1 d1小于d2
   */
  public static int compareDay(Date d1, Date d2) {
    Calendar time = Calendar.getInstance();
    time.setTime(d1);
    time.set(Calendar.MILLISECOND, 0);
    time.set(Calendar.SECOND, 0);
    time.set(Calendar.MINUTE, 0);
    time.set(Calendar.HOUR_OF_DAY, 0);
    Date newD1 = time.getTime();
    time.setTime(d2);
    time.set(Calendar.MILLISECOND, 0);
    time.set(Calendar.SECOND, 0);
    time.set(Calendar.MINUTE, 0);
    time.set(Calendar.HOUR_OF_DAY, 0);
    Date newD2 = time.getTime();
    return newD1.compareTo(newD2);
  }

  /**
   * 获取当前时间 精确到毫秒
   */
  public static Date getCurrDateTime() {
    Calendar time = Calendar.getInstance();
    time.setTimeInMillis(System.currentTimeMillis());
    time.set(Calendar.MILLISECOND, 0);
    return time.getTime();
  }

  /**
   * 给date增加 days天
   */
  public static Date addDays(Date date, int days) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, days);
    return calendar.getTime();
  }

  /**
   * 给date增加 seconds秒
   */
  public static Date addSeconds(Date date, int seconds) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.SECOND, seconds);
    return calendar.getTime();
  }

  /**
   * 给date增加minutes分钟
   */
  public static Date addMinutes(Date date, int minutes) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MINUTE, minutes);
    return calendar.getTime();
  }

  /**
   * 给date增加hours小时
   */
  public static Date addHours(Date date, int hours) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR_OF_DAY, hours);
    return calendar.getTime();
  }

  /**
   * 给date增加months月
   */
  public static Date addMonths(Date date, int months) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MONTH, months);
    return calendar.getTime();
  }

  /**
   * 给date增加years年
   */
  public static Date addYears(Date date, int years) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.YEAR, years);
    return calendar.getTime();
  }

  /**
   * 获取当前日期
   *
   * @return 时分秒均为0
   */
  public static Date getCurrDay() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    return calendar.getTime();
  }

  /**
   * 获取day的下一个日期
   */
  public static Date nextDay(Date day) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(day);
    calendar.add(Calendar.DATE, 1);
    return calendar.getTime();
  }

  /**
   * 清空date中的time段,重置为0
   */
  public static Date clearTime(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    return calendar.getTime();
  }

  /**
   * 清空毫秒
   */
  public static Date clearMilliSecond(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }

  /**
   * 指定时间 是否在 当前时间 之后，注：和日期无关
   *
   * @param time 指定的时间， 传入样例:16:23:42:267 或 16(等同于16:00:00:000)
   */
  public static boolean isAfterTime(String time) {
    Date date = parseTime(new Date(), time);
    return date.after(new Date());
  }

  /**
   * 设定date的时间细节
   *
   * @param date 要设定时间细节的date
   * @param timeDetail 以:号分隔的24小时制的时间，例:16:23:42:267 或 16(等同于16:00:00:000)
   */
  public static Date parseTime(Date date, String timeDetail) {

    List<String> strList = new ArrayList<String>();
    strList.addAll(Arrays.asList(timeDetail.split(":")));

    while (strList.size() < 4) {
      strList.add("0");
    }
    return parseTime(date, Integer.parseInt(strList.get(0)), Integer.parseInt(strList.get(1)),
        Integer.parseInt(strList.get(2)), Integer.parseInt(strList.get(3)));
  }

  /**
   * 设定date的时间细节
   *
   * @param date 要设定时间细节的date
   * @param hourOfDay 0-23
   * @param minute 0-59
   * @param second 0-59
   * @param milliSecond 0-999
   */
  public static Date parseTime(Date date, int hourOfDay, int minute, int second, int milliSecond) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    setCalendarTime(cal, hourOfDay, minute, second, milliSecond);
    return cal.getTime();
  }

  private static void setCalendarTime(Calendar cal, int hourOfDay, int minute, int second,
      int milliSecond) {
    cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
    cal.set(Calendar.MINUTE, minute);
    cal.set(Calendar.SECOND, second);
    cal.set(Calendar.MILLISECOND, milliSecond);
  }

  /**
   * 获取上个月的当前日期
   */
  public static String getTodayOfPrevMonth(Date date, String pattrn) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.MONTH, -1);
    return format(c.getTime(), pattrn);
  }

  /**
   * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
   */
  public static int getSeason(Date date) {
    int season = 0;
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    int month = c.get(Calendar.MONTH);
    switch (month) {
      case Calendar.JANUARY:
      case Calendar.FEBRUARY:
      case Calendar.MARCH:
        season = 1;
        break;
      case Calendar.APRIL:
      case Calendar.MAY:
      case Calendar.JUNE:
        season = 2;
        break;
      case Calendar.JULY:
      case Calendar.AUGUST:
      case Calendar.SEPTEMBER:
        season = 3;
        break;
      case Calendar.OCTOBER:
      case Calendar.NOVEMBER:
      case Calendar.DECEMBER:
        season = 4;
        break;
      default:
        break;
    }
    return season;
  }

  /**
   * 取得季度月
   */
  public static Date[] getSeasonDate(Date date) {
    Date[] season = new Date[3];

    Calendar c = Calendar.getInstance();
    c.setTime(date);

    int nSeason = getSeason(date);
    if (nSeason == 1) {// 第一季度
      c.set(Calendar.MONTH, Calendar.JANUARY);
      season[0] = c.getTime();
      c.set(Calendar.MONTH, Calendar.FEBRUARY);
      season[1] = c.getTime();
      c.set(Calendar.MONTH, Calendar.MARCH);
      season[2] = c.getTime();
    } else if (nSeason == 2) {// 第二季度
      c.set(Calendar.MONTH, Calendar.APRIL);
      season[0] = c.getTime();
      c.set(Calendar.MONTH, Calendar.MAY);
      season[1] = c.getTime();
      c.set(Calendar.MONTH, Calendar.JUNE);
      season[2] = c.getTime();
    } else if (nSeason == 3) {// 第三季度
      c.set(Calendar.MONTH, Calendar.JULY);
      season[0] = c.getTime();
      c.set(Calendar.MONTH, Calendar.AUGUST);
      season[1] = c.getTime();
      c.set(Calendar.MONTH, Calendar.SEPTEMBER);
      season[2] = c.getTime();
    } else if (nSeason == 4) {// 第四季度
      c.set(Calendar.MONTH, Calendar.OCTOBER);
      season[0] = c.getTime();
      c.set(Calendar.MONTH, Calendar.NOVEMBER);
      season[1] = c.getTime();
      c.set(Calendar.MONTH, Calendar.DECEMBER);
      season[2] = c.getTime();
    }
    return season;
  }

  /**
   * 取得季度天数
   */
  public static int getDayOfSeason(Date date) {
    int day = 0;
    Date[] seasonDates = getSeasonDate(date);
    for (Date date2 : seasonDates) {
      day += getDayOfMonth(date2);
    }
    return day;
  }

  /**
   * 取得季度剩余天数
   */
  public static int getRemainDayOfSeason(Date date) {
    return getDayOfSeason(date) - getPassDayOfSeason(date);
  }

  /**
   * 取得季度已过天数
   */
  public static int getPassDayOfSeason(Date date) {
    int day = 0;

    Date[] seasonDates = getSeasonDate(date);

    Calendar c = Calendar.getInstance();
    c.setTime(date);
    int month = c.get(Calendar.MONTH); // 季度第一个月
    if (month == Calendar.JANUARY || month == Calendar.APRIL || month == Calendar.JULY
        || month == Calendar.OCTOBER) {
      day = getPassDayOfMonth(seasonDates[0]);
    } else if (month == Calendar.FEBRUARY || month == Calendar.MAY || month == Calendar.AUGUST
        || month == Calendar.NOVEMBER) { // 季度第二个月
      day = getDayOfMonth(seasonDates[0]) + getPassDayOfMonth(seasonDates[1]);
    } else if (month == Calendar.MARCH || month == Calendar.JUNE || month == Calendar.SEPTEMBER
        || month == Calendar.DECEMBER) { // 季度第三个月
      day = getDayOfMonth(seasonDates[0]) + getDayOfMonth(seasonDates[1]) + getPassDayOfMonth(
          seasonDates[2]);
    }
    return day;
  }

  /**
   * 取得季度最后一天
   */
  public static Date getLastDateOfSeason(Date date) {
    return getLastDateOfMonth(getSeasonDate(date)[2]);
  }

  /**
   * 取得季度第一天
   */
  public static Date getFirstDateOfSeason(Date date) {
    return getFirstDateOfMonth(getSeasonDate(date)[0]);
  }

  /**
   * 取得一年的第几周
   */
  public static int getWeekOfYear(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    int week_of_year = c.get(Calendar.WEEK_OF_YEAR);
    return week_of_year - 1;
  }

  /**
   * getWeekBeginAndEndDate
   */
  public static String getWeekBeginAndEndDate(Date date, String pattern) {
    Date monday = getMondayOfWeek(date);
    Date sunday = getSundayOfWeek(date);
    return format(monday, pattern) + " - " + format(sunday, pattern);
  }

  /**
   * 根据日期取得对应周周一日期
   */
  public static Date getMondayOfWeek(Date date) {
    Calendar monday = Calendar.getInstance();
    monday.setTime(date);
    monday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
    monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    return monday.getTime();
  }

  /**
   * 根据日期取得对应周周日日期
   */
  public static Date getSundayOfWeek(Date date) {
    Calendar sunday = Calendar.getInstance();
    sunday.setTime(date);
    sunday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
    sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    return sunday.getTime();
  }

  /**
   * 取得月的剩余天数
   */
  public static int getRemainDayOfMonth(Date date) {
    int dayOfMonth = getDayOfMonth(date);
    int day = getPassDayOfMonth(date);
    return dayOfMonth - day;
  }

  /**
   * 取得月已经过的天数
   */
  public static int getPassDayOfMonth(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * 取得月天数
   */
  public static int getDayOfMonth(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  /**
   * 取得月第一天
   */
  public static Date getFirstDateOfMonth(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
    return c.getTime();
  }

  /**
   * 取得月最后一天
   */
  public static Date getLastDateOfMonth(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    return c.getTime();
  }

  /**
   * 获取当月所有的日期
   */
  public static List<Date> getAllTheDateOftheMonth(Date date) {
    List<Date> list = new ArrayList<Date>();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.DATE, 1);
    int month = cal.get(Calendar.MONTH);
    while (cal.get(Calendar.MONTH) == month) {
      list.add(cal.getTime());
      cal.add(Calendar.DATE, 1);
    }
    return list;
  }

  /**
   * 产生周序列,即得到当前时间所在的年度是第几周
   */
  public static String getSeqWeek() {
    Calendar c = Calendar.getInstance(Locale.CHINA);
    String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
    if (week.length() == 1) {
      week = "0" + week;
    }
    String year = Integer.toString(c.get(Calendar.YEAR));
    return year + week;
  }

  /**
   * 根据一个日期，返回是星期几的字符串
   */
  public static String getWeek(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    // int hour=c.get(Calendar.DAY_OF_WEEK);
    // hour中存的就是星期几了，其范围 1~7
    // 1=星期日 7=星期六，其他类推
    return new SimpleDateFormat("EEEE").format(c.getTime());
  }

  public static String getWeekStr(Date sdate) {
    String str = "";
    str = getWeek(sdate);
    if ("1".equals(str)) {
      str = "星期日";
    } else if ("2".equals(str)) {
      str = "星期一";
    } else if ("3".equals(str)) {
      str = "星期二";
    } else if ("4".equals(str)) {
      str = "星期三";
    } else if ("5".equals(str)) {
      str = "星期四";
    } else if ("6".equals(str)) {
      str = "星期五";
    } else if ("7".equals(str)) {
      str = "星期六";
    }
    return str;
  }

  /**
   * 判断日期是否为周末
   */
  public static boolean dateIsWeekend(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
        || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 获取 date 凌晨 java 时间戳
   * @return
   */
  public static Long getDateZeroLong(Date date){
    Calendar now = Calendar.getInstance();
    now.setTime(date);
    now.set(Calendar.HOUR_OF_DAY, 0);
    now.set(Calendar.MINUTE, 0);
    now.set(Calendar.SECOND, 0);
    now.set(Calendar.MILLISECOND, 0);

    return now.getTimeInMillis();
  }

  /**
   * 获取当前时间 整点 java 时间戳
   * @param date
   * @return
   */
  public static Long getHourZeroLong (Date date) {

    Calendar now = Calendar.getInstance();
    now.setTime(date);
    now.set(Calendar.HOUR_OF_DAY, Integer.parseInt(gethours()));
    now.set(Calendar.MINUTE, 0);
    now.set(Calendar.SECOND, 0);
    now.set(Calendar.MILLISECOND, 0);

    return now.getTimeInMillis();
  }

  /**
   * 获取当前时间 整分钟 java 时间戳
   * @param date
   * @return
   */
  public static Long getMinueZeroLong (Date date) {

    Calendar now = Calendar.getInstance();
    now.setTime(date);
    now.set(Calendar.HOUR_OF_DAY, Integer.parseInt(gethours()));
    now.set(Calendar.MINUTE, Integer.parseInt(getMinue()));
    now.set(Calendar.SECOND, 0);
    now.set(Calendar.MILLISECOND, 0);

    return now.getTimeInMillis();
  }

  /**
   * 获取 十天前 凌晨 java 时间戳
   * @return
   */
  public static Long get10DayZeroLong () {

    Calendar now = Calendar.getInstance();
    now.set(Calendar.HOUR_OF_DAY, 0);
    now.set(Calendar.MINUTE, 0);
    now.set(Calendar.SECOND, 0);
    now.set(Calendar.MILLISECOND, 0);
    now.add(Calendar.DATE, -10);

    return now.getTimeInMillis();
  }

  /**
   * 得到某个日期在这一天中10点的价格
   */
  public static Date getTENInTheDay(Date date) {
    String dateString = SHORT_DATE_FORMAT.format(date) + " " + TEN_TIME;
    try {
      return LONG_DATE_FORMAT.parse(dateString);
    } catch (ParseException e) {
      throw new RuntimeException("parser date error.", e);
    }
  }

  //发送mq  id
  public static String randomStr(int len) {
    StringBuffer buffer = new StringBuffer();
    Random random = new Random();

    for(int i = 0; i < len; ++i) {
      buffer.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
    }
    return buffer.toString();
  }

  public static void main(String[] args) {
    get10DayZeroLong();
  }
}
