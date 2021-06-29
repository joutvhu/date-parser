package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.OffsetTime;

public class OffsetTimeConvertor implements Convertor<OffsetTime> {
    private static OffsetTimeConvertor instance;

    public static synchronized OffsetTimeConvertor getInstance() {
        if (instance == null)
            instance = new OffsetTimeConvertor();
        return instance;
    }

    @Override
    public OffsetTime convert(ObjectiveDate objective) {
        return OffsetTime.of(
                LocalTimeConvertor.getInstance().convert(objective),
                ZoneOffsetConvertor.getInstance().convert(objective)
        );
    }
}
