package com.joutvhu.date.parser.domain;

import lombok.Getter;

@Getter
public class ParseBackup implements Backup<Object> {
    private final ObjectiveDate.DateBackup dateBackup;
    private final StringSource.PositionBackup positionBackup;

    public ParseBackup(ObjectiveDate objective, StringSource position) {
        this.dateBackup = objective.backup();
        this.positionBackup = position.backup();
    }

    public static ParseBackup backup(ObjectiveDate objective, StringSource position) {
        return new ParseBackup(objective, position);
    }

    public int getBackupPosition() {
        return positionBackup.getBackup();
    }

    @Override
    public Object restore() {
        this.dateBackup.restore();
        this.positionBackup.restore();
        return null;
    }

    @Override
    public void commit() {
        this.dateBackup.commit();
        this.positionBackup.commit();
    }
}
