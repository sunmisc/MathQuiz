package me.astoriamine.quiz.nodes;

public final class InvalidNode<T> implements Node<T> {
    @Override
    public T evaluate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String asString() {
        throw new UnsupportedOperationException();
    }
}
