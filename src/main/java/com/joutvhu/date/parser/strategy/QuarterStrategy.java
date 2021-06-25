package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;
import com.joutvhu.date.parser.util.CommonUtil;

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
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        String value = source.get(this.ordinal ? 3 : 1);

        if (this.ordinal) {
            if (CommonUtil.hasOrdinal(value))
                value = value.substring(0, value.length() - 2);
            else {
                backup.restore();
                throw new MismatchPatternException("The \"" + value + "\" of quarter must be end with an ordinal.", backup.getBackup(), this.pattern);
            }
        }

        if (CommonUtil.isNumber(value)) {
            try {
                int quarter = Integer.parseInt(value);
                if (quarter < 1 || quarter > 4)
                    throw new MismatchPatternException("The \"" + quarter + "\" is not a quarter.", backup.getBackup(), this.pattern);
                this.nextStrategy(chain);
                builder.put(QUARTER, quarter);
            } catch (Exception e) {
                backup.restore();
                throw e;
            }
        }
    }
}
