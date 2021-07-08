package com.joutvhu.date.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

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
}
