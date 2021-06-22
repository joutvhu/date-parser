package com.joutvhu.date.parser.domain;

import lombok.Getter;
import lombok.Setter;

import java.text.ParsePosition;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
public class StringSource extends ParsePosition {
    private int length;
    private String source;
    private Integer backupPosition;

    public StringSource(String source) {
        this(source, 0);
    }

    public StringSource(String source, int position) {
        super(position);
        if (source == null)
            throw new IllegalArgumentException("String of date must not be null.");

        this.source = source;
        this.length = source.length();
    }

    public StringSource next() {
        int index = this.getIndex() + 1;
        if (index < this.length) {
            this.setIndex(index);
            return this;
        } else
            return null;
    }

    public PositionBackup backup() {
        return new PositionBackup(this);
    }

    public Character character() {
        int index = this.getIndex();
        if (index < this.length)
            return this.source.charAt(index);
        else
            return null;
    }

    public String get(int length) {
        int index = this.getIndex();
        if (index + length < this.length) {
            this.setIndex(index + length);
            return this.source.substring(index, index + length);
        } else {
            this.setIndex(this.length);
            return this.source.substring(index);
        }
    }

    public Iterator<String> get(int from, int to) {
        final AtomicReference<String> value = new AtomicReference<>();
        final AtomicReference<Integer> length = new AtomicReference<>();
        int index = this.getIndex();

        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                Integer i = length.get();
                return i == null || (i < to && (index + i) < StringSource.this.length);
            }

            @Override
            public String next() {
                Integer i = length.get();
                if (length.get() == null) {
                    length.set(from);
                    value.set(StringSource.this.get(from));
                } else {
                    i++;
                    StringSource.this.setIndex(index + i);
                    value.set(value.get() + StringSource.this.source.charAt(index + i));
                }
                return value.get();
            }
        };
    }

    public class PositionBackup {
        private int backup;
        private StringSource position;

        public PositionBackup(StringSource position) {
            this.position = position;
            this.backup = position.getIndex();
        }

        public StringSource restore() {
            this.position.setIndex(this.backup);
            return position;
        }
    }
}
