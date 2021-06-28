package com.joutvhu.date.parser.support;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DateFormatTest {
    @Test
    public void parse_Case0() {
        ObjectiveDate objective = new DateFormat("yyyyDDD hh:mm:ss.SSS a Z")
                .parse("2021068 02:41:32.651 pm America/Los_Angeles");
        Assertions.assertNotNull(objective);
    }

    @Test
    public void parse_DayOfYear0() {
        ObjectiveDate objective = new DateFormat("yyyyDDD").parse("2021068");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(9, objective.getDay());
        Assertions.assertEquals(3, objective.getMonth());
    }

    @Test
    public void parse_DayOfYear1() {
        ObjectiveDate objective = new DateFormat("yyyyDDD").parse("2024060");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(29, objective.getDay());
        Assertions.assertEquals(2, objective.getMonth());
    }

    @Test
    public void parse_DayOfYear2() {
        ObjectiveDate objective = new DateFormat("yyyy DDDo").parse("2021 60th");
        Assertions.assertEquals(1, objective.getDay());
        Assertions.assertEquals(3, objective.getMonth());
    }

    @Test
    public void parse_Quarter0() {
        Assertions.assertThrows(Exception.class, () -> {
            new DateFormat("yyyyMd Q").parse("20210523 3");
        });
    }

    @Test
    public void parse_Quarter1() {
        ObjectiveDate objective = new DateFormat("yyyyMd Q").parse("20210523 2");
        Assertions.assertNotNull(objective);
    }

    @Test
    public void parse_WeekOfYear0() {
        ObjectiveDate objective = new DateFormat("yyyy ww E").parse("2021 26 Tue");
        Assertions.assertEquals(29, objective.getDay());
        Assertions.assertEquals(6, objective.getMonth());
    }

    @Test
    public void parse_WeekOfMonth0() {
        ObjectiveDate objective = new DateFormat("yyyy MM W u").parse("2021 06 3 2");
        Assertions.assertEquals(15, objective.getDay());
        Assertions.assertEquals(6, objective.getMonth());
    }

    @Test
    public void parse_WeekdayInMonth0() {
        ObjectiveDate objective = new DateFormat("yyyy MM F u").parse("2021 06 4 3");
        Assertions.assertEquals(23, objective.getDay());
        Assertions.assertEquals(6, objective.getMonth());
    }

    @Test
    public void parse_WeekdayInMonth1() {
        ObjectiveDate objective = new DateFormat("yyyy MM F u").parse("2021 06 3 7");
        Assertions.assertEquals(20, objective.getDay());
        Assertions.assertEquals(6, objective.getMonth());
    }

    @Test
    public void parse_Century0() {
        ObjectiveDate objective = new DateFormat("yy CC").parse("21 21");
        Assertions.assertEquals(2021, objective.getYear());
    }

    @Test
    public void parse_Century1() {
        ObjectiveDate objective = new DateFormat("yy CC").parse("95 20");
        Assertions.assertEquals(1995, objective.getYear());
    }

    @Test
    public void parse_Era0() {
        ObjectiveDate objective = new DateFormat("yyyy GG").parse("2021 BC");
        Assertions.assertEquals(-2021, objective.getYear());
    }

    @Test
    public void parse_Era1() {
        ObjectiveDate objective = new DateFormat("yyyy GG").parse("2021 AD");
        Assertions.assertEquals(2021, objective.getYear());
    }
}
