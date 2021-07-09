package com.joutvhu.date.parser.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MismatchPatternExceptionTest {
    @Test
    void get_pattern() {
        MismatchPatternException exception = new MismatchPatternException("", null, 0, "");
        Assertions.assertEquals("", exception.getPattern());
    }

    @Test
    void get_position() {
        MismatchPatternException exception = new MismatchPatternException("", null, 0, "");
        Assertions.assertEquals(0, exception.getPosition());
    }
}
