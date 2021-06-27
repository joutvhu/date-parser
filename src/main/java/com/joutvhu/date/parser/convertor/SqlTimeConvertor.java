package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.sql.Time;

public class SqlTimeConvertor implements Convertor<Time> {
    public static final SqlTimeConvertor INSTANCE = new SqlTimeConvertor();

    @Override
    public Time convert(DateBuilder builder) {
        return Time.valueOf(LocalTimeConvertor.INSTANCE.convert(builder));
    }
}
