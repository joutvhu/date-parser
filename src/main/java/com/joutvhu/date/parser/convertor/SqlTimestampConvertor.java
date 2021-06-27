package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.sql.Timestamp;

public class SqlTimestampConvertor implements Convertor<Timestamp> {
    public static final SqlTimestampConvertor INSTANCE = new SqlTimestampConvertor();

    @Override
    public Timestamp convert(DateBuilder builder) {
        return Timestamp.valueOf(LocalDateTimeConvertor.INSTANCE.convert(builder));
    }
}
