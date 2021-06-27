package com.joutvhu.date.parser;

import com.joutvhu.date.parser.exception.ParseException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.FastDateParser;

import java.text.ParsePosition;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@UtilityClass
public class DateParser {
    private static class ApacheDateParser extends FastDateParser {
        public ApacheDateParser(String pattern, TimeZone timeZone, Locale locale) {
            this(pattern, timeZone, locale, null);
        }

        public ApacheDateParser(String pattern, TimeZone timeZone, Locale locale, Date centuryStart) {
            super(pattern, timeZone, locale, centuryStart);
        }
    }

    public <T> T parse(Class<T> type, String value, String... patterns) {
        if (type == null)
            throw new IllegalArgumentException("Date Type must not be null");
        if (value == null || patterns == null)
            throw new IllegalArgumentException("Date and Patterns must not be null");

        TimeZone timeZone = TimeZone.getDefault();
        Locale locale = Locale.getDefault();
        ParsePosition pos = new ParsePosition(0);
        Calendar calendar = Calendar.getInstance(timeZone, locale);

        for (final String pattern : patterns) {
            final FastDateParser fdp = new DateParser.ApacheDateParser(pattern, timeZone, locale);
            calendar.clear();
            try {
                if (fdp.parse(value, pos, calendar) && pos.getIndex() == value.length())
                    return parse(type, calendar);
            } catch (final IllegalArgumentException ignore) {
                // leniency is preventing calendar from being set
            }
            pos.setIndex(0);
        }

        throw new ParseException("Unable to parse the date: " + value, patterns);
    }

    private <T> T parse(Class<T> type, Calendar calendar) {
        Object result = null;

        if (Calendar.class.equals(type)) {
            result = calendar;
        } else if (Date.class.equals(type)) {
            result = calendar.getTime();
        } else if (Long.class.equals(type)) {
            result = calendar.getTimeInMillis();
        } else if (Instant.class.equals(type)) {
            result = calendar.toInstant();
        } else if (TimeZone.class.equals(type)) {
            result = calendar.getTimeZone();
        } else if (ZoneOffset.class.equals(type)) {
            result = calendar.getTimeZone()
                    .toZoneId()
                    .getRules()
                    .getOffset(Instant.now());
        } else if (LocalDate.class.equals(type)) {
            result = LocalDate.of(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
        } else if (LocalTime.class.equals(type)) {
            result = LocalTime.of(
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    calendar.get(Calendar.SECOND),
                    calendar.get(Calendar.MILLISECOND) * 1000000
            );
        } else if (LocalDateTime.class.equals(type)) {
            result = LocalDateTime.of(
                    parse(LocalDate.class, calendar),
                    parse(LocalTime.class, calendar)
            );
        } else if (java.sql.Date.class.equals(type)) {
            result = java.sql.Date.valueOf(
                    parse(LocalDate.class, calendar)
            );
        } else if (java.sql.Time.class.equals(type)) {
            result = java.sql.Time.valueOf(
                    parse(LocalTime.class, calendar)
            );
        } else if (java.sql.Timestamp.class.equals(type)) {
            result = java.sql.Timestamp.valueOf(
                    parse(LocalDateTime.class, calendar)
            );
        } else if (ZonedDateTime.class.equals(type)) {
            result = ZonedDateTime.of(
                    parse(LocalDateTime.class, calendar),
                    calendar.getTimeZone().toZoneId()
            );
        } else if (OffsetTime.class.equals(type)) {
            result = OffsetTime.of(
                    parse(LocalTime.class, calendar),
                    calendar.getTimeZone()
                            .toZoneId()
                            .getRules()
                            .getOffset(Instant.now())
            );
        } else if (OffsetDateTime.class.equals(type)) {
            result = OffsetDateTime.of(
                    parse(LocalDateTime.class, calendar),
                    calendar.getTimeZone()
                            .toZoneId()
                            .getRules()
                            .getOffset(Instant.now())
            );
        } else if (DayOfWeek.class.equals(type)) {
            result = DayOfWeek.of(
                    calendar.get(Calendar.DAY_OF_WEEK)
            );
        } else if (MonthDay.class.equals(type)) {
            result = MonthDay.of(
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
        } else if (Month.class.equals(type)) {
            result = Month.of(
                    calendar.get(Calendar.MONTH)
            );
        } else if (Year.class.equals(type)) {
            result = Year.of(
                    calendar.get(Calendar.YEAR)
            );
        } else if (YearMonth.class.equals(type)) {
            result = YearMonth.of(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH)
            );
        } else {
            throw new ClassCastException("Unsupported " + type.getName() + " class.");
        }

        return (T) result;
    }
}
