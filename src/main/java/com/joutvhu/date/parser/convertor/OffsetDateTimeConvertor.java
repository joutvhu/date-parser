package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.OffsetDateTime;

public class OffsetDateTimeConvertor implements Convertor<OffsetDateTime> {
    private static OffsetDateTimeConvertor instance;

    public static synchronized OffsetDateTimeConvertor getInstance() {
        if (instance == null)
            instance = new OffsetDateTimeConvertor();
        return instance;
    }

    @Override
    public OffsetDateTime convert(ObjectiveDate objective) {
        return OffsetDateTime.of(
                LocalDateTimeConvertor.getInstance().convert(objective),
                ZoneOffsetConvertor.getInstance().convert(objective)
        );
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, OffsetDateTime object) {
        if (object != null) {
            ZoneOffsetConvertor.getInstance().convert(objective, object.getOffset());
            LocalDateTimeConvertor.getInstance().convert(objective, object.toLocalDateTime());
        }
        return objective;
    }
}
