package me.astoriamine.quiz.nodes;

public class ValueNode<T> implements Node<T> {

    private final T value;

    public ValueNode(T value) {
        this.value = value;
    }

    @Override
    public T evaluate() {
        return value;
    }

    @Override
    public String asString() {
        return String.valueOf(value);
    }
}
