package com.joutvhu.date.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DateFormatTest {
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

    @Test
    void format_LocalDateTime1() {
        LocalDateTime date = LocalDateTime.now();
        String result = DateParser.format(date, "MM dd yyyy N");

        String expected = DateTimeFormatter
                .ofPattern("MM dd yyyy N")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(date);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_LocalDateTime2() {
        LocalDateTime date = LocalDateTime.now();
        String result = DateParser.format(date, "dd n");

        String expected = DateTimeFormatter
                .ofPattern("dd n")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(date);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_SqlDate0() {
        LocalDate localDate = LocalDate.now();
        java.sql.Date date = java.sql.Date.valueOf(localDate);
        String result = DateParser.format(date, "MMM dd yyyy");

        String expected = DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(localDate);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_SqlTime0() {
        LocalTime localTime = LocalTime.now();
        Time time = Time.valueOf(localTime);
        String result = DateParser.format(time, "hh:mm:ss a");

        String expected = DateTimeFormatter
                .ofPattern("hh:mm:ss a")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(localTime);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_SqlTimestamp0() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp date = Timestamp.valueOf(localDateTime);
        String result = DateParser.format(date, "MMM dd yyyy HH:mm:ss.SSS");

        String expected = DateTimeFormatter
                .ofPattern("MMM dd yyyy HH:mm:ss.SSS")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(localDateTime);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_Long0() {
        String result = DateParser.formatter()
                .withZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"))
                .format(1624846968780L, "yyyy-MM-dd HH:mm:ss.SSS");
        Assertions.assertEquals("2021-06-28 09:22:48.780", result);
    }

    @Test
    void format_OffsetDateTime0() {
        OffsetDateTime date = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);
        String result = DateParser.format(date, "MMM dd yyyy HH:mm:ss.SSS");

        String expected = DateTimeFormatter
                .ofPattern("MMM dd yyyy HH:mm:ss.SSS")
                .withLocale(Locale.getDefault())
                .withZone(ZoneOffset.UTC)
                .format(date);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_OffsetTime0() {
        OffsetTime time = OffsetTime.of(LocalTime.now(), ZoneOffset.UTC);
        String result = DateParser.format(time, "HH:mm:ss.SSS");

        String expected = DateTimeFormatter
                .ofPattern("HH:mm:ss.SSS")
                .withLocale(Locale.getDefault())
                .withZone(ZoneOffset.UTC)
                .format(time);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_ZonedDateTime0() {
        ZonedDateTime date = ZonedDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);
        String result = DateParser.format(date, "MMM dd yyyy HH:mm:ss.SSS");

        String expected = DateTimeFormatter
                .ofPattern("MMM dd yyyy HH:mm:ss.SSS")
                .withLocale(Locale.getDefault())
                .withZone(ZoneOffset.UTC)
                .format(date);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_ZonedDateTime1() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, 7, 9, 14, 1, 3, 4100000);
        ZonedDateTime date = ZonedDateTime.of(localDateTime, ZoneOffset.UTC);
        String result = DateParser.format(date, "MMM dd yyyy HH:mm:ss.SSS");

        String expected = DateTimeFormatter
                .ofPattern("MMM dd yyyy HH:mm:ss.SSS")
                .withLocale(Locale.getDefault())
                .withZone(ZoneOffset.UTC)
                .format(date);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void format_ZonedOffset0() {
        String result = DateParser.format(ZoneOffset.UTC, "X");
        Assertions.assertEquals("Z", result);
    }

    @Test
    void format_TimeZone0() {
        String result = DateParser.format(TimeZone.getTimeZone("GMT"), "x");
        Assertions.assertEquals("+00", result);
    }
}
