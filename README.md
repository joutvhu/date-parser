# Date Parser

Utility to parse String to Date by target type and string format

## Using

```java
Date date = DateParser.parse(Date.class, "2021-06-27 21:52:25.408", "yyyy-MM-dd HH:mm:ss.SSS");

LocalDateTime localDateTime = DateParser.parse(LocalDateTime.class, "2021-06-27 21:52:25.408", "yyyy-MM-dd HH:mm:ss.SSS");
```