package tk.mybatis.springboot.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.springboot.base.exception.SystemException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class DateUtil
{
    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT2 = "yyyyMMdd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

    public static long getCurrentTimeMillis()
    {
        Timestamp time = getSysDate();
        return time.getTime();
    }

    public static String getCurrentTime()
    {
        return getDateString("yyyy-MM-dd HH:mm:ss");
    }

    public static Timestamp getSysDate()
    {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String getDateString(String pattern)
    {
        if (StringUtil.isBlank(pattern)) {}
        Timestamp time = getSysDate();
        DateFormat dfmt = new SimpleDateFormat(pattern);
        Date date = time;
        return dfmt.format(date);
    }

    public static String getDateString(Timestamp time, String pattern)
            throws SystemException
    {
        if (time == null) {
            throw new SystemException("time is null");
        }
        if (StringUtil.isBlank(pattern)) {
            throw new SystemException("pattern is null");
        }
        DateFormat dfmt = new SimpleDateFormat(pattern);
        Date date = time;
        return date != null ? dfmt.format(date) : "";
    }

    public static String getDateString(Date date, String pattern)
            throws SystemException
    {
        if (date == null) {
            throw new SystemException("date is null");
        }
        if (StringUtil.isBlank(pattern)) {
            throw new SystemException("pattern is null");
        }
        SimpleDateFormat sdfmt = new SimpleDateFormat(pattern);
        return date != null ? sdfmt.format(date) : "";
    }

    public static boolean isValidDate(String str, String fomat)
            throws SystemException
    {
        if (StringUtil.isBlank(str)) {
            throw new SystemException("str is null");
        }
        if (StringUtil.isBlank(fomat)) {
            throw new SystemException("fomat is null");
        }
        boolean flag = true;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(fomat);
            sdf.parse(str);
            flag = true;
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
            flag = false;
        }
        return flag;
    }

    public static Date str2Date(String str)
    {
        Date date = null;
        if (!StringUtil.isBlank(str)) {
            date = to_date(str, "yyyy-MM-dd HH:mm:ss");
        }
        return date;
    }

    public static Timestamp getFutureTime()
    {
        Date d = str2Timestamp("2100-01-01 00:00:00");
        return getBeforeSecond(new Timestamp(d.getTime()));
    }

    public static Date str2Timestamp(String str)
    {
        Date date = null;
        if (!StringUtil.isBlank(str)) {
            date = to_date(str, "yyyy-MM-dd HH:mm:ss");
        }
        return date;
    }

    public static Timestamp StrToTimestamp(String dateStr)
    {
        Timestamp valueOf = Timestamp.valueOf(dateStr);
        return valueOf;
    }

    public static Date to_date(String dateStr, String format)
            throws SystemException
    {
        if ((!StringUtil.isBlank(dateStr)) ||

                (StringUtil.isBlank(format))) {}
        DateFormat df = new SimpleDateFormat(format);
        try
        {
            Date date = df.parse(dateStr);
            return new Date(date.getTime());
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
            throw new SystemException(e.getMessage());
        }
    }

    public static Date getDate()
    {
        String s = getDateString("yyyy-MM-dd HH:mm:ss");
        return str2Date(s);
    }

    public static Date getTheDayDate(Timestamp sysDate)
    {
        DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sysDate;
        String dateString = dfmt.format(date);
        return str2Date(dateString);
    }

    public static Date getOffsetDaysDate(Timestamp sysDate, int offsetDays)
    {
        Timestamp t = getOffsetDaysTime(sysDate, offsetDays);
        return getTheDayDate(t);
    }

    public static Date getOffsetDaysDate(Date date, int offsetDays)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, offsetDays);
        return new Date(calendar.getTimeInMillis());
    }

    public static Timestamp getTheDayFirstSecond(Timestamp sysDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(13, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getTheDayLastSecond(Timestamp sysDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(13, -1);
        calendar.add(5, 1);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getOffsetDaysTime(Timestamp sysDate, int offsetDays)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(5, offsetDays);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getOffsetMonthsTime(Timestamp sysDate, int offsetDays)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(2, offsetDays);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getOffsetYearsTime(Timestamp sysDate, int offsetDays)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(1, offsetDays);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getTimeThisMonthLastSec(Timestamp sysDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(2, 1);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(13, -1);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getTimeThisMonthFirstSec(Timestamp sysDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(2, 0);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(13, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getTimeNextMonthFirstSec(Timestamp sysDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(2, 1);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(13, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static int getDaysOfThisMonth()
    {
        Timestamp currTimestamp = getSysDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currTimestamp);
        return calendar.getActualMaximum(5);
    }

    public static String getMonth(String yyyyMM)
    {
        if ((!StringUtil.isBlank(yyyyMM)) && (yyyyMM.length() != 6)) {}
        return yyyyMM.substring(4, 6);
    }

    public static boolean isDateType(String str)
    {
        String withYYYYMMDDHHSSRegax = "^\\d{4}([1-9]|(1[0-2])|(0[1-9]))([1-9]|([12]\\d)|(3[01])|(0[1-9]))(([0-1][0-9])|([2][0-3]))([0-5][0-9])([0-5][0-9])$";
        String withYYYYMMDDRegax = "^\\d{4}([1-9]|(1[0-2])|(0[1-9]))([1-9]|([12]\\d)|(3[01])|(0[1-9]))$";
        String withYYYYMMRegax = "^\\d{4}((1[0-2])|(0[1-9]))$";
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        if (str.length() == 6) {
            return str.matches(withYYYYMMRegax);
        }
        if (str.length() == 8) {
            return str.matches(withYYYYMMDDRegax);
        }
        if (str.length() == 14) {
            return str.matches(withYYYYMMDDHHSSRegax);
        }
        return false;
    }

    public static long getTimeDifference(Timestamp formatTime1, Timestamp formatTime2)
    {
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        long t1 = 0L;
        long t2 = 0L;
        try
        {
            t1 = timeformat.parse(getTimeStampNumberFormat(formatTime1)).getTime();
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
        }
        try
        {
            t2 = timeformat.parse(getTimeStampNumberFormat(formatTime2)).getTime();
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
        }
        long diff = t1 - t2;
        return diff / 86400000L;
    }

    public static int getTimeDifference(String beginDate, String endDate)
    {
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyyMM");

        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        try
        {
            cal1.setTime(timeformat.parse(endDate));
            cal2.setTime(timeformat.parse(beginDate));
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
        }
        int c = (cal1.get(1) - cal2.get(1)) * 12 + cal1.get(2) - cal2.get(2);
        return c;
    }

    public static int getDates()
    {
        Timestamp currTimestamp = getSysDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currTimestamp);
        return calendar.get(5);
    }

    public static int getDaysBetween(String beginDate, String endDate)
    {
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        long between_days = 0L;
        try
        {
            cal.setTime(timeformat.parse(beginDate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(timeformat.parse(endDate));
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / 86400000L;
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String getOffsetMonth(String date, int offsetMonth)
    {
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyyMM");

        Calendar cal = new GregorianCalendar();
        try
        {
            cal.setTime(timeformat.parse(date));
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
        }
        cal.add(2, offsetMonth);
        return timeformat.format(cal.getTime());
    }

    public static long getMinuteDif(Timestamp formatTime1, Timestamp formatTime2)
    {
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        long t1 = 0L;
        long t2 = 0L;
        try
        {
            t1 = timeformat.parse(getTimeStampNumberFormat(formatTime1)).getTime();
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
        }
        try
        {
            t2 = timeformat.parse(getTimeStampNumberFormat(formatTime2)).getTime();
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
        }
        long diff = t1 - t2;
        return diff / 60000L;
    }

    public static String getTimeStampNumberFormat(Timestamp formatTime)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", new Locale("zh", "cn"));
        return format.format(formatTime);
    }

    public static int getMillis()
    {
        Timestamp currTimestamp = getSysDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currTimestamp);
        return calendar.get(14);
    }

    public static Timestamp getBeforeSecond(Timestamp currentDate)
    {
        Calendar calender = Calendar.getInstance();
        calender.setTime(currentDate);
        calender.add(13, -1);
        return new Timestamp(calender.getTimeInMillis());
    }

    public static Timestamp getAfterSecond(Timestamp currentDate)
    {
        Calendar calender = Calendar.getInstance();
        calender.setTime(currentDate);
        calender.add(13, 1);
        return new Timestamp(calender.getTimeInMillis());
    }

    public static Timestamp getTimestamp(String time)
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        Timestamp ts = null;
        try
        {
            ts = new Timestamp(format.parse(time).getTime());
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
        }
        return ts;
    }

    public static String getCurYM()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        Calendar calender = Calendar.getInstance();
        return df.format(calender.getTime());
    }

    public static Timestamp getTimestamp(String time, String pattern)
            throws SystemException
    {
        if (StringUtil.isBlank(time)) {
            throw new SystemException("time is null");
        }
        if (StringUtil.isBlank(pattern)) {
            throw new SystemException("pattern is null");
        }
        DateFormat format = new SimpleDateFormat(pattern);
        format.setLenient(false);
        Timestamp ts = null;
        try
        {
            ts = new Timestamp(format.parse(time).getTime());
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
        }
        return ts;
    }

    public static Timestamp getTimestamp(long time)
    {
        Timestamp ts = new Timestamp(time);
        return ts;
    }

    public static int getTimeDifferenceMonth(Timestamp formatTime1, Timestamp formatTime2)
    {
        Calendar calendarTime1 = Calendar.getInstance();
        calendarTime1.setTime(formatTime1);
        int yearTime1 = calendarTime1.get(1);
        int monthTime1 = calendarTime1.get(2);
        int dayTime1 = calendarTime1.get(5);

        Calendar calendarTime2 = Calendar.getInstance();
        calendarTime2.setTime(formatTime2);
        int yearTime2 = calendarTime2.get(1);
        int monthTime2 = calendarTime2.get(2);
        int dayTime2 = calendarTime2.get(5);

        int y = yearTime2 - yearTime1;
        int m = monthTime2 - monthTime1;
        int d = dayTime2 - dayTime1;
        if (d >= 0) {
            return y * 12 + m + 1;
        }
        return y * 12 + m;
    }

    public static String trans2CnTime(Timestamp time)
    {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return df.format(time);
    }

    public static String trans2CnDate(Timestamp time)
    {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(time);
    }

    public static Timestamp getTimeNextDay(Timestamp date)
    {
        long time = date.getTime();
        time += 86400000L;
        return new Timestamp(time);
    }

    public static Timestamp getTimeBeforeDay(Timestamp date)
    {
        long time = date.getTime();
        time -= 86400000L;
        return new Timestamp(time);
    }

    public static Timestamp getBeforeMonth(Timestamp currentDate)
    {
        Calendar calender = Calendar.getInstance();
        calender.setTime(currentDate);
        calender.add(2, -1);
        return new Timestamp(calender.getTimeInMillis());
    }

    public static Timestamp getTimeLastMonthLastSec(Timestamp sysDate)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sysDate);
        cal.add(2, -1);
        int maxDay = cal.getActualMaximum(5);
        cal.set(cal.get(1), cal.get(2), maxDay, 23, 59, 59);
        return new Timestamp(cal.getTimeInMillis());
    }

    public static String getInterval(Timestamp nowdate)
    {
        String interval = null;
        long time = getSysDate().getTime() - nowdate.getTime();
        if ((time / 1000L < 10L) && (time / 1000L >= 0L))
        {
            interval = "刚刚";
        }
        else if ((time / 3600000L < 24L) && (time / 3600000L > 0L))
        {
            int h = (int)(time / 3600000L);
            interval = h + "小时前";
        }
        else if ((time / 60000L < 60L) && (time / 60000L > 0L))
        {
            int m = (int)(time % 3600000L / 60000L);
            interval = m + "分钟前";
        }
        else if ((time / 1000L < 60L) && (time / 1000L > 0L))
        {
            int se = (int)(time % 60000L / 1000L);
            interval = se + "秒前";
        }
        else
        {
            long days = getTimeDifference(getSysDate(), nowdate);
            interval = days + "天前";
        }
        return interval;
    }

    public static int getMonth(Timestamp time)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int month = cal.get(2);
        return month + 1;
    }

    public static int getYear(Timestamp time)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int year = cal.get(1);
        return year;
    }

    public static int getDay(Timestamp time)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int day = cal.get(5);
        return day;
    }

    public static String getCurrentDate()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = df.format(new Date());
        return s;
    }

    public static String dateToIso(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(date);
    }

    public static long DiffDate(String dateStr1, String dateStr2)
    {
        long interval = 0L;
        try
        {
            Date date1 = str2Date(dateStr1);
            Date date2 = str2Date(dateStr2);
            interval = (date2.getTime() - date1.getTime()) / 60000L;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return interval;
    }

    public static Timestamp strDateToTimestamp(String date)
    {
        if ((date != null) && (!"".equals(date)))
        {
            String replaceAll = date.replaceAll("/", "-");
            String[] split = date.split("-");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatter2 = null;
            if (split.length > 2) {
                formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            } else {
                formatter2 = new SimpleDateFormat("yyyy-MM");
            }
            Timestamp timestamp = null;
            try
            {
                Date parse = formatter2.parse(replaceAll);
                String format = formatter.format(parse);
                timestamp = Timestamp.valueOf(format);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return timestamp;
        }
        return null;
    }

    public static List<Timestamp> getTimeList(String date, String startTime, String endTime)
            throws ParseException
    {
        Timestamp start = StrToTimestamp(date + " " + startTime + ":00");
        Timestamp end = StrToTimestamp(date + " " + endTime + ":00");
        List<Timestamp> list = new ArrayList();
        for (int i = 0; i < 48; i++)
        {
            int minutes = start.getMinutes();
            int minTmp = minutes + 30;
            start.setMinutes(minTmp);
            Timestamp timestampT = new Timestamp(start.getTime());
            if (start.getTime() == end.getTime())
            {
                list.add(timestampT);
                break;
            }
            list.add(timestampT);
        }
        return list;
    }
}
