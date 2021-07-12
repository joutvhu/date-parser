# Date Parser

[![GitHub](https://img.shields.io/github/license/joutvhu/date-parser)](https://github.com/joutvhu/date-parser/blob/main/LICENSE)
[![codecov](https://codecov.io/gh/joutvhu/date-parser/branch/main/graph/badge.svg?token=2OJDWTPPGW)](https://codecov.io/gh/joutvhu/date-parser)

This is a Date Utility with two purposes:
- Parse String to Date according to a target class, and the pattern strings.
- Format Date to String based on a pattern string.

## Installation

- If you are using Gradle just add the following dependency to your `build.gradle`.

```groovy
implementation "com.github.joutvhu:date-parser:1.0.0"
```

- Or add the following dependency to your `pom.xml` if you are using Maven.

```xml
<dependency>
    <groupId>com.github.joutvhu</groupId>
    <artifactId>date-parser</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Using

### DateFormatter

- `withConvertor(Class<T> typeOfConvertor, Convertor<T> convertor)` or `withConvertor(Convertor<T> convertor)` used to add a custom convertor to the `DateFormatter`.
  - Use these methods to add a [`Convertor`](./src/main/java/com/joutvhu/date/parser/convertor/Convertor.java).

- `withLocale(Locale defaultLocale)` used to set default `java.util.Locale`, if this value is null the default value is `Locale.getDefault()`.

- `withZone(TimeZone defaultZone)` used to set default `java.util.TimeZone`, if this value is null the default value is `TimeZone.getDefault()`.

- `withWeekFields(WeekFields defaultWeekFields)` used to set default `java.time.temporal.WeekFields`, if this value is null the default value is `WeekFields.of(locale)`.

- `withStrategyFactory(StrategyFactory strategyFactory)` used to override [`StrategyFactory`](./src/main/java/com/joutvhu/date/parser/strategy/StrategyFactory.java).
  - Use this method to add, update or delete a [`Strategy`](./src/main/java/com/joutvhu/date/parser/strategy/Strategy.java).

- `parse(Class<T> type, String value, String... patterns)` used to parse string to date.
  - `type` is the type of target object you want to get.
  - `value` is the input string you want to convert to the target object.
  - `patterns` are the possible formats of the input string.

- `format(T object, String pattern)` used to format an object to a target string.
  - `object` is the input object you want to convert to string.
  - `pattern` is the format of the target string.

### DateParser

- `DateParser.formatter()` will be return a new [`DateFormatter`](#DateFormatter).

- `DateParser.parse(Class<T> type, String value, String... patterns)` is equivalent to `DateParser.formatter().parse(Class<T> type, String value, String... patterns)`.

- `DateParser.format(T object, String pattern)` is equivalent to `DateParser.formatter().format(T object, String pattern)`.

## Example

- Parse Date
```java
Date date = DateParser.formatter().parse(Date.class, "2021-06-27 21:52:25.408", "yyyy-MM-dd HH:mm:ss.SSS");

LocalDateTime localDateTime = DateParser.parse(LocalDateTime.class, "2021-06-27 21:52:25.408", "yyyy-MM-dd HH:mm:ss.SSS");

Instant instant = DateParser.parse(Instant.class, "2021-06-28T02:22:48.780101Z", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");

DayOfWeek dayOfWeek = DateParser.formatter().withWeekFields(WeekFields.ISO).parse(DayOfWeek.class, "Sun", "E");
```

- Format Date
```java
String date = DateParser.format(new Date(), "MMM dd, yyyy hh:mm:ss a");

String instant = DateParser.format(Instant.now(), "yyyy-MM-dd'T'HH:mm:ss.SSSX");

String yearMonth = DateParser.format(YearMonth.of(2021, Month.DECEMBER), "Mo yyyy");
```
