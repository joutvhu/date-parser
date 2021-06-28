package com.joutvhu.date.parser.util;

import lombok.experimental.UtilityClass;

import java.time.DateTimeException;
import java.time.Month;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoField;
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

    @SuppressWarnings("java:S131")
    public void checkValidDate(int year, int month, int dayOfMonth) {
        ChronoField.YEAR.checkValidValue(year);
        ChronoField.MONTH_OF_YEAR.checkValidValue(month);
        ChronoField.DAY_OF_MONTH.checkValidValue(dayOfMonth);

        if (dayOfMonth > 28) {
            int dom = 31;
            switch (month) {
                case 2:
                    dom = (IsoChronology.INSTANCE.isLeapYear(year) ? 29 : 28);
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    dom = 30;
                    break;
            }

            if (dayOfMonth > dom) {
                if (dayOfMonth == 29)
                    throw new DateTimeException("Invalid date 'February 29' as '" + year + "' is not a leap year");
                else
                    throw new DateTimeException("Invalid date '" + Month.of(month).name() + " " + dayOfMonth + "'");
            }
        }
    }
}
