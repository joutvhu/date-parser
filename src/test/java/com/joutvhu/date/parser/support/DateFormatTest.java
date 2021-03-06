package com.joutvhu.date.parser.support;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.strategy.QuarterStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DateFormatTest {
    @Test
    void parse_Case0() {
        ObjectiveDate objective = new DateFormat("yyyyDDD hh:mm:ss.SSS a Z")
                .parse("2021068 02:41:32.651 pm America/Los_Angeles");
        Assertions.assertNotNull(objective);
    }

    @Test
    void parse_DayOfYear0() {
        ObjectiveDate objective = new DateFormat("yyyyDDD").parse("2021068");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(9, objective.getDay());
        Assertions.assertEquals(3, objective.getMonth());
    }

    @Test
    void parse_DayOfYear1() {
        ObjectiveDate objective = new DateFormat("yyyyDDD").parse("2024060");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(29, objective.getDay());
        Assertions.assertEquals(2, objective.getMonth());
    }

    @Test
    void parse_DayOfYear2() {
        ObjectiveDate objective = new DateFormat("yyyy DDDo").parse("2021 60th");
        Assertions.assertEquals(1, objective.getDay());
        Assertions.assertEquals(3, objective.getMonth());
    }

    @Test
    void parse_Quarter0() {
        Assertions.assertThrows(Exception.class, () -> {
            new DateFormat("yyyyMd Q").parse("20210523 3");
        });
    }

    @Test
    void parse_Quarter1() {
        ObjectiveDate objective = new DateFormat("yyyyMd Q").parse("20210523 2");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(2, (Integer) objective.get(QuarterStrategy.QUARTER));
    }

    @Test
    void parse_Quarter2() {
        ObjectiveDate objective = new DateFormat("M Q").parse("03 1");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(1, (Integer) objective.get(QuarterStrategy.QUARTER));
    }

    @Test
    void parse_Quarter4() {
        ObjectiveDate objective = new DateFormat("M Q").parse("09 3");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(3, (Integer) objective.get(QuarterStrategy.QUARTER));
    }

    @Test
    void parse_Quarter5() {
        ObjectiveDate objective = new DateFormat("M Q").parse("08 3");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(3, (Integer) objective.get(QuarterStrategy.QUARTER));
    }

    @Test
    void parse_Quarter6() {
        ObjectiveDate objective = new DateFormat("M Q").parse("10 4");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(4, (Integer) objective.get(QuarterStrategy.QUARTER));
    }

    @Test
    void parse_Quarter7() {
        ObjectiveDate objective = new DateFormat("M Q").parse("12 4");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(4, (Integer) objective.get(QuarterStrategy.QUARTER));
    }

    @Test
    void parse_Quarter8() {
        ObjectiveDate objective = new DateFormat("QQQ").parse("Q3");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(3, (Integer) objective.get(QuarterStrategy.QUARTER));
    }

    @Test
    void parse_Quarter9() {
        ObjectiveDate objective = new DateFormat("qqq").parse("003");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(3, (Integer) objective.get(QuarterStrategy.QUARTER));
    }

    @Test
    void parse_Quarter10() {
        ObjectiveDate objective = new DateFormat("qo").parse("1st");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(1, (Integer) objective.get(QuarterStrategy.QUARTER));
    }

    @Test
    void parse_Quarter11() {
        ObjectiveDate objective = new DateFormat("QQQQ").parse("1st quarter");
        Assertions.assertNotNull(objective);
        Assertions.assertEquals(1, (Integer) objective.get(QuarterStrategy.QUARTER));
    }

    @Test
    void parse_WeekOfYear0() {
        ObjectiveDate objective = new DateFormat("yyyy ww E").parse("2021 26 Tue");
        Assertions.assertEquals(29, objective.getDay());
        Assertions.assertEquals(6, objective.getMonth());
    }

    @Test
    void parse_WeekOfMonth0() {
        ObjectiveDate objective = new DateFormat("yyyy MM W e").parse("2021 06 3 2");
        Assertions.assertEquals(15, objective.getDay());
        Assertions.assertEquals(6, objective.getMonth());
    }

    @Test
    void parse_WeekdayInMonth0() {
        ObjectiveDate objective = new DateFormat("yyyy MM F e").parse("2021 06 4 3");
        Assertions.assertEquals(23, objective.getDay());
        Assertions.assertEquals(6, objective.getMonth());
    }

    @Test
    void parse_WeekdayInMonth1() {
        ObjectiveDate objective = new DateFormat("yyyy MM F e").parse("2021 06 3 7");
        Assertions.assertEquals(20, objective.getDay());
        Assertions.assertEquals(6, objective.getMonth());
    }

    @Test
    void parse_Century0() {
        ObjectiveDate objective = new DateFormat("yy CC").parse("21 21");
        Assertions.assertEquals(2021, objective.getYear());
    }

    @Test
    void parse_Century1() {
        ObjectiveDate objective = new DateFormat("yy CC").parse("95 20");
        Assertions.assertEquals(1995, objective.getYear());
    }

    @Test
    void parse_Era0() {
        ObjectiveDate objective = new DateFormat("yyyy GG").parse("2021 BC");
        Assertions.assertEquals(-2021, objective.getYear());
    }

    @Test
    void parse_Era1() {
        ObjectiveDate objective = new DateFormat("yyyy GG").parse("2021 AD");
        Assertions.assertEquals(2021, objective.getYear());
    }
}
