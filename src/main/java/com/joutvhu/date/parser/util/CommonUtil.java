package com.joutvhu.date.parser.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtil {
    public boolean isNumber(String value) {
        return value.matches("^\\d+$");
    }

    public boolean isNumber(char value) {
        return '0' <= value && value <= '9';
    }
}
