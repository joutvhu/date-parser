package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchException;

public class AmPmStrategy extends Strategy {
    private boolean upperCase;

    public AmPmStrategy(char c) {
        super(c);
        this.upperCase = c == 'A';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        String value = source.get(2);

        try {
            if ("am".equalsIgnoreCase(value)) {
                this.nextStrategy(chain);
                // TODO save am
                return;
            } else if ("pm".equalsIgnoreCase(value)) {
                this.nextStrategy(chain);
                // TODO save pm
                return;
            }
        } catch (MismatchException e) {
            backup.restore();
            throw e;
        }

        backup.restore();
        throw new MismatchException("The \"" + value + "\" must be \"AM\" or \"PM\".", backup.getBackup(), this.pattern);
    }
}
