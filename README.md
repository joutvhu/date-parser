# Date Parser

[![GitHub](https://img.shields.io/github/license/joutvhu/date-parser)](https://github.com/joutvhu/date-parser/blob/main/LICENSE)
[![codecov](https://codecov.io/gh/joutvhu/date-parser/branch/main/graph/badge.svg?token=2OJDWTPPGW)](https://codecov.io/gh/joutvhu/date-parser)

Utility to parse String to Date by target type and string format

## Using

```java
Date date = DateParser.getInstance().parse(Date.class, "2021-06-27 21:52:25.408", "yyyy-MM-dd HH:mm:ss.SSS");

LocalDateTime localDateTime = DateParser.quickParse(LocalDateTime.class, "2021-06-27 21:52:25.408", "yyyy-MM-dd HH:mm:ss.SSS");
```
