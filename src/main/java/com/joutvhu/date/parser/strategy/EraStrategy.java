package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchPatternException;

public class EraStrategy extends Strategy {
    public static final String AD = "AD";
    public static final String BC = "BC";
    public static final String ERA = "era";

    public EraStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'G', c);
    }

    @Override
    public void parse(DateBuilder builder, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        String value = source.get(2);

        try {
            if ("AD".equalsIgnoreCase(value)) {
                this.nextStrategy(chain);
                builder.set(ERA, AD);
                return;
            } else if ("BC".equalsIgnoreCase(value)) {
                this.nextStrategy(chain);
                builder.set(ERA, BC);
                return;
            }
        } catch (Exception e) {
            backup.restore();
            throw e;
        }

        backup.restore();
        throw new MismatchPatternException("The \"" + value + "\" must be \"AD\" or \"BC\".", backup.getBackup(), this.pattern);
    }
}
