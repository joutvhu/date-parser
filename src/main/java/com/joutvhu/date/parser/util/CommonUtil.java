package com.joutvhu.date.parser.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@UtilityClass
public class CommonUtil {
    public boolean isNumber(String value) {
        return value.matches("^\\d+$");
    }

    public boolean isNumber(char value) {
        return '0' <= value && value <= '9';
    }

    public boolean isNumber(AtomicBoolean first, String value) {
        if (first.get()) {
            first.set(false);
            return isNumber(value);
        } else {
            return isNumber(value.charAt(value.length() - 1));
        }
    }

    public boolean hasOrdinal(String value) {
        return value.matches("(st|nd|rd|th)$");
    }

    public int indexIgnoreCaseOf(String value, List<String> in) {
        for (int i = 0, len = in.size(); i < len; i++) {
            if (value != null && value.equalsIgnoreCase(in.get(i)))
                return i;
        }
        return -1;
    }
}
