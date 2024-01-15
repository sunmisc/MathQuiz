package me.astoriamine.quiz.nodes;

import java.util.concurrent.atomic.AtomicReference;

public class CachedValueNode<T> implements Node<T> {

    private final Node<T> origin;
    private final AtomicReference<Pair<T>> pair
            = new AtomicReference<>();

    public CachedValueNode(Node<T> origin) {
        this.origin = origin;
    }

    private Pair<T> get() {
        return pair.updateAndGet(prev -> prev == null
                ? new Pair<>(
                        origin.asString(), origin.evaluate())
                : prev);
    }

    @Override
    public T evaluate() {
        return get().value();
    }

    @Override
    public String asString() {
        return get().string();
    }
    private record Pair<T>(String string, T value) { }
}
