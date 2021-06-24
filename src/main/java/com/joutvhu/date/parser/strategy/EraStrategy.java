package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchException;

public class EraStrategy extends Strategy {
    public EraStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'G', c);
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        String value = source.get(2);

        try {
            if ("AD".equalsIgnoreCase(value)) {
                this.nextStrategy(chain);
                // TODO save AD
                return;
            } else if ("BC".equalsIgnoreCase(value)) {
                this.nextStrategy(chain);
                // TODO save BC
                return;
            }
        } catch (MismatchException e) {
            backup.restore();
            throw e;
        }

        backup.restore();
        throw new MismatchException("The \"" + value + "\" must be \"AD\" or \"BC\".", backup.getBackup(), this.pattern);
    }
}
