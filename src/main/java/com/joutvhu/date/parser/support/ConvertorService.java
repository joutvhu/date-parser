package com.joutvhu.date.parser.support;

import com.joutvhu.date.parser.convertor.Convertor;

import java.util.Iterator;
import java.util.ServiceLoader;

@SuppressWarnings("java:S3740")
public class ConvertorService {
    private static ConvertorService instance;
    private final ServiceLoader<Convertor> loader;

    public ConvertorService() {
        this.loader = ServiceLoader.load(Convertor.class);
    }

    public static synchronized ConvertorService getInstance() {
        if (instance == null)
            instance = new ConvertorService();
        return instance;
    }

    @SuppressWarnings("java:S1066")
    public <T> Convertor<T> getConvertor(Class<T> type, boolean forParse) {
        if (type == null)
            return null;
        Iterator<Convertor> convertorIterator = this.loader.iterator();
        Convertor<T> subtypeConvertor = null;

        while (convertorIterator.hasNext()) {
            Convertor convertor = convertorIterator.next();
            Class<?> targetType = Convertor.typeOfConvertor(convertor);

            if (type.equals(targetType))
                return convertor;
            if (subtypeConvertor == null) {
                if ((forParse && type.isAssignableFrom(targetType)) ||
                        (!forParse && targetType.isAssignableFrom(type)))
                    subtypeConvertor = convertor;
            }
        }
        return subtypeConvertor;
    }
}
