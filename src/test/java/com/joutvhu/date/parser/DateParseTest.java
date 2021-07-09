package com.joutvhu.date.parser;

import com.joutvhu.date.parser.exception.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DateParseTest {
    @Test
    void parse_utilDate0() {
        Date result = DateParser.formatter()
                .parse(Date.class, "2021/6/7", "yyyy/M/d");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-07 00:00:00.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    void parse_utilDate1() {
        Date result = DateParser.formatter()
                .parse(Date.class, "2021/06/27", "yyyy/MM/dd");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 00:00:00.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    void parse_utilDate2() {
        Date result = DateParser.formatter()
                .parse(Date.class, "2021/06/27 21", "yyyy/MM/dd HH");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 21:00:00.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    void parse_utilDate3() {
        Date result = DateParser.formatter()
                .parse(Date.class, "2021/06/27 21:52", "yyyy/MM/dd HH:mm");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 21:52:00.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    void parse_utilDate4() {
        Date result = DateParser.formatter()
                .parse(Date.class, "2021/06/27 21:52:25", "yyyy/MM/dd HH:mm:ss");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 21:52:25.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    void parse_utilDate5() {
        Date result = DateParser.formatter()
                .parse(Date.class, "2021/06/27 21:52:25.408", "yyyy/MM/dd HH:mm:ss.SSS");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 21:52:25.408", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    void parse_Calendar0() {
        Calendar result = DateParser.formatter()
                .parse(Calendar.class, "2021/06/27 21:52:25.408", "yyyy/MM/dd HH:mm:ss.SSS");
        Assertions.assertNotNull(result);
    }

    @Test
    void parse_LocalDateTime0() {
        LocalDateTime result = DateParser.formatter()
                .parse(LocalDateTime.class, "2021/06/27 21:52:25.408", "yyyy/MM/dd HH:mm:ss.SSS");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27T21:52:25.408", result.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @Test
    void parse_LocalDateTime1() {
        LocalDateTime result = DateParser.formatter()
                .parse(LocalDateTime.class, "2021/06/27 41045272000000", "yyyy/MM/dd N");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27T11:24:05.272", result.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @Test
    void parse_Instant0() {
        Instant result = DateParser.parse(Instant.class, "2021/06/27 21:52:25.403841585", "yyyy/MM/dd HH:mm:ss.SSSSSSSSS");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(403841585, result.getNano());
    }

    @Test
    void parse_Instant1() {
        Instant result = DateParser.parse(Instant.class, "2021-06-28T02:22:48.780101Z", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-28T02:22:48.780101Z", result.toString());
    }

    @Test
    void parse_Long1() {
        Long result = DateParser.parse(Long.class, "2021-06-28T02:22:48.780Z", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Assertions.assertEquals(1624846968780L, result);
    }

    @Test
    void parse_Year0() {
        Year result = DateParser.parse(Year.class, "2021", "yyyy");
        Assertions.assertEquals(2021, result.getValue());
    }

    @Test
    void parse_Year1() {
        Year result = DateParser.parse(Year.class, "2021", "yy");
        Assertions.assertEquals(2021, result.getValue());
    }

    @Test
    void parse_Month0() {
        Month result = DateParser.parse(Month.class, "08", "MM");
        Assertions.assertEquals(8, result.getValue());
    }

    @Test
    void parse_Month1() {
        Month result = DateParser.parse(Month.class, "9", "MM");
        Assertions.assertEquals(9, result.getValue());
    }

    @Test
    void parse_YearMonth0() {
        YearMonth result = DateParser.parse(YearMonth.class, "11/2022", "M/yyyy");
        Assertions.assertEquals(11, result.getMonthValue());
        Assertions.assertEquals(2022, result.getYear());
    }

    @Test
    void parse_MonthDay0() {
        MonthDay result = DateParser.parse(MonthDay.class, "1/22", "M/d");
        Assertions.assertEquals(1, result.getMonthValue());
        Assertions.assertEquals(22, result.getDayOfMonth());
    }

    @Test
    void parse_DayOfWeek0() {
        DayOfWeek result = DateParser.parse(DayOfWeek.class, "Sun", "E");
        Assertions.assertEquals(7, result.getValue());
    }

    @Test
    void parse_DayOfWeek1() {
        DayOfWeek result = DateParser.parse(DayOfWeek.class, "2021/06/28", "yyyy/MM/d");
        Assertions.assertEquals(1, result.getValue());
    }

    @Test
    void parse_DayOfWeek2() {
        DayOfWeek result = DateParser.parse(DayOfWeek.class, "Wednesday", "EEEE");
        Assertions.assertEquals(3, result.getValue());
    }

    @Test
    void parse_DayOfWeek4() {
        DayOfWeek result = DateParser.parse(DayOfWeek.class, "2", "e");
        Assertions.assertEquals(2, result.getValue());
    }

    @Test
    void parse_DayOfWeek5() {
        DayOfWeek result = DateParser.parse(DayOfWeek.class, "Sun", "EEE");
        Assertions.assertEquals(7, result.getValue());
    }

    @Test
    void parse_DayOfWeek6() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            DateParser.parse(DayOfWeek.class, "2021/06", "yyyy/MM");
        });
    }

    @Test
    void parse_DayOfWeek7() {
        DayOfWeek result = DateParser.parse(DayOfWeek.class, "tue", "EEE");
        Assertions.assertEquals(2, result.getValue());
    }

    @Test
    void parse_DayOfWeek8() {
        DayOfWeek result = DateParser.parse(DayOfWeek.class, "SAT", "EEE");
        Assertions.assertEquals(6, result.getValue());
    }

    @Test
    void parse_ZoneOffset0() {
        ZoneOffset result = DateParser.parse(ZoneOffset.class, "SE Asia Standard Time", "Z");
        Assertions.assertEquals("+07:00", result.toString());
    }

    @Test
    void parse_TimeZone0() {
        TimeZone result = DateParser.parse(TimeZone.class, "SE Asia Standard Time", "Z");
        Assertions.assertEquals("GMT+07:00", result.getID());
    }

    @Test
    void parse_OffsetTime0() {
        OffsetTime result = DateParser.parse(OffsetTime.class, "16:12:53.221 SE Asia Standard Time", "HH:mm:ss.SSS Z");
        Assertions.assertEquals("16:12:53.221+07:00", result.toString());
    }

    @Test
    void parse_OffsetDateTime0() {
        OffsetDateTime result = DateParser.parse(OffsetDateTime.class, "2021/06/28 12:12:53.221 CAST", "yyyy/MM/dd HH:mm:ss.SSS Z");
        Assertions.assertEquals("2021-06-28T12:12:53.221-06:00", result.toString());
    }

    @Test
    void parse_ZonedDateTime0() {
        ZonedDateTime result = DateParser.parse(ZonedDateTime.class, "5/1/2020 1:20:42.234 PST", "M/d/yyyy H:m:s.SSS Z");
        Assertions.assertEquals("2020-05-01T01:20:42.234-07:00[America/Los_Angeles]", result.toString());
    }

    @Test
    void parse_SqlDate0() {
        java.sql.Date result = DateParser.parse(java.sql.Date.class, "5/1/2020", "d/M/yyyy");
        Assertions.assertEquals("2020-01-05", result.toString());
    }

    @Test
    void parse_SqlTime0() {
        java.sql.Time result = DateParser.parse(java.sql.Time.class, "1:20:42 pm", "h:m:s a");
        Assertions.assertEquals("13:20:42", result.toString());
    }

    @Test
    void parse_SqlTimestamp0() {
        java.sql.Timestamp result = DateParser.parse(java.sql.Timestamp.class, "5/1/2020 1:20:42.234 pm", "d/M/yyyy h:m:s.SSS a");
        Assertions.assertEquals("2020-01-05 13:20:42.234", result.toString());
    }

    @Test
    void parse_Fail0() {
        Assertions.assertThrows(ParseException.class, () -> {
            DateParser.parse(Instant.class, "5/1/2020 1:20:42.234 cm", "d/M/yyyy h:m:s.SSS a");
        });
    }

    private String format(Date date, String format) {
        SimpleDateFormat dt = new SimpleDateFormat(format);
        return dt.format(date);
    }
}
