package me.astoriamine.quiz.util;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class Lazy<T> implements Supplier<T> {

    private static final Object EMPTY = new Object();

    private final ReentrantLock lock =
            new ReentrantLock();
    private Supplier<T> origin;
    private volatile T value = (T) EMPTY;

    public Lazy(Supplier<T> origin) {
        this.origin = origin;
    }

    @Override
    public T get() {
        T val = value;
        if (val == EMPTY) {
            lock.lock();
            try {
                // non-volatile read here
                if ((val = value) == EMPTY) {
                    val = value = origin.get();
                    origin = null; // to reduce footprint
                }
            } finally {
                lock.unlock();
            }
        }
        return val;
    }
}
