package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.OffsetTime;

public class OffsetTimeConvertor implements Convertor<OffsetTime> {
    public static final OffsetTimeConvertor INSTANCE = new OffsetTimeConvertor();

    @Override
    public OffsetTime convert(ObjectiveDate objective) {
        return OffsetTime.of(
                LocalTimeConvertor.INSTANCE.convert(objective),
                ZoneOffsetConvertor.INSTANCE.convert(objective)
        );
    }
}
