package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.sql.Date;

public class SqlDateConvertor implements Convertor<Date> {
    public static final SqlDateConvertor INSTANCE = new SqlDateConvertor();

    @Override
    public Date convert(ObjectiveDate objective) {
        return Date.valueOf(LocalDateConvertor.INSTANCE.convert(objective));
    }
}
