package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchException;
import com.joutvhu.date.parser.util.CommonUtil;

public class QuarterStrategy extends Strategy {
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
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        String value = source.get(this.ordinal ? 3 : 1);

        if (this.ordinal) {
            if (CommonUtil.hasOrdinal(value))
                value = value.substring(0, value.length() - 2);
            else {
                backup.restore();
                throw new MismatchException("The \"" + value + "\" of quarter must be end with an ordinal.", backup.getBackup(), this.pattern);
            }
        }

        if (CommonUtil.isNumber(value)) {
            try {
                int quarter = Integer.parseInt(value);
                if (quarter < 1 || quarter > 4)
                    throw new MismatchException("The \"" + quarter + "\" is not a quarter.", backup.getBackup(), this.pattern);
                this.nextStrategy(chain);
                // TODO save quarter
            } catch (MismatchException e) {
                backup.restore();
                throw e;
            }
        }
    }
}
