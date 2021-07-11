package com.joutvhu.date.parser.util;

import lombok.experimental.UtilityClass;

import java.time.format.TextStyle;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;

@UtilityClass
public class NameUtil {
    @SuppressWarnings({"java:S135", "java:S1197", "java:S3776"})
    public <T> Map.Entry<String, T> findName(
            Iterator<String> iterator,
            T[] values,
            Locale[] locales,
            TextStyle[] styles,
            DisplayNameGetter<T> getter,
            Predicate<T> checker
    ) {
        String value = null;
        Boolean match[] = new Boolean[locales.length];

        while (iterator.hasNext()) {
            value = iterator.next();
            int length = value.length();
            boolean next = false;
            boolean checked = false;

            for (int li = 0, ll = locales.length; li < ll; li++) {
                if (length > 1 && match[li] == null)
                    continue;
                checked = true;
                match[li] = null;

                for (T v : values) {
                    for (int si = 0, sl = styles.length; si < sl; si++) {
                        String name = getter.getDisplayName(v, styles[si], locales[li]);

                        if (name.equalsIgnoreCase(value)) {
                            if (checker.test(v))
                                return new AbstractMap.SimpleEntry<>(value, v);
                            next = true;
                            match[li] = true;
                        }

                        if (match[li] == null &&
                            name.length() > length &&
                            String.valueOf(name.charAt(length - 1))
                                    .equalsIgnoreCase(
                                            String.valueOf(value.charAt(length - 1)))) {
                            match[li] = true;
                        }

                        if (next) break;
                    }
                    if (next) break;
                }
                if (next) break;
            }
            if (!checked) break;
        }

        return new AbstractMap.SimpleEntry<>(value, null);
    }

    @FunctionalInterface
    public interface DisplayNameGetter<T> {
        String getDisplayName(T value, TextStyle style, Locale locale);
    }
}
