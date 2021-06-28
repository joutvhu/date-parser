package com.joutvhu.date.parser.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
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
        return value.matches(".*(st|nd|rd|th)");
    }

    public int indexIgnoreCaseOf(String value, List<String> in) {
        for (int i = 0, len = in.size(); i < len; i++) {
            if (value != null && value.equalsIgnoreCase(in.get(i)))
                return i;
        }
        return -1;
    }

    public String rightPad(final String str, final int size, final char padChar) {
        if (str == null)
            return null;
        final int pads = size - str.length();
        if (pads <= 0)
            return str;
        if (pads > 8192)
            return rightPad(str, size, String.valueOf(padChar));
        return str.concat(repeat(padChar, pads));
    }

    public String rightPad(final String str, final int size, String padStr) {
        if (str == null)
            return null;
        if (padStr == null || padStr.length() == 0)
            padStr = " ";
        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0)
            return str;
        if (padLen == 1 && pads <= 8192)
            return rightPad(str, size, padStr.charAt(0));

        if (pads == padLen)
            return str.concat(padStr);
        else if (pads < padLen)
            return str.concat(padStr.substring(0, pads));
        else {
            final char[] padding = new char[pads];
            final char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return str.concat(new String(padding));
        }
    }

    public String repeat(final char ch, final int repeat) {
        if (repeat <= 0)
            return "";
        final char[] buf = new char[repeat];
        Arrays.fill(buf, ch);
        return new String(buf);
    }
}
