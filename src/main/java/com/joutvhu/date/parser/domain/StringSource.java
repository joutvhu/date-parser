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

    public Iterator<String> iterator(int from, int to) {
        final int currentIndex = this.getIndex();
        final AtomicReference<String> value = new AtomicReference<>();
        final AtomicReference<Integer> relativeIndex = new AtomicReference<>();

        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                Integer i = relativeIndex.get();
                return i == null || (i < to && (currentIndex + i) < StringSource.this.length);
            }

            @Override
            public String next() {
                Integer i = relativeIndex.get();
                if (relativeIndex.get() == null) {
                    relativeIndex.set(from);
                    value.set(StringSource.this.get(from));
                } else {
                    i++;
                    StringSource.this.setIndex(currentIndex + i);
                    value.set(value.get() + StringSource.this.source.charAt(currentIndex + i));
                }
                return value.get();
            }
        };
    }

    @Getter
    public class PositionBackup {
        private final int backup;
        private final StringSource position;

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
