package com.joutvhu.date.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.text.SimpleDateFormat;
import java.time.*;
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

        Assertions.assertEquals("03rd, 18th", result);
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
        String result = DateParser.format(year, "G CC yy");

        Assertions.assertEquals("AD 21 21", result);
    }

    @Test
    void format_Year1() {
        Year year = Year.of(2020);
        String result = DateParser.format(year, "CCo yy");

        Assertions.assertEquals("21st 20", result);
    }

    @Test
    void format_LocalDate0() {
        LocalDate date = LocalDate.now();
        String result = DateParser.format(date, "MMM dd yyyy");

        String expected = DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(date);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_LocalTime0() {
        LocalTime date = LocalTime.now();
        String result = DateParser.format(date, "hh:mm:ss.SSS a");

        String expected = DateTimeFormatter
                .ofPattern("hh:mm:ss.SSS a")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(date);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_LocalDateTime0() {
        LocalDateTime date = LocalDateTime.now();
        String result = DateParser.format(date, "MMM dd yyyy HH:mm:ss.SSS");

        String expected = DateTimeFormatter
                .ofPattern("MMM dd yyyy HH:mm:ss.SSS")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(date);

        Assertions.assertEquals(expected, result);
    }
}
