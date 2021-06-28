package com.joutvhu.date.parser.support;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

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
}
