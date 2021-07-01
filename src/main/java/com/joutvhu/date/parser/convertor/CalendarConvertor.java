package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.util.Calendar;
import java.util.Objects;

public class CalendarConvertor implements Convertor<Calendar> {
    private static CalendarConvertor instance;

    public static synchronized CalendarConvertor getInstance() {
        if (instance == null)
            instance = new CalendarConvertor();
        return instance;
    }

    @Override
    public Calendar convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getZone());
        Objects.requireNonNull(objective.getLocale());
        Objects.requireNonNull(objective.getYear());
        Objects.requireNonNull(objective.getMonth());
        Objects.requireNonNull(objective.getDay());

        Calendar calendar = Calendar.getInstance(objective.getZone(), objective.getLocale());
        calendar.set(
                objective.getYear(),
                objective.getMonth() - 1,
                objective.getDay(),
                objective.getHour() != null ? objective.getHour() : 0,
                objective.getMinute() != null ? objective.getMinute() : 0,
                objective.getSecond() != null ? objective.getSecond() : 0
        );
        calendar.set(Calendar.MILLISECOND, objective.getNano() != null ? objective.getNano() / 1000000 : 0);

        return calendar;
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, Calendar object) {
        if (object != null) {
            objective.setYear(object.get(Calendar.YEAR));
            objective.setMonth(object.get(Calendar.MONTH) + 1);
            objective.setDay(object.get(Calendar.DAY_OF_MONTH));
            objective.setHour(object.get(Calendar.HOUR_OF_DAY));
            objective.setMinute(object.get(Calendar.MINUTE));
            objective.setSecond(object.get(Calendar.SECOND));
            objective.setNano(object.get(Calendar.MILLISECOND) * 1000000);
            if (object.getTimeZone() != null)
                objective.setZone(object.getTimeZone());
        }
        return objective;
    }
}
