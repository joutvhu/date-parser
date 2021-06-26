package com.joutvhu.date.parser.support;

import com.joutvhu.date.parser.domain.DateBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DateFormatTest {
    @Test
    public void parse_Case0() {
        DateBuilder builder = new DateFormat("yyyyMd hh:mm:ss.SSS a Z").parse("20211123 02:41:32.651 pm America/Los_Angeles");
        Assertions.assertNotNull(builder);
    }

    @Test
    public void parse_DayOfYear0() {
        DateBuilder builder = new DateFormat("yyyyDDD").parse("2021068");
        Assertions.assertNotNull(builder);
        Assertions.assertEquals(9, builder.getDay());
        Assertions.assertEquals(3, builder.getMonth());
    }

    @Test
    public void parse_DayOfYear1() {
        DateBuilder builder = new DateFormat("yyyyDDD").parse("2024060");
        Assertions.assertNotNull(builder);
        Assertions.assertEquals(29, builder.getDay());
        Assertions.assertEquals(2, builder.getMonth());
    }
}
