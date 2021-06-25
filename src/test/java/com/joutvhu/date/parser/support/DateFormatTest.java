package com.joutvhu.date.parser.support;

import com.joutvhu.date.parser.domain.DateStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DateFormatTest {
    @Test
    public void parse_Case0() {
        DateStorage storage = new DateFormat("yyyyMd hh:mm:ss.SSS a Z").parse("20211123 02:41:32.651 pm America/Los_Angeles");
        Assertions.assertNotNull(storage);
    }
}
