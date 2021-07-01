package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

public class LongConvertor implements Convertor<Long> {
    private static LongConvertor instance;

    public static synchronized LongConvertor getInstance() {
        if (instance == null)
            instance = new LongConvertor();
        return instance;
    }

    @Override
    public Long convert(ObjectiveDate objective) {
        return CalendarConvertor.getInstance().convert(objective).getTimeInMillis();
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, Long object) {
        return objective;
    }
}
