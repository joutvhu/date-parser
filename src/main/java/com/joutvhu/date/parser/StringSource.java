package com.joutvhu.date.parser;

import lombok.Getter;
import lombok.Setter;

import java.text.ParsePosition;

@Getter
@Setter
public class StringSource {
    private int length;
    private String source;
    private ParsePosition position;

    public StringSource(String source) {
        this(source, new ParsePosition(0));
    }

    public StringSource(String source, ParsePosition position) {
        if (source == null)
            throw new NullPointerException("String of date must not be null.");

        this.source = source;
        if (position == null)
            this.position = new ParsePosition(0);
        else
            this.position = position;
        this.length = source.length();
    }

    public StringSource next() {
        int index = this.position.getIndex() + 1;
        if (index < this.length) {
            this.position.setIndex(index);
            return this;
        } else
            return null;
    }

    public Character character() {
        int index = this.position.getIndex();
        if (index < this.length)
            return this.source.charAt(index);
        else
            return null;
    }
}
