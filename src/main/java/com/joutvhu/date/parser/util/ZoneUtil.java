package com.joutvhu.date.parser.util;

import lombok.experimental.UtilityClass;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

@UtilityClass
public class ZoneUtil {
    public final Map<String, List<String>> ZONE_NAMES = new HashMap<>(64);

    static {
        putNames("ACDT:+09:30", "ACDT", "Australian Central Daylight Time", "Cen. Australia Standard Time", "Adelaide");
        putNames("ACST:+09:30", "ACST", "Australian Central Standard Time", "AUS Central Standard Time", "Darwin");
        putNames("AEDT:+10:00", "AEDT", "Australian Eastern Daylight Time", "AUS Eastern Standard Time", "Canberra", "Melbourne", "Sydney");
        putNames("AEST:+10:00", "AEST", "Australian Eastern Standard Time", "E. Australia Standard Time", "Brisbane");
        putNames("AFT:+04:30", "AFT", "Afghanistan Time", "Afghanistan Standard Time", "Kabul");
        putNames("AKST:-09:00", "AKST", "Alaska Standard Time", "Alaskan Standard Time", "Alaska");
        putNames("AMST:+04:00", "AMST", "Armenia Standard Time", "Armenian Standard Time", "Yerevan");
        putNames("ART:-03:00", "ART", "Argentina Standard Time", "Argentina Standard Time", "Buenos Aires");
        putNames("AST:-04:00", "AST", "Atlantic Standard Time", "Atlantic Standard Time", "Atlantic Time", "Atlantic Time (Canada)");
        putNames("ARAB:+03:00", "ARAB", "Arab Standard Time", "Arab Standard Time", "Kuwait", "Riyadh");
        putNames("ARABIA:+04:00", "ARABIA", "Arabian Standard Time", "Arabian Standard Time", "Abu Dhabi", "Muscat");
        putNames("ARABIC:+03:00", "ARABIC", "Arabic Standard Time", "Arabic Standard Time", "Baghdad");
        putNames("AWST:+08:00", "AWST", "Australian Western Standard Time", "W. Australia Standard Time", "Perth");
        putNames("AZOST:-01:00", "AZOST", "Azores Standard Time", "Azores Standard Time", "Azores");
        putNames("AZT:+04:00", "AZT", "Azerbaijan Time", "Azerbaijan Standard Time", "Baku");
        putNames("CAST:-06:00", "CAST", "Central America Standard Time", "Central America Standard Time", "Central America");
        putNames("CAT:+06:00", "CAT", "Central Asia Time", "Central Asia Standard Time", "Astana", "Dhaka");
        putNames("CBST:-04:00", "CBST", "Central Brazilian Time", "Central Brazilian Standard Time", "Manaus");
        putNames("CET:+01:00", "CET", "Central Europe Time", "Central Europe Standard Time", "Belgrade", "Bratislava", "Budapest", "Ljubljana", "Prague");
        putNames("CEST:+01:00", "CEST", "Central European Standard Time", "Central European Standard Time", "Sarajevo", "Skopje", "Warsaw", "Zagreb");
        putNames("CCS:+04:00", "CCS", "Caucasus Time", "Caucasus Standard Time", "Caucasus Standard Time");
        putNames("CGT:-03:00", "CGT", "Greenland Standard Time", "Greenland Standard Time", "Greenland");
        putNames("CHN:+08:00", "CHN", "China Standard Time", "China Standard Time", "Beijing", "Chongqing", "Hong Kong", "Urumqi");
        putNames("CPST:+11:00", "CPST", "Central Pacific Standard Time", "Central Pacific Standard Time", "Magadan", "Solomon Is.", "New Caledonia");
        putNames("CST:-06:00", "CST", "Central Standard Time", "Central Standard Time", "Central Time (US & Canada)");
        putNames("CSTM:-06:00", "CSTM", "Central Standard Time (Mexico)", "Central Standard Time (Mexico)", "Guadalajara", "Mexico City", "Monterrey - New");
        putNames("CVT:-01:00", "CVT", "Cape Verde Time", "Cape Verde Standard Time", "Cape Verde Is.");
        putNames("DST:-12:00", "DST", "Dateline Standard Time", "Dateline Standard Time", "International Date Line West");
        putNames("EAT:+03:00", "EAT", "East Africa Time", "E. Africa Standard Time", "Nairobi");
        putNames("EET:+02:00", "EET", "Eastern European Time", "E. Europe Standard Time", "Minsk");
        putNames("EGY:+02:00", "EGY", "Egypt Standard Time", "Egypt Standard Time", "Cairo");
        putNames("EKB:+05:00", "EKB", "Ekaterinburg Standard Time", "Ekaterinburg Standard Time", "Ekaterinburg");
        putNames("ESAST:-03:00", "ESAST", "South America Eastern Standard Time", "E. South America Standard Time", "Brasilia");
        putNames("EST:-05:00", "EST", "Eastern Standard Time", "Eastern Standard Time", "Eastern Time (US & Canada)");
        putNames("FJT:+12:00", "FJT", "Fiji Standard Time", "Fiji Standard Time", "Fiji", "Kamchatka", "Marshall Is.");
        putNames("FLE:+02:00", "FLE", "FLE Standard Time", "FLE Standard Time", "Helsinki", "Kyiv", "Riga", "Sofia", "Tallinn", "Vilnius");
        putNames("GET:+03:00", "GET", "Georgia Standard Time", "Georgian Standard Time", "Tbilisi");
        putNames("GMT:GMT", "GMT", "Greenwich Mean Time", "GMT Standard Time", "Dublin", "Edinburgh", "Lisbon", "London");
        putNames("GST:GMT", "GST", "Greenwich Standard Time", "Greenwich Standard Time", "Monrovia", "Reykjavik");
        putNames("GTB:+02:00", "GTB", "GTB Standard Time", "GTB Standard Time", "Athens", "Bucharest", "Istanbul");
        putNames("HAST:-10:00", "HAST", "Hawaii-Aleutian Standard Time", "Hawaiian Standard Time", "Hawaii");
        putNames("IRST:+03:30", "IRST", "Iran Standard Time", "Iran Standard Time", "Tehran");
        putNames("IST:+05:30", "IST", "India Standard Time", "India Standard Time", "Chennai", "Kolkata", "Mumbai", "New Delhi");
        putNames("ISRAEL:+02:00", "ISRAEL", "Israel Standard Time", "Israel Standard Time", "Jerusalem");
        putNames("JRD:+02:00", "JRD", "Jordan Standard Time", "Jordan Standard Time", "Amman");
        putNames("JST:+09:00", "JST", "Japan Standard Time", "Tokyo Standard Time", "Osaka", "Sapporo", "Tokyo");
        putNames("KST:+09:00", "KST", "Korea Standard Time", "Korea Standard Time", "Seoul");
        putNames("MAST:-02:00", "MAST", "Mid-Atlantic Standard Time", "Mid-Atlantic Standard Time", "Mid-Atlantic");
        putNames("MEST:+02:00", "MEST", "Middle East Standard Time", "Middle East Standard Time", "Beirut");
        putNames("MMT:+06:30", "MMT", "Myanmar Standard Time", "Myanmar Standard Time", "Yangon (Rangoon)");
        putNames("MRC:GMT", "MRC", "Morocco Standard Time", "Morocco Standard Time", "Casablanca");
        putNames("MSK:+03:00", "MSK", "Moscow Standard Time", "Russian Standard Time", "Moscow", "St. Petersburg", "Volgograd");
        putNames("MST:-07:00", "MST", "Mountain Standard Time", "Mountain Standard Time", "Mountain Time (US & Canada)");
        putNames("MSTM:-07:00", "MSTM", "Mountain Standard Time (Mexico)", "Mountain Standard Time (Mexico)", "Chihuahua", "La Paz", "Mazatlan - New");
        putNames("MUT:+04:00", "MUT", "Mauritius Time", "Mauritius Standard Time", "Port Louis");
        putNames("MVD:-03:00", "MVD", "Montevideo Standard Time", "Montevideo Standard Time", "Montevideo");
        putNames("NAEST:+08:00", "NAEST", "North Asia East Standard Time", "North Asia East Standard Time", "Irkutsk", "Ulaan Bataar");
        putNames("NAST:+07:00", "NAST", "North Asia Standard Time", "North Asia Standard Time", "Krasnoyarsk");
        putNames("NCAST:+06:00", "NCAST", "North Central Asia Standard Time", "North Central Asia Standard Time", "Almaty", "Novosibirsk");
        putNames("NLT:-03:30", "NLT", "Newfoundland Time", "Newfoundland Standard Time", "Newfoundland");
        putNames("NMT:+02:00", "NMT", "Namibia Time", "Namibia Standard Time", "Windhoek");
        putNames("NPT:+05:45", "NPT", "Nepal Time", "Nepal Standard Time", "Kathmandu");
        putNames("NZST:+12:00", "NZST", "New Zealand Standard Time", "New Zealand Standard Time", "Auckland", "Wellington");
        putNames("PSA:-04:00", "PSA", "Pacific South America Time", "Pacific SA Standard Time", "Santiago");
        putNames("PST:-08:00", "PST", "Pacific Standard Time", "Pacific Standard Time", "Pacific Time (US & Canada)");
        putNames("PSTM:-08:00", "PSTM", "Pacific Standard Time (Mexico)", "Pacific Standard Time (Mexico)", "Tijuana", "Baja California");
        putNames("PAK:+05:00", "PAK", "Pakistan Standard Time", "Pakistan Standard Time", "Islamabad", "Karachi");
        putNames("ROM:+01:00", "ROM", "Romance Standard Time", "Romance Standard Time", "Brussels", "Copenhagen", "Madrid", "Paris");
        putNames("SAEST:-03:00", "SAEST", "SA Eastern Standard Time", "SA Eastern Standard Time", "Georgetown");
        putNames("SAPST:-05:00", "SAPST", "SA Pacific Standard Time", "SA Pacific Standard Time", "Bogota", "Lima", "Quito", "Rio Branco");
        putNames("SAST:+02:00", "SAST", "South Africa Standard Time", "South Africa Standard Time", "Harare", "Pretoria");
        putNames("SAWST:-04:00", "SAWST", "SA Western Standard Time", "SA Western Standard Time", "La Paz");
        putNames("SEA:+07:00", "SEA", "SE Asia Standard Time", "SE Asia Standard Time", "Bangkok", "Hanoi", "Jakarta");
        putNames("SLST:+05:30", "SLST", "Sri Lanka Standard Time", "Sri Lanka Standard Time", "Sri Jayawardenepura");
        putNames("SMO:-11:00", "SMO", "Samoa Time", "Samoa Standard Time", "Midway Island", "Samoa");
        putNames("SSK:-06:00", "SSK", "Saskatchewan Time", "Canada Central Standard Time", "Saskatchewan");
        putNames("SST:+08:00", "SST", "Singapore Standard Time", "Singapore Standard Time", "Kuala Lumpur", "Singapore");
        putNames("TON:+13:00", "TON", "Tonga Standard Time", "Tonga Standard Time", "Nuku'alofa");
        putNames("TSM:+10:00", "TSM", "Tasmania Standard Time", "Tasmania Standard Time", "Hobart");
        putNames("TWT:+08:00", "TWT", "Taiwan Time", "Taipei Standard Time", "Taipei");
        putNames("USEST:-05:00", "USEST", "US Eastern Standard Time", "US Eastern Standard Time", "Indiana (East)");
        putNames("USMST:-07:00", "USMST", "US Mountain Standard Time", "US Mountain Standard Time", "Arizona");
        putNames("UTC:GMT", "UTC", "Coordinated Universal Time");
        putNames("VNZ:-04:30", "VNZ", "Venezuela Time", "Venezuela Time Zone", "Caracas");
        putNames("VVS:+10:00", "VVS", "Vladivostok Standard Time", "Vladivostok Standard Time", "Vladivostok");
        putNames("WAT:+05:00", "WAT", "West Asia Standard Time", "West Asia Standard Time", "Tashkent");
        putNames("WCAST:+01:00", "WCAST", "Western Central Africa Standard Time", "W. Central Africa Standard Time", "West Central Africa");
        putNames("WET:+01:00", "WET", "Western European Time", "W. Europe Standard Time", "Amsterdam", "Berlin", "Bern", "Rome", "Stockholm", "Vienna");
        putNames("WPT:+10:00", "WPT", "West Pacific Standard Time", "West Pacific Standard Time", "Guam", "Port Moresby");
        putNames("YAKT:+09:00", "YAKT", "Yakutsk Standard Time", "Yakutsk Standard Time", "Yakutsk");
    }

    private void putNames(String key, String... values) {
        ZONE_NAMES.put(key, Arrays.asList(values));
    }

    private String getZoneAbbreviation(String zoneName) {
        return ZONE_NAMES.entrySet()
                .stream()
                .filter(entry -> CommonUtil.indexIgnoreCaseOf(zoneName, entry.getValue()) != -1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    private ZoneId zoneIdByName(String zoneName) {
        String zoneAbbreviation = getZoneAbbreviation(zoneName);
        if (zoneAbbreviation == null)
            return null;

        String[] zoneString = zoneAbbreviation.split(":");
        if ("GMT".equals(zoneString[1]))
            return ZoneOffset.UTC;
        else
            return ZoneOffset.of(zoneString[1]);
    }

    public ZoneId zoneIdOf(String zoneId) {
        Objects.requireNonNull(zoneId, "Zone ID must be not null.");

        if (zoneId.equalsIgnoreCase("Z"))
            return ZoneOffset.UTC;
        else if (zoneId.length() <= 1 || zoneId.startsWith("+") || zoneId.startsWith("-"))
            return ZoneOffset.of(zoneId);
        else if (!zoneId.startsWith("UTC") && !zoneId.startsWith("GMT") && !zoneId.startsWith("UT")) {
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

        ZoneId zone = zoneIdByName(zoneId);
        if (zone != null)
            return zone;
        else
            return ZoneId.of(zoneId);
    }

    public TimeZone getTimeZone(String zone) {
        try {
            ZoneId zoneId = zoneIdOf(zone);
            return zoneId != null ? TimeZone.getTimeZone(zoneId) : null;
        } catch (Exception e) {
            return null;
        }
    }
}
