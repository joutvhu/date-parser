package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface Convertor<T> {
    T convert(ObjectiveDate objective);

    ObjectiveDate convert(ObjectiveDate objective, T object);

    static <T> Class<T> typeOfConvertor(Convertor<T> convertor) {
        Class<? extends Convertor> convertorClass = convertor.getClass();
        for (Type type : convertorClass.getGenericInterfaces()) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                String rawTypeName = parameterizedType.getRawType().getTypeName();
                if ("com.joutvhu.date.parser.convertor.Convertor".equals(rawTypeName) &&
                    parameterizedType.getActualTypeArguments().length == 1) {
                    try {
                        return (Class<T>) Class.forName(parameterizedType
                                .getActualTypeArguments()[0].getTypeName());
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
