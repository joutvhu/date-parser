package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.support.DateFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StrategyTest {
    @Test
    void parse_Century0() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("CCo");
            dateFormat.parse("12 ");
        });
    }

    @Test
    void parse_Century1() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("CC");
            dateFormat.parse("1st");
        });
    }

    @Test
    void parse_Year0() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("yyyy");
            dateFormat.parse("8c0f");
        });
    }

    @Test
    void parse_Month0() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("MMM");
            dateFormat.parse("Jul ");
        });
    }

    @Test
    void parse_Month1() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("MMo");
            dateFormat.parse("11xx");
        });
    }

    @Test
    void parse_Day0() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("ddo");
            dateFormat.parse("11");
        });
    }

    @Test
    void parse_Week0() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("wwo");
            dateFormat.parse("11");
        });
    }

    @Test
    void parse_Week1() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("WW");
            dateFormat.parse("10");
        });
    }

    @Test
    void parse_Week2() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("WW");
            dateFormat.parse("1x");
        });
    }
}
