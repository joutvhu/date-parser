package com.joutvhu.date.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.text.SimpleDateFormat;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DateFormatTest {
    @Test
    void format_utilDate0() {
        Date date = new Date();
        String result = DateParser.quickFormat(date, "MMM dd, yyyy hh:mm:ss a");

        String expected = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a").format(date);

        Assertions.assertEquals(expected, result);
    }
}
