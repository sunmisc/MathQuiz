package me.astoriamine.quiz.text;

import java.util.Objects;

public class TextEnvelope implements Text {
    private final Text origin;

    public TextEnvelope(Text origin) {
        this.origin = origin;
    }


    @Override
    public String asString() {
        return origin.asString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextEnvelope that = (TextEnvelope) o;
        return Objects.equals(origin, that.origin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin);
    }

    @Override
    public String toString() {
        return origin.toString();
    }
}
