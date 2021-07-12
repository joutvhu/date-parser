package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.support.DateFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Calendar;
import java.util.GregorianCalendar;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StrategyTest {
    @Test
    void parse_AmPm0() {
        DateFormat dateFormat = new DateFormat("a");
        ObjectiveDate objectiveDate = dateFormat.parse("am");
        Assertions.assertEquals(Calendar.AM, objectiveDate.<Integer>get(AmPmStrategy.AM_PM));
    }

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
    void parse_Hour0() {
        ObjectiveDate objectiveDate = new DateFormat("hh a")
                .parse("11 am");

        Assertions.assertEquals(11, objectiveDate.getHour());
    }

    @Test
    void parse_Hour1() {
        ObjectiveDate objectiveDate = new DateFormat("HH")
                .parse("23");

        Assertions.assertEquals(23, objectiveDate.getHour());
    }

    @Test
    void parse_Hour2() {
        ObjectiveDate objectiveDate = new DateFormat("kk")
                .parse("24");

        Assertions.assertEquals(0, objectiveDate.getHour());
    }

    @Test
    void parse_Hour3() {
        ObjectiveDate objectiveDate = new DateFormat("K a")
                .parse("0 pm");

        Assertions.assertEquals(12, objectiveDate.getHour());
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

    @Test
    void parse_Weekday0() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("e");
            dateFormat.parse("8");
        });
    }

    @Test
    void parse_Weekday1() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("e");
            dateFormat.parse("M");
        });
    }

    @Test
    void parse_Weekday2() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("E");
            dateFormat.parse("Mom");
        });
    }

    @Test
    void parse_Zone0() {
        Assertions.assertThrows(Exception.class, () -> {
            DateFormat dateFormat = new DateFormat("Z");
            dateFormat.parse(" UTC");
        });
    }

    @Test
    void format_AmPm0() {
        ObjectiveDate objectiveDate = new ObjectiveDate(null, null);
        objectiveDate.set(AmPmStrategy.AM_PM, Calendar.AM);

        DateFormat dateFormat = new DateFormat("a");
        String result = dateFormat.format(objectiveDate);

        Assertions.assertEquals("AM", result);
    }

    @Test
    void format_AmPm1() {
        ObjectiveDate objectiveDate = new ObjectiveDate(null, null);
        objectiveDate.set(AmPmStrategy.AM_PM, Calendar.PM);

        DateFormat dateFormat = new DateFormat("a");
        String result = dateFormat.format(objectiveDate);

        Assertions.assertEquals("PM", result);
    }

    @Test
    void format_Era0() {
        ObjectiveDate objectiveDate = new ObjectiveDate(null, null);
        objectiveDate.set(EraStrategy.ERA, GregorianCalendar.AD);

        DateFormat dateFormat = new DateFormat("G");
        String result = dateFormat.format(objectiveDate);

        Assertions.assertEquals("AD", result);
    }

    @Test
    void format_Era1() {
        ObjectiveDate objectiveDate = new ObjectiveDate(null, null);
        objectiveDate.set(EraStrategy.ERA, GregorianCalendar.BC);

        DateFormat dateFormat = new DateFormat("G");
        String result = dateFormat.format(objectiveDate);

        Assertions.assertEquals("BC", result);
    }

    @Test
    void format_Hour0() {
        ObjectiveDate objectiveDate = new ObjectiveDate(null, null);
        objectiveDate.setHour(12);

        DateFormat dateFormat = new DateFormat("h");
        String result = dateFormat.format(objectiveDate);

        Assertions.assertEquals("12", result);
    }

    @Test
    void format_WeekdayInMonth0() {
        ObjectiveDate objectiveDate = new ObjectiveDate(null, null);
        objectiveDate.set(WeekdayInMonthStrategy.WEEKDAY_IN_MONTH, 2);

        DateFormat dateFormat = new DateFormat("F");
        String result = dateFormat.format(objectiveDate);

        Assertions.assertEquals("2", result);
    }

    @Test
    void format_Week0() {
        ObjectiveDate objectiveDate = new ObjectiveDate(null, null);
        objectiveDate.set(WeekStrategy.WEEK_OF_YEAR, 22);

        DateFormat dateFormat = new DateFormat("w");
        String result = dateFormat.format(objectiveDate);

        Assertions.assertEquals("22", result);
    }

    @Test
    void format_Week1() {
        ObjectiveDate objectiveDate = new ObjectiveDate(null, null);
        objectiveDate.set(WeekStrategy.WEEK_OF_MONTH, 3);

        DateFormat dateFormat = new DateFormat("W");
        String result = dateFormat.format(objectiveDate);

        Assertions.assertEquals("3", result);
    }
}
