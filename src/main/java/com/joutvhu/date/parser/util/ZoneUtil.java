package com.joutvhu.date.parser.util;

import lombok.experimental.UtilityClass;
import sun.util.calendar.ZoneInfoFile;

import java.text.DateFormatSymbols;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

@UtilityClass
public class ZoneUtil {
    private String zoneIdOfName(String name, DateFormatSymbols symbols) {
        if (name == null)
            return null;

        String[][] zoneStrings = symbols.getZoneStrings();
        for (String[] zones : zoneStrings) {
            for (int i = 0, len = zones.length; i < len; i++) {
                if (name.equalsIgnoreCase(zones[i]))
                    return zones[0];
            }
        }
        return null;
    }

    private String zoneIdOfName(String name, Locale locale) {
        if (name == null)
            return null;

        DateFormatSymbols localeSymbols = null;
        if (locale != null) {
            localeSymbols = DateFormatSymbols.getInstance(locale);
            String zoneName = zoneIdOfName(name, localeSymbols);
            if (zoneName != null)
                return zoneName;
        }

        DateFormatSymbols rootSymbols = DateFormatSymbols.getInstance(Locale.ROOT);
        if (rootSymbols.equals(localeSymbols))
            return null;
        else
            return zoneIdOfName(name, rootSymbols);
    }

    private ZoneId zoneIdOf(String zoneId) {
        Objects.requireNonNull(zoneId, "Zone ID must be not null.");

        if (zoneId.equalsIgnoreCase("Z"))
            return ZoneOffset.UTC;
        else if (zoneId.length() <= 1 || zoneId.startsWith("+") || zoneId.startsWith("-"))
            return ZoneOffset.of(zoneId);
        else if (zoneId.startsWith("UTC") || zoneId.startsWith("GMT") || zoneId.startsWith("UT"))
            return ZoneId.of(zoneId);
        else {
            String id = ZoneId.SHORT_IDS
                    .entrySet()
                    .stream()
                    .filter(entry -> zoneId.equalsIgnoreCase(entry.getKey()) || zoneId
                            .equalsIgnoreCase(entry.getValue()))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);
            if (id != null)
                return ZoneId.of(id, ZoneId.SHORT_IDS);
        }

        return null;
    }

    public TimeZone getTimeZone(String zone, Locale locale) {
        try {
            ZoneId zoneId = zoneIdOf(zone);
            if (zoneId != null) {
                TimeZone timeZone = TimeZone.getTimeZone(zoneId);
                if (timeZone != null)
                    return timeZone;
            }

            String zoneString = zoneIdOfName(zone, locale);
            return zoneString != null ? ZoneInfoFile.getZoneInfo(zoneString) : null;
        } catch (Exception e) {
            return null;
        }
    }
}
