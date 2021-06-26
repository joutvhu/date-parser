package com.joutvhu.date.parser.domain;

import lombok.Getter;

@Getter
public class ParseBackup implements Backup<Object> {
    private DateBuilder.DateBackup dateBackup;
    private StringSource.PositionBackup positionBackup;

    public ParseBackup(DateBuilder builder, StringSource position) {
        this.dateBackup = builder.backup();
        this.positionBackup = position.backup();
    }

    public static ParseBackup backup(DateBuilder builder, StringSource position) {
        return new ParseBackup(builder, position);
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
