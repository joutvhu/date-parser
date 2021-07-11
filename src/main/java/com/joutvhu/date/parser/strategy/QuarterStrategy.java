package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.ParseBackup;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.subscription.QuarterSubscription;
import com.joutvhu.date.parser.util.CommonUtil;

import java.util.Objects;

public class QuarterStrategy extends Strategy {
    public static final String QUARTER = "quarter";

    private boolean numeric;
    private boolean ordinal;
    private boolean shortText;
    private boolean longText;

    public QuarterStrategy(char c) {
        super(c);
        this.numeric = c == 'q';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c, c == 'o');
    }

    @Override
    public void afterPatternSet() {
        this.ordinal = this.pattern.endsWith("o");
        this.shortText = !this.numeric && this.length() == 3;
        this.longText = !this.numeric && this.length() == 4;
    }

    @Override
    public int length() {
        return this.ordinal ? super.length() - 1 : super.length();
    }

    @SuppressWarnings("java:S3776")
    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        String value = source.get(1);
        StringBuilder fullValue = new StringBuilder(value);

        if (!this.numeric && this.shortText) {
            value = source.get(1);
            fullValue.append(value);
        }

        if (CommonUtil.isNumber(value)) {
            try {
                int quarter = Integer.parseInt(value);
                if (quarter == 0 && this.numeric) {
                    for (int i = 1, len = this.length(); i < len; i++) {
                        Character c = source.character();
                        source.next();
                        if (c != null)
                            fullValue.append(c);

                        if (c == null || c < '0' || c > '4') {
                            throw new MismatchPatternException(
                                    "The '" + fullValue + "' is not a quarter.",
                                    backup.getBackupPosition(),
                                    this.pattern);
                        }
                        if (c != '0') {
                            quarter = c - '0';
                            break;
                        }
                    }
                }

                if (quarter < 1 || quarter > 4) {
                    throw new MismatchPatternException(
                            "The '" + quarter + "' is not a quarter.",
                            backup.getBackupPosition(),
                            this.pattern);
                }

                if (this.ordinal || this.longText) {
                    ParseBackup ordinalBackup = ParseBackup.backup(objective, source);
                    String o = source.get(2);
                    fullValue.append(o);
                    if (!CommonUtil.hasOrdinal(o)) {
                        if (this.longText)
                            ordinalBackup.restore();
                        else {
                            throw new MismatchPatternException(
                                    "The quarter '" + fullValue + "' must be end with an ordinal indicator.",
                                    backup.getBackupPosition(),
                                    this.pattern);
                        }
                    }
                }

                if (this.longText) {
                    ParseBackup quarterBackup = ParseBackup.backup(objective, source);
                    String q = source.get(8);
                    if (!" quarter".equalsIgnoreCase(q))
                        quarterBackup.restore();
                }

                chain.next();
                objective.subscribe(new QuarterSubscription());
                objective.set(QUARTER, quarter);
                backup.commit();
            } catch (Exception e) {
                backup.restore();
                throw e;
            }
        }
    }

    @Override
    public void format(ObjectiveDate objective, StringBuilder target, NextStrategy chain) {
        Integer quarter;
        if (objective.getMonth() != null) {
            quarter = (objective.getMonth() - 1) / 3 + 1;
        } else {
            quarter = objective.get(QUARTER);
        }

        Objects.requireNonNull(quarter, "Month is null.");
        if (this.shortText)
            target.append("Q");
        target.append(quarter);
        if (this.ordinal || this.longText)
            target.append(CommonUtil.getOrdinal(quarter));
        if (this.longText)
            target.append(" quarter");
        chain.next();
    }
}
