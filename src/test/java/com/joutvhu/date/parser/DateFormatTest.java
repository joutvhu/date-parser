package com.joutvhu.date.parser;

import com.joutvhu.date.parser.domain.DateStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DateFormatTest {
    @Test
    public void parse_Case0() {
        DateStorage storage = new DateFormat("yyyyMd").parse("20211123");
        Assertions.assertNotNull(storage);
    }
}
