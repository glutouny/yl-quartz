package com.yl.quartz.demo.infra.util;

import com.yl.quartz.demo.infra.constants.BaseConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 时间工具类
 *
 * @author li.yang01@hand-china.com 2021/1/15 11:29 上午
 */
public class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private static ThreadLocal<DateFormat> dtf = ThreadLocal.withInitial(() -> new SimpleDateFormat(BaseConstants.Pattern.DATETIME));

    private DateUtils() {
    }

    private static final ThreadLocal<DateFormat> SDF_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat(BaseConstants.Pattern.DATETIME));

    /**
     * 取得当前时间上个月的日历
     *
     * @return
     */
    public static Calendar getLastMonthCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return calendar;
    }

    /**
     * 取得指定月份的起始时间
     *
     * @param cc
     * @return
     */
    public static Date getStartTimeOfCalendar(Calendar cc) {
        int firstDay = 1;
        int hour = 0;
        int minute = 0;
        int second = 0;
        cc.set(cc.get(Calendar.YEAR), cc.get(Calendar.MONTH), firstDay, hour, minute, second);
        return cc.getTime();
    }

    /**
     * 取得指定月份的结束时间
     *
     * @param cc
     * @return
     */
    public static Date getEndTimeOfCalendar(Calendar cc) {
        // 指定月份最后一天
        int lastDay = cc.getActualMaximum(Calendar.DAY_OF_MONTH);
        int hour = 23;
        int minute = 59;
        int second = 59;
        cc.set(cc.get(Calendar.YEAR), cc.get(Calendar.MONTH), lastDay, hour, minute, second);
        return cc.getTime();
    }

    /**
     * @param date 时间
     * @return 时间字符串
     */
    public static String convertToString(final Date date) {
        if (date != null) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(BaseConstants.Pattern.DATETIME);
            try {
                return simpleDateFormat.format(date);
            } catch (final Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * date型时间转换成String yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String dateTime2String(final Date date) {
        if (date == null) {
            return null;
        }
        return dtf.get().format(date);
    }

    /**
     * 解析指定的字符串到日期格式(T->java.util.Date)["yyyy-MM-dd HH:mm:ss"] 线程安全
     */
    public static Date parseToDate(final String source) {
        Date date = null;
        try {
            date = SDF_FORMAT.get().parse(source);
        } catch (final ParseException e) {
            LOGGER.error("parseToDate happened error:", e);
        }
        return date;
    }
}
