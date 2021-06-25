package com.joutvhu.date.parser.domain;

import lombok.Getter;
import lombok.Setter;

import java.text.ParsePosition;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Getter
@Setter
public class StringSource extends ParsePosition {
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
    }

    public int getLength() {
        return source.length();
    }

    public StringSource next() {
        int index = this.getIndex();
        if (index < source.length()) {
            this.setIndex(index + 1);
            return this;
        } else
            return null;
    }

    public PositionBackup backup() {
        return new PositionBackup(this);
    }

    public Character character() {
        int index = this.getIndex();
        if (index < source.length())
            return this.source.charAt(index);
        else
            return null;
    }

    public String get(int length) {
        int index = this.getIndex();
        if (index + length < source.length()) {
            this.setIndex(index + length);
            return this.source.substring(index, index + length);
        } else {
            this.setIndex(source.length());
            return this.source.substring(index);
        }
    }

    public Iterator<String> iterator(int from) {
        return iterator(from, this.getLength() - this.getIndex());
    }

    public Iterator<String> iterator(int from, int to) {
        return new Iterator<String>() {
            private final StringSource source = StringSource.this;
            private final int currentIndex = StringSource.this.getIndex();

            private String value;
            private Integer relativeIndex;

            @Override
            public boolean hasNext() {
                if (relativeIndex == null)
                    return currentIndex < source.getLength();
                else
                    return relativeIndex < to && currentIndex + relativeIndex < source.getLength();
            }

            @Override
            public String next() {
                if (relativeIndex == null) {
                    if (currentIndex >= source.getLength())
                        throw new NoSuchElementException();
                    else if (currentIndex + from <= source.getLength()) {
                        relativeIndex = from;
                        int end = currentIndex + relativeIndex;
                        source.setIndex(end);
                        value = source.getSource().substring(currentIndex, end);
                    } else {
                        relativeIndex = source.getLength() - currentIndex;
                        source.setIndex(source.getLength());
                        value = source.getSource().substring(currentIndex, source.getLength());
                    }
                } else {
                    if (relativeIndex < to && currentIndex + relativeIndex < source.getLength()) {
                        relativeIndex++;
                        int last = currentIndex + relativeIndex;
                        source.setIndex(last);
                        value += source.getSource().charAt(last - 1);
                    } else
                        throw new NoSuchElementException();
                }
                return value;
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
