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
    private String[][] zoneMappings = null;

    private String[][] getZoneMappings() {
        if (zoneMappings == null) {
            zoneMappings = new String[][]{
                    new String[]{"+09:30", "ACDT", "Australian Central Daylight Time", "Cen. Australia Standard Time"},
                    new String[]{"+09:30", "ACST", "Australian Central Standard Time", "AUS Central Standard Time"},
                    new String[]{"+10:00", "AEDT", "Australian Eastern Daylight Time", "AUS Eastern Standard Time"},
                    new String[]{"+10:00", "AEST", "Australian Eastern Standard Time", "E. Australia Standard Time"},
                    new String[]{"+04:30", "AFT", "Afghanistan Time", "Afghanistan Standard Time"},
                    new String[]{"-09:00", "AKST", "Alaska Standard Time", "Alaskan Standard Time"},
                    new String[]{"+04:00", "AMST", "Armenia Standard Time", "Armenian Standard Time"},
                    new String[]{"-03:00", "ART", "Argentina Standard Time"},
                    new String[]{"-04:00", "AST", "Atlantic Standard Time", "Atlantic Time", "Atlantic Time (Canada)"},
                    new String[]{"+03:00", "ARAB", "Arab Standard Time"},
                    new String[]{"+04:00", "ARABIA", "Arabian Standard Time"},
                    new String[]{"+03:00", "ARABIC", "Arabic Standard Time"},
                    new String[]{"+08:00", "AWST", "Australian Western Standard Time", "W. Australia Standard Time"},
                    new String[]{"-01:00", "AZOST", "Azores Standard Time"},
                    new String[]{"+04:00", "AZT", "Azerbaijan Time", "Azerbaijan Standard Time"},
                    new String[]{"-06:00", "CAST", "Central America Standard Time"},
                    new String[]{"+06:00", "CAT", "Central Asia Time", "Central Asia Standard Time"},
                    new String[]{"-04:00", "CBST", "Central Brazilian Time", "Central Brazilian Standard Time"},
                    new String[]{"+01:00", "CET", "Central Europe Time", "Central Europe Standard Time"},
                    new String[]{"+01:00", "CEST", "Central European Standard Time"},
                    new String[]{"+04:00", "CCS", "Caucasus Time", "Caucasus Standard Time"},
                    new String[]{"-03:00", "CGT", "Greenland Standard Time"},
                    new String[]{"+08:00", "CHN", "China Standard Time"},
                    new String[]{"+11:00", "CPST", "Central Pacific Standard Time"},
                    new String[]{"-06:00", "CST", "Central Standard Time", "Central Time (US & Canada)"},
                    new String[]{"-06:00", "CSTM", "Central Standard Time (Mexico)"},
                    new String[]{"-01:00", "CVT", "Cape Verde Time", "Cape Verde Standard Time"},
                    new String[]{"-12:00", "DST", "Dateline Standard Time", "International Date Line West"},
                    new String[]{"+03:00", "EAT", "East Africa Time", "E. Africa Standard Time"},
                    new String[]{"+02:00", "EET", "Eastern European Time", "E. Europe Standard Time"},
                    new String[]{"+02:00", "EGY", "Egypt Standard Time"},
                    new String[]{"+05:00", "EKB", "Ekaterinburg Standard Time"},
                    new String[]{"-03:00", "ESAST", "South America Eastern Standard Time", "E. South America Standard Time"},
                    new String[]{"-05:00", "EST", "Eastern Standard Time", "Eastern Time (US & Canada)"},
                    new String[]{"+12:00", "FJT", "Fiji Standard Time"},
                    new String[]{"+02:00", "FLE", "FLE Standard Time"},
                    new String[]{"+03:00", "GET", "Georgia Standard Time", "Georgian Standard Time"},
                    new String[]{"GMT", "GMT", "Greenwich Mean Time", "GMT Standard Time"},
                    new String[]{"GMT", "GST", "Greenwich Standard Time"},
                    new String[]{"+02:00", "GTB", "GTB Standard Time"},
                    new String[]{"-10:00", "HAST", "Hawaii-Aleutian Standard Time", "Hawaiian Standard Time"},
                    new String[]{"+02:00", "ISRAEL", "Israel Standard Time", "Israel Time"},
                    new String[]{"+02:00", "JRD", "Jordan Standard Time"},
                    new String[]{"+09:00", "JST", "Japan Standard Time", "Tokyo Standard Time"},
                    new String[]{"-02:00", "MAST", "Mid-Atlantic Standard Time"},
                    new String[]{"+02:00", "MEST", "Middle East Standard Time"},
                    new String[]{"+06:30", "MMT", "Myanmar Standard Time"},
                    new String[]{"GMT", "MRC", "Morocco Standard Time"},
                    new String[]{"+03:00", "MSK", "Moscow Standard Time", "Russian Standard Time"},
                    new String[]{"-07:00", "MST", "Mountain Standard Time", "Mountain Time (US & Canada)"},
                    new String[]{"-07:00", "MSTM", "Mountain Standard Time (Mexico)"},
                    new String[]{"+04:00", "MUT", "Mauritius Time", "Mauritius Standard Time"},
                    new String[]{"-03:00", "MVD", "Montevideo Standard Time"},
                    new String[]{"+08:00", "NAEST", "North Asia East Standard Time"},
                    new String[]{"+07:00", "NAST", "North Asia Standard Time"},
                    new String[]{"+06:00", "NCAST", "North Central Asia Standard Time"},
                    new String[]{"-03:30", "NLT", "Newfoundland Time", "Newfoundland Standard Time"},
                    new String[]{"+02:00", "NMT", "Namibia Time", "Namibia Standard Time"},
                    new String[]{"+05:45", "NPT", "Nepal Time", "Nepal Standard Time"},
                    new String[]{"-04:00", "PSA", "Pacific South America Time", "Pacific SA Standard Time"},
                    new String[]{"-08:00", "PST", "Pacific Standard Time", "Pacific Time (US & Canada)"},
                    new String[]{"-08:00", "PSTM", "Pacific Standard Time (Mexico)"},
                    new String[]{"+05:00", "PAK", "Pakistan Standard Time"},
                    new String[]{"+01:00", "ROM", "Romance Standard Time"},
                    new String[]{"-03:00", "SAEST", "SA Eastern Standard Time"},
                    new String[]{"-05:00", "SAPST", "SA Pacific Standard Time"},
                    new String[]{"-04:00", "SAWST", "SA Western Standard Time"},
                    new String[]{"+07:00", "SEA", "SE Asia Standard Time"},
                    new String[]{"+05:30", "SLST", "Sri Lanka Standard Time"},
                    new String[]{"-11:00", "SMO", "Samoa Time", "Samoa Standard Time"},
                    new String[]{"-06:00", "SSK", "Saskatchewan Time", "Canada Central Standard Time"},
                    new String[]{"+08:00", "SST", "Singapore Standard Time"},
                    new String[]{"+13:00", "TON", "Tonga Standard Time"},
                    new String[]{"+10:00", "TSM", "Tasmania Standard Time"},
                    new String[]{"+08:00", "TWT", "Taiwan Time", "Taipei Standard Time"},
                    new String[]{"-05:00", "USEST", "US Eastern Standard Time"},
                    new String[]{"-07:00", "USMST", "US Mountain Standard Time"},
                    new String[]{"-04:30", "VNZ", "Venezuela Time", "Venezuela Time Zone"},
                    new String[]{"+10:00", "VVS", "Vladivostok Standard Time"},
                    new String[]{"+05:00", "WAT", "West Asia Standard Time"},
                    new String[]{"+01:00", "WCAST", "Western Central Africa Standard Time", "W. Central Africa Standard Time"},
                    new String[]{"+01:00", "WET", "Western European Time", "W. Europe Standard Time"},
                    new String[]{"+10:00", "WPT", "West Pacific Standard Time"},
                    new String[]{"+09:00", "YAKT", "Yakutsk Standard Time"}
            };
        }
        return zoneMappings;
    }

    private String zoneOffsetOfName(String name) {
        if (name == null)
            return null;

        for (String[] zones : getZoneMappings()) {
            for (int i = 1, len = zones.length; i < len; i++) {
                if (name.equalsIgnoreCase(zones[i]))
                    return zones[0];
            }
        }

        return null;
    }

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
            if (zoneString != null) {
                TimeZone timeZone = ZoneInfoFile.getZoneInfo(zoneString);
                if (timeZone != null)
                    return timeZone;
            }

            String zoneOffset = zoneOffsetOfName(zone);
            if (zoneOffset != null) {
                if ("GMT".equals(zoneOffset))
                    return TimeZone.getTimeZone(ZoneOffset.UTC);
                else
                    return TimeZone.getTimeZone(ZoneOffset.of(zoneOffset));
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
