package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.TimeZone;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConvertorTest {
    @Test
    void instance_CalendarConvertor() {
        Assertions.assertEquals(CalendarConvertor.getInstance(), CalendarConvertor.getInstance());
    }

    @Test
    void instance_DateConvertor() {
        Assertions.assertEquals(DateConvertor.getInstance(), DateConvertor.getInstance());
    }

    @Test
    void instance_DayOfWeekConvertor() {
        Assertions.assertEquals(DayOfWeekConvertor.getInstance(), DayOfWeekConvertor.getInstance());
    }

    @Test
    void instance_InstantConvertor0() {
        Assertions.assertEquals(InstantConvertor.getInstance(), InstantConvertor.getInstance());
    }

    @Test
    void instance_InstantConvertor1() {
        ObjectiveDate objectiveDate = new ObjectiveDate(null, null);
        objectiveDate.setZone(null);
        InstantConvertor.getInstance().convert(objectiveDate, Instant.now());

        Assertions.assertNotNull(objectiveDate.getYear());
        Assertions.assertNotNull(objectiveDate.getZone());
    }

    @Test
    void instance_LocalDateConvertor() {
        Assertions.assertEquals(LocalDateConvertor.getInstance(), LocalDateConvertor.getInstance());
    }

    @Test
    void instance_LocalDateTimeConvertor() {
        Assertions.assertEquals(LocalDateTimeConvertor.getInstance(), LocalDateTimeConvertor.getInstance());
    }

    @Test
    void instance_LocalTimeConvertor() {
        Assertions.assertEquals(LocalTimeConvertor.getInstance(), LocalTimeConvertor.getInstance());
    }

    @Test
    void instance_LongConvertor() {
        Assertions.assertEquals(LongConvertor.getInstance(), LongConvertor.getInstance());
    }

    @Test
    void instance_MonthConvertor() {
        Assertions.assertEquals(MonthConvertor.getInstance(), MonthConvertor.getInstance());
    }

    @Test
    void instance_MonthDayConvertor() {
        Assertions.assertEquals(MonthDayConvertor.getInstance(), MonthDayConvertor.getInstance());
    }

    @Test
    void instance_OffsetDateTimeConvertor() {
        Assertions.assertEquals(OffsetDateTimeConvertor.getInstance(), OffsetDateTimeConvertor.getInstance());
    }

    @Test
    void instance_OffsetTimeConvertor() {
        Assertions.assertEquals(OffsetTimeConvertor.getInstance(), OffsetTimeConvertor.getInstance());
    }

    @Test
    void instance_SqlDateConvertor() {
        Assertions.assertEquals(SqlDateConvertor.getInstance(), SqlDateConvertor.getInstance());
    }

    @Test
    void instance_SqlTimeConvertor() {
        Assertions.assertEquals(SqlTimeConvertor.getInstance(), SqlTimeConvertor.getInstance());
    }

    @Test
    void instance_SqlTimestampConvertor() {
        Assertions.assertEquals(SqlTimestampConvertor.getInstance(), SqlTimestampConvertor.getInstance());
    }

    @Test
    void instance_TimeZoneConvertor() {
        Assertions.assertEquals(TimeZoneConvertor.getInstance(), TimeZoneConvertor.getInstance());
    }

    @Test
    void instance_YearConvertor() {
        Assertions.assertEquals(YearConvertor.getInstance(), YearConvertor.getInstance());
    }

    @Test
    void instance_YearMonthConvertor() {
        Assertions.assertEquals(YearMonthConvertor.getInstance(), YearMonthConvertor.getInstance());
    }

    @Test
    void instance_ZonedDateTimeConvertor() {
        Assertions.assertEquals(ZonedDateTimeConvertor.getInstance(), ZonedDateTimeConvertor.getInstance());
    }

    @Test
    void instance_ZoneOffsetConvertor0() {
        Assertions.assertEquals(ZoneOffsetConvertor.getInstance(), ZoneOffsetConvertor.getInstance());
    }

    @Test
    void instance_ZoneOffsetConvertor1() {
        ObjectiveDate objectiveDate = new ObjectiveDate(null, TimeZone.getTimeZone(ZoneOffset.of("+07:00")));
        ZoneOffset zoneOffset = ZoneOffsetConvertor.getInstance().convert(objectiveDate);
        Assertions.assertEquals(25200, zoneOffset.getTotalSeconds());
    }
}
