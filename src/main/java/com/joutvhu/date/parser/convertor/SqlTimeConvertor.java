package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.sql.Time;

public class SqlTimeConvertor implements Convertor<Time> {
    public static final SqlTimeConvertor INSTANCE = new SqlTimeConvertor();

    @Override
    public Time convert(ObjectiveDate objective) {
        return Time.valueOf(LocalTimeConvertor.INSTANCE.convert(objective));
    }
}
