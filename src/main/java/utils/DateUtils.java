package utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * @author zhangyunlong
 */
public class DateUtils {
    /**
     * 将 date 向后偏移一天，判断是否换月。
     *
     * @param date 日期
     * @return 最后一天返回true，否则返回false
     */
    public static boolean isLastDayOfMonth(final LocalDate date) {
        return date.getMonth() != date.plusDays(1).getMonth();
    }

    /**
     * 判断日期是否为周五
     *
     * @param date 日期
     * @return 如果是周五返回true，否则返回false
     */
    public static boolean isFriday(final LocalDate date) {
        return DayOfWeek.FRIDAY.equals(date.getDayOfWeek());
    }

    /**
     * 判断某个日期是否在指定的范围，[startDate, endDate]
     *
     * @param theDate 日期
     * @param startDate 起始日期
     * @param endDate 终止日期
     * @return 是否在指定的日期范围之间
     */
    public static boolean isBetween(LocalDate theDate, LocalDate startDate, LocalDate endDate) {
        return theDate.compareTo(startDate) >= 0 && theDate.compareTo(endDate) <= 0;
    }

    /**
     * 判断指定的日期是否在双周
     *
     * @param date 日期
     * @return 是否在双周
     */
    public static boolean isBiweekly(LocalDate date) {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, date.getYear());
        instance.set(Calendar.MONTH, date.getMonthValue() - 1);
        instance.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
        return instance.get(Calendar.WEEK_OF_YEAR) % 2 == 0;
    }
}
