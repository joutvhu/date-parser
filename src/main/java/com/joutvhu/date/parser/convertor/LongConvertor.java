package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

public class LongConvertor implements Convertor<Long> {
    public static final LongConvertor INSTANCE = new LongConvertor();

    @Override
    public Long convert(DateBuilder builder) {
        return CalendarConvertor.INSTANCE.convert(builder).getTimeInMillis();
    }
}
