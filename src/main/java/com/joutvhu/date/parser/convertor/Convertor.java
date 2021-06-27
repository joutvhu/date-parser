package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

public interface Convertor<T> {
    T convert(DateBuilder builder);
}
