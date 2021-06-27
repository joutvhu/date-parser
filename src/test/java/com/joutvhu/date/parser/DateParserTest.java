package com.joutvhu.date.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DateParserTest {
    @Test
    public void parse_utilDate0() {
        Date result = DateParser.instance()
                .parse(Date.class, "2021/6/7", "yyyy/M/d");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-07 00:00:00.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void parse_utilDate1() {
        Date result = DateParser.instance()
                .parse(Date.class, "2021/06/27", "yyyy/MM/dd");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 00:00:00.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void parse_utilDate2() {
        Date result = DateParser.instance()
                .parse(Date.class, "2021/06/27 21", "yyyy/MM/dd HH");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 21:00:00.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void parse_utilDate3() {
        Date result = DateParser.instance()
                .parse(Date.class, "2021/06/27 21:52", "yyyy/MM/dd HH:mm");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 21:52:00.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void parse_utilDate4() {
        Date result = DateParser.instance()
                .parse(Date.class, "2021/06/27 21:52:25", "yyyy/MM/dd HH:mm:ss");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 21:52:25.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void parse_utilDate5() {
        Date result = DateParser.instance()
                .parse(Date.class, "2021/06/27 21:52:25.408", "yyyy/MM/dd HH:mm:ss.SSS");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 21:52:25.408", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void parse_Calendar0() {
        Calendar result = DateParser.instance()
                .parse(Calendar.class, "2021/06/27 21:52:25.408", "yyyy/MM/dd HH:mm:ss.SSS");
        Assertions.assertNotNull(result);
    }

    @Test
    public void parse_LocalDateTime0() {
        LocalDateTime result = DateParser.instance()
                .parse(LocalDateTime.class, "2021/06/27 21:52:25.408", "yyyy/MM/dd HH:mm:ss.SSS");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27T21:52:25.408", result.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @Test
    public void parse_Instant0() {
        Instant result = DateParser.quickParse(Instant.class, "2021/06/27 21:52:25.403841585", "yyyy/MM/dd HH:mm:ss.SSSSSSSSS");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(403841585, result.getNano());
    }

    private String format(Date date, String format) {
        SimpleDateFormat dt = new SimpleDateFormat(format);
        return dt.format(date);
    }
}
