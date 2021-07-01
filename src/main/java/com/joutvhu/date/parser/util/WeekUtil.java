package com.joutvhu.date.parser.util;

import lombok.experimental.UtilityClass;

import java.time.temporal.WeekFields;

@UtilityClass
public class WeekUtil {
    /**
     * @param dayOfWeek      The day of week need to convert.
     * @param firstDayOfWeek The first day-of-week (Monday - 1), (Sunday - 7)
     * @return day of week (startWeekOn)
     */
    public int convertWeekDay(int dayOfWeek, int firstDayOfWeek) {
        return (dayOfWeek + 7 - firstDayOfWeek) % 7 + 1;
    }

    public int getWeekOfYearByDayOfYear(WeekFields weekFields, int dayOfYear, int dayOfWeek) {
        return getWeekOfYearByDayOfYear(
                weekFields.getFirstDayOfWeek().getValue(),
                weekFields.getMinimalDaysInFirstWeek(),
                dayOfYear,
                dayOfWeek
        );
    }

    /**
     * Get week of year by day of year and day of week.
     *
     * @param firstDayOfWeek         The first day-of-week varies by culture.
     * @param minimalDaysInFirstWeek The number of days considered to define the first week of a month or year varies by culture (Week 0).
     * @param dayOfYear              The day of year.
     * @param dayOfWeek              The weekday of current day (1 is Monday, 7 is Sunday).
     * @return The week of year.
     */
    public int getWeekOfYearByDayOfYear(int firstDayOfWeek, int minimalDaysInFirstWeek, int dayOfYear, int dayOfWeek) {
        if (firstDayOfWeek != 1)
            dayOfWeek = convertWeekDay(dayOfWeek, firstDayOfWeek);
        dayOfYear = dayOfYear - dayOfWeek;
        if (dayOfYear <= 0)
            return 7 + dayOfYear >= minimalDaysInFirstWeek ? 1 : 0;
        return dayOfYear / 7 + (dayOfYear % 7 >= minimalDaysInFirstWeek ? 2 : 1);
    }

    public int getWeekOfMonthByDayOfMonth(WeekFields weekFields, int dayOfMonth, int dayOfWeek) {
        return getWeekOfMonthByDayOfMonth(
                weekFields.getFirstDayOfWeek().getValue(),
                dayOfMonth,
                dayOfWeek
        );
    }

    /**
     * Get week of month by day of month and day of week.
     *
     * @param firstDayOfWeek The first day-of-week varies by culture.
     * @param dayOfMonth     The day of month
     * @param dayOfWeek      The weekday of current day (1 is Monday, 7 is Sunday).
     * @return The week of month.
     */
    public int getWeekOfMonthByDayOfMonth(int firstDayOfWeek, int dayOfMonth, int dayOfWeek) {
        if (firstDayOfWeek != 1)
            dayOfWeek = convertWeekDay(dayOfWeek, firstDayOfWeek);
        dayOfMonth = dayOfMonth - dayOfWeek;
        if (dayOfMonth <= 0)
            return 1;
        return dayOfMonth / 7 + (dayOfMonth % 7 > 0 ? 2 : 1);
    }

    public int getDayOfYearByWeekOfYear(WeekFields weekFields, int weekOfYear, int dayOfWeek, int firstDayOfYear) {
        return getDayOfYearByWeekOfYear(
                weekFields.getFirstDayOfWeek().getValue(),
                weekFields.getMinimalDaysInFirstWeek(),
                weekOfYear,
                dayOfWeek,
                firstDayOfYear
        );
    }

    /**
     * Get day of year by week of year and day of week
     *
     * @param firstDayOfWeek         The first day-of-week varies by culture.
     * @param minimalDaysInFirstWeek The number of days considered to define the first week of a month or year varies by culture (Week 0).
     * @param weekOfYear             The week of the year.
     * @param dayOfWeek              The weekday of current day (1 is Monday, 7 is Sunday).
     * @param firstDayOfYear         The weekday of the first day of the year (1 is Monday, 7 is Sunday).
     * @return The day of the year.
     */
    public int getDayOfYearByWeekOfYear(int firstDayOfWeek, int minimalDaysInFirstWeek, int weekOfYear, int dayOfWeek, int firstDayOfYear) {
        if (firstDayOfWeek != 1) {
            dayOfWeek = convertWeekDay(dayOfWeek, firstDayOfWeek);
            firstDayOfYear = convertWeekDay(firstDayOfYear, firstDayOfWeek);
        }
        int dayOfYear = (weekOfYear * 7) - (7 - dayOfWeek) - (firstDayOfYear - 1);
        if (weekOfYear == 0 || (7 - firstDayOfYear) < (minimalDaysInFirstWeek - 1)) {
            // There is week 0
            dayOfYear += 7;
        }
        return dayOfYear;
    }

    public int getDayOfMonthByWeekOfMonth(WeekFields weekFields, int weekOfMonth, int dayOfWeek, int firstDayOfMonth) {
        return getDayOfMonthByWeekOfMonth(
                weekFields.getFirstDayOfWeek().getValue(),
                weekOfMonth,
                dayOfWeek,
                firstDayOfMonth
        );
    }

    /**
     * Get day of month by week of month and day of week.
     *
     * @param firstDayOfWeek  The first day-of-week varies by culture.
     * @param weekOfMonth     The week of the month.
     * @param dayOfWeek       The weekday of current day (1 is Monday, 7 is Sunday).
     * @param firstDayOfMonth The weekday of the first day of the month (1 is Monday, 7 is Sunday).
     * @return The day of the month.
     */
    public int getDayOfMonthByWeekOfMonth(int firstDayOfWeek, int weekOfMonth, int dayOfWeek, int firstDayOfMonth) {
        if (firstDayOfWeek != 1) {
            dayOfWeek = convertWeekDay(dayOfWeek, firstDayOfWeek);
            firstDayOfMonth = convertWeekDay(firstDayOfMonth, firstDayOfWeek);
        }
        return (weekOfMonth * 7) - (7 - dayOfWeek) - (firstDayOfMonth - 1);
    }

    public int getDayOfMonthByWeekdayInMonth(WeekFields weekFields, int weekdayInMonth, int dayOfWeek, int firstDayOfMonth) {
        return getDayOfMonthByWeekdayInMonth(
                weekFields.getFirstDayOfWeek().getValue(),
                weekdayInMonth,
                dayOfWeek,
                firstDayOfMonth
        );
    }

    /**
     * Get day of month by weekday in month and day of week.
     *
     * @param firstDayOfWeek  The first day-of-week varies by culture.
     * @param weekdayInMonth  The weekday in the month.
     * @param dayOfWeek       The weekday of current day (1 is Monday, 7 is Sunday).
     * @param firstDayOfMonth The weekday of the first day of the month (1 is Monday, 7 is Sunday).
     * @return The day of the month.
     */
    public int getDayOfMonthByWeekdayInMonth(int firstDayOfWeek, int weekdayInMonth, int dayOfWeek, int firstDayOfMonth) {
        if (firstDayOfWeek != 1) {
            dayOfWeek = convertWeekDay(dayOfWeek, firstDayOfWeek);
            firstDayOfMonth = convertWeekDay(firstDayOfMonth, firstDayOfWeek);
        }
        return (weekdayInMonth * 7) - firstDayOfMonth + dayOfWeek + 1 - (dayOfWeek < firstDayOfMonth ? 0 : 7);
    }
}
