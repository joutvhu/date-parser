package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.ZoneUtil;

import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class ZoneStrategy extends Strategy {
    private char type;

    public ZoneStrategy(char c) {
        super(c);
        this.type = c;
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        Iterator<String> iterator = source.iterator(this.length());

        while (iterator.hasNext()) {
            String value = iterator.next();

            if (value.startsWith(" ")) {
                backup.restore();
                throw new MismatchPatternException(
                        "The time zone can't start with a space.",
                        backup.getBackupPosition(),
                        this.pattern);
            }

            TimeZone timeZone = ZoneUtil.getTimeZone(value, objective.getLocale());
            if (timeZone != null) {
                try {
                    chain.next();
                    objective.set(ObjectiveDate.ZONE, timeZone);
                    backup.commit();
                    return;
                } catch (Exception e) {
                    if (!iterator.hasNext()) {
                        backup.restore();
                        throw e;
                    }
                }
            }
        }

        backup.restore();
        throw new MismatchPatternException(
                "The time zone is invalid.",
                backup.getBackupPosition(),
                this.pattern);
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        Objects.requireNonNull(objective.getZone(), "Zone is null.");
        int count = this.length();
        int offset = objective.getZone().getRawOffset();
        switch (this.type) {
            case 'V':
                target.append(objective.getZone().getID());
                break;
            case 'z':
                target.append(objective.getZone().getDisplayName(
                        false,
                        count < 4 ? TimeZone.SHORT : TimeZone.LONG,
                        objective.getLocale() == null ? Locale.ROOT : objective.getLocale()
                ));
                break;
            case 'O':
                if (count < 4)
                    target.append(printLocalizedOffset(offset, TimeZone.SHORT));
                else
                    target.append(printLocalizedOffset(offset, TimeZone.LONG));
                break;
            case 'X':
                if (offset == 0)
                    target.append("Z");
                else
                    target.append(printOffset(offset, count));
                break;
            case 'x':
                if (offset == 0)
                    target.append(count == 1 ? "+00" : (count % 2 == 0 ? "+0000" : "+00:00"));
                else
                    target.append(printOffset(offset, count));
                break;
            default:
                if (count < 4)
                    target.append(offset == 0 ? "+0000" : printOffset(offset, count));
                else if (count == 4)
                    target.append(printLocalizedOffset(offset, TimeZone.LONG));
                else
                    target.append(offset == 0 ? "Z" : printOffset(offset, count));
                break;
        }
        chain.next();
    }

    private static StringBuilder appendHMS(StringBuilder buf, int t, int style) {
        if (style == TimeZone.LONG || t >= 10)
            buf.append((char) (t / 10 + '0'));
        return buf.append((char) (t % 10 + '0'));
    }

    private static String printOffset(int offset, int count) {
        StringBuilder buf = new StringBuilder(offset < 0 ? "-" : "+");

        offset = Math.abs(offset / 1000);
        int absHours = (offset / 3600) % 100;
        int absMinutes = (offset / 60) % 60;
        int absSeconds = offset % 60;

        appendHMS(buf, absHours, TimeZone.LONG);
        if (count > 2 && (count & 1) != 0)
            buf.append(':');
        appendHMS(buf, absMinutes, TimeZone.LONG);
        if (count > 3 || absSeconds != 0) {
            if (count > 2 && (count & 1) != 0)
                buf.append(':');
            appendHMS(buf, absSeconds, TimeZone.LONG);
        }

        return buf.toString();
    }

    private static String printLocalizedOffset(int offset, int style) {
        StringBuilder buf = new StringBuilder("GMT");
        buf.append(offset < 0 ? "-" : "+");

        offset = Math.abs(offset / 1000);
        int absHours = (offset / 3600) % 100;
        int absMinutes = (offset / 60) % 60;
        int absSeconds = offset % 60;

        appendHMS(buf, absHours, style);
        if (style == TimeZone.LONG || absMinutes != 0 || absSeconds != 0) {
            buf.append(':');
            appendHMS(buf, absMinutes, TimeZone.LONG);
            if (absSeconds != 0) {
                buf.append(':');
                appendHMS(buf, absSeconds, TimeZone.LONG);
            }
        }

        return buf.toString();
    }
}
