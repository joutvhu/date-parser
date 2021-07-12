package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

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
        if (object != null) {
            Calendar calendar = Calendar.getInstance(
                    CommonUtil.defaultIfNull(objective.getZone(), TimeZone.getDefault()),
                    CommonUtil.defaultIfNull(objective.getLocale(), Locale.getDefault())
            );
            calendar.setTimeInMillis(object);
            CalendarConvertor.getInstance().convert(objective, calendar);
        }
        return objective;
    }
}
