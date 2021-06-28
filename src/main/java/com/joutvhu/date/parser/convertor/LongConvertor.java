package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

public class LongConvertor implements Convertor<Long> {
    public static final LongConvertor INSTANCE = new LongConvertor();

    @Override
    public Long convert(ObjectiveDate objective) {
        return CalendarConvertor.INSTANCE.convert(objective).getTimeInMillis();
    }
}
