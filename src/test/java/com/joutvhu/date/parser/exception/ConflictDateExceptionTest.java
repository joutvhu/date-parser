package com.joutvhu.date.parser.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConflictDateExceptionTest {
    @Test
    void get_conflict() {
        ConflictDateException exception = new ConflictDateException("", null, new Date());
        Assertions.assertTrue(exception.getConflict()[0] instanceof Date);
    }
}
