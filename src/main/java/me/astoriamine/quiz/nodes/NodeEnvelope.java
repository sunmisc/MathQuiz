package me.astoriamine.quiz.nodes;

import java.util.Objects;

public class NodeEnvelope<T> implements Node<T> {

    private final Node<T> origin;

    public NodeEnvelope(Node<T> origin) {
        this.origin = origin;
    }

    @Override
    public T evaluate() {
        return origin.evaluate();
    }

    @Override
    public String asString() {
        return origin.asString();
    }

    @Override
    public String toString() {
        return origin.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeEnvelope<?> that = (NodeEnvelope<?>) o;
        return Objects.equals(origin, that.origin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin);
    }
}
