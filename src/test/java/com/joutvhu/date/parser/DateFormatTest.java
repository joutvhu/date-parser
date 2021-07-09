package com.joutvhu.date.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DateFormatTest {
    @Test
    void format_utilDate0() {
        Date date = new Date();
        String result = DateParser.format(date, "MMM dd, yyyy hh:mm:ss a");

        String expected = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a").format(date);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_Instant0() {
        Instant date = Instant.now();
        String result = DateParser.format(date, "MMM dd, yyyy HH:mm:ss VV");

        String expected = DateTimeFormatter
                .ofPattern("MMM dd, yyyy HH:mm:ss VV")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(date);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_Calendar0() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"), Locale.ROOT);
        calendar.setTimeInMillis(1625801809664L);
        String result = DateParser.format(calendar, "MMMM dd, yyyy HH:mm:ss O");

        Assertions.assertEquals("July 09, 2021 10:36:49 GMT+7", result);
    }

    @Test
    void format_DayOfWeek0() {
        DayOfWeek dayOfWeek = DayOfWeek.WEDNESDAY;
        String result = DateParser.format(dayOfWeek, "EEE");

        Assertions.assertEquals("Wed", result);
    }

    @Test
    void format_Month0() {
        Month month = Month.FEBRUARY;
        String result = DateParser.format(month, "Mo");

        Assertions.assertEquals("2nd", result);
    }

    @Test
    void format_MonthDay0() {
        MonthDay monthDay = MonthDay.of(Month.MARCH, 18);
        String result = DateParser.format(monthDay, "MMo, ddo");

        Assertions.assertEquals("02rd, 18th", result);
    }

    @Test
    void format_YearMonth0() {
        YearMonth yearMonth = YearMonth.of(2021, Month.DECEMBER);
        String result = DateParser.format(yearMonth, "Mo yyyy");

        Assertions.assertEquals("12th 2021", result);
    }

    @Test
    void format_Year0() {
        Year year = Year.of(2021);
        String result = DateParser.format(year, "CC yy");

        Assertions.assertEquals("21 21", result);
    }
}
