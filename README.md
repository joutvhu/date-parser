# Date Parser

Utility to parse String to Date by target type and string format

## Using

```java
Date date = DateParser.instance().parse(Date.class, "2021-06-27 21:52:25.408", "yyyy-MM-dd HH:mm:ss.SSS");

LocalDateTime localDateTime = DateParser.quickParse(LocalDateTime.class, "2021-06-27 21:52:25.408", "yyyy-MM-dd HH:mm:ss.SSS");
```