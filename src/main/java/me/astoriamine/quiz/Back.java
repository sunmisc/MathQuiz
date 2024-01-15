package me.astoriamine.quiz;


import me.astoriamine.quiz.nodes.Node;

@FunctionalInterface
public interface Back<T> {

    void accept(Node<T> expr);
}
