package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.sql.Date;

public class SqlDateConvertor implements Convertor<Date> {
    public static final SqlDateConvertor INSTANCE = new SqlDateConvertor();

    @Override
    public Date convert(DateBuilder builder) {
        return Date.valueOf(LocalDateConvertor.INSTANCE.convert(builder));
    }
}
