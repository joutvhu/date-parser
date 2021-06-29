package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.sql.Timestamp;

public class SqlTimestampConvertor implements Convertor<Timestamp> {
    private static SqlTimestampConvertor instance;

    public static synchronized SqlTimestampConvertor getInstance() {
        if (instance == null)
            instance = new SqlTimestampConvertor();
        return instance;
    }

    @Override
    public Timestamp convert(ObjectiveDate objective) {
        return Timestamp.valueOf(LocalDateTimeConvertor.getInstance().convert(objective));
    }
}
