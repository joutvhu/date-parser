package com.joutvhu.date.parser.domain;

public interface Backup<T> {
    T restore();

    void commit();
}
