package tech.zg.webatis.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
public class DateUtils {

    public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";
    public static final String YM = "yyyyMM";
    public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
    public static final String NORMAL_DATE_FORMAT_MS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_MILL_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
    public static final String DATE_ALL = "yyyyMMddHHmmssS";

    /**
     * java.util.Date时间转换为格式字符串
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param date   需要转换的日期
     * @param format 日期格式，如yyyy-MM-dd HH:mm:ss，可参见DateUtils,默认为yyyy-MM-dd HH:mm:ss
     * @return 转换成功返回对应的日期字符串，转换失败返回null
     */
    public static String formatDate(Date date, String format) {
        if (date == null){
            return null;
        }
        String formatDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format == null ? DATETIME_FORMAT : format);
            formatDate = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatDate;
    }

    public static String formatDate(Timestamp timestamp, String format) {
        if (timestamp == null){
            return null;
        }
        String formatDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format == null ? DATETIME_FORMAT : format);
            formatDate = dateFormat.format(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatDate;
    }

    /**
     * 获取当前时间，格式为yyyy-MM-dd HH:mm:ss
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @return 当前时间，格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTimeString() {
        return formatDate(getCurrentDate(), DATETIME_FORMAT);
    }

    /**
     * 获取当前时间
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @return 当前时间，java.util.Date
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 将时间置为23时59分钟59秒
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param date
     * @return
     */
    public static Date setDayCutOffTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 将Timestamp时间转换为指定格式日期
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param timestamp 需要转换的日期
     * @return 转换成功返回日期字符串格式为yyyy-MM-dd HH:mm:ss，转换失败返回null
     */
    public static String timestampToDateTimeString(Timestamp timestamp) {
        if (timestamp == null){
            return null;
        }
        return timestampToFormatDateString(timestamp, DATETIME_FORMAT);
    }

    /**
     * 获取10位时间戳字符串
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @return 时间戳字符串
     */
    public static String getTimestampStr() {

        Date date = new Date();
        long time = date.getTime();
        String dateline = time + "";
        dateline = dateline.substring(0, 10);
        return dateline;
    }

    /**
     * 获取10位时间戳数值
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @return 时间戳数值
     */
    public static int getTimestamp() {

        return Integer.valueOf(getTimestampStr());
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式日期String 转换为Timestamp
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param datetime 需要转换的日期字符串,格式为yyyy-MM-dd HH:mm:ss
     * @return 转换成功返回Timestamp，转换失败返回null
     */
    public static Timestamp datetimeStringToTimestamp(String datetime) {
        if (datetime == null){
            return null;
        }
        Timestamp result = null;
        try {
            result = Timestamp.valueOf(datetime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将Timestamp时间转换为指定格式日期
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param timestamp 需要转换的日期
     * @param format    日期格式，如yyyy-MM-dd HH:mm:ss，可参见DateUtils,默认为yyyy-MM-dd HH:mm:ss
     * @return 转换成功返回对应的日期字符串，转换失败返回null
     */
    public static String timestampToFormatDateString(Timestamp timestamp, String format) {
        if (timestamp == null){
            return null;
        }
        // dateFormat 若未输入则默认为yyyy-MM-dd HH:mm:ss
        SimpleDateFormat dateFormat = new SimpleDateFormat(format == null ? DATETIME_FORMAT : format);
        String normalDateString = null;
        try {
            normalDateString = dateFormat.format(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return normalDateString;
    }

    /**
     * 根据时间字符串格式化
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param dateStr 字符串时间
     * @param format 格式化格式
     * @return 日期
     */
    public static Date stringToDate(String dateStr, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 对制定日期，进行增减天数计算，并返回计算后的新日期，正数减，负数减。
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param date 日期
     * @param amount 加减天数
     * @return 运算后的天数
     */
    public static Date dayAdd(Date date, int amount) {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.DAY_OF_MONTH, amount);
        return gc.getTime();
    }

    /**
     * 判断日期是否在范围内，包含相等的日期
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param date 要判断的日期
     * @param start 判断区间范围开始
     * @param end 判断区间范围结束
     * @return 在区间内返回true
     */
    public static boolean isBetween(final Date date, final Date start, final Date end) {
        if (date == null || start == null || end == null || start.after(end)) {
            throw new IllegalArgumentException("some date parameters is null or dateBein after dateEnd");
        }
        return !date.before(start) && !date.after(end);
    }
}
