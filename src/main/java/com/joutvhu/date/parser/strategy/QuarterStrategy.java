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

    private boolean ordinal;

    public QuarterStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c, c == 'o');
    }

    @Override
    public void afterPatternSet() {
        this.ordinal = this.pattern.endsWith("o");
    }

    @Override
    public void parse(ObjectiveDate objective, StringSource source, NextStrategy chain) {
        ParseBackup backup = ParseBackup.backup(objective, source);
        String value = source.get(this.ordinal ? 3 : 1);

        if (this.ordinal) {
            if (CommonUtil.hasOrdinal(value))
                value = value.substring(0, value.length() - 2);
            else {
                backup.restore();
                throw new MismatchPatternException(
                        "The quarter '" + value + "' must be end with an ordinal indicator.",
                        backup.getBackupPosition(),
                        this.pattern);
            }
        }

        if (CommonUtil.isNumber(value)) {
            try {
                int quarter = Integer.parseInt(value);
                if (quarter < 1 || quarter > 4) {
                    throw new MismatchPatternException(
                            "The '" + quarter + "' is not a quarter.",
                            backup.getBackupPosition(),
                            this.pattern);
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
        Objects.requireNonNull(objective.getMonth());
        int quarter = (objective.getMonth() - 1) / 3 + 1;
        target.append(quarter);
        if (this.ordinal)
            target.append(CommonUtil.getOrdinal(quarter));
        chain.next();
    }
}
