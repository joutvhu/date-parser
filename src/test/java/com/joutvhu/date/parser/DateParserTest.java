package com.joutvhu.date.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.text.SimpleDateFormat;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DateParserTest {
    @Test
    public void testParse_utilDate0() {
        Date result = DateParser.parse(Date.class,"2021/6/7", "yyyy/M/d");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-07 00:00:00.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void testParse_utilDate1() {
        Date result = DateParser.parse(Date.class,"2021/06/27", "yyyy/MM/dd");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 00:00:00.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void testParse_utilDate2() {
        Date result = DateParser.parse(Date.class,"2021/06/27 21", "yyyy/MM/dd HH");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 21:00:00.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void testParse_utilDate3() {
        Date result = DateParser.parse(Date.class,"2021/06/27 21:52", "yyyy/MM/dd HH:mm");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 21:52:00.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void testParse_utilDate4() {
        Date result = DateParser.parse(Date.class,"2021/06/27 21:52:25", "yyyy/MM/dd HH:mm:ss");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 21:52:25.000", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Test
    public void testParse_utilDate5() {
        Date result = DateParser.parse(Date.class,"2021/06/27 21:52:25.408", "yyyy/MM/dd HH:mm:ss.SSS");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2021-06-27 21:52:25.408", format(result, "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    private String format(Date date, String format) {
        SimpleDateFormat dt = new SimpleDateFormat(format);
        return dt.format(date);
    }
}
