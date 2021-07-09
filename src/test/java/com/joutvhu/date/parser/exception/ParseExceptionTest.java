package com.joutvhu.date.parser.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParseExceptionTest {
    @Test
    void get_pattern0() {
        ParseException exception = new ParseException("", "");
        Assertions.assertEquals("", exception.getPatterns()[0]);
    }

    @Test
    void get_pattern1() {
        ParseException exception = new ParseException(new Exception(), "");
        Assertions.assertEquals("", exception.getPatterns()[0]);
    }

    @Test
    void get_pattern2() {
        ParseException exception = new ParseException("", null, true, true, "");
        Assertions.assertEquals("", exception.getPatterns()[0]);
    }
}
