package me.astoriamine.quiz.nodes;

import me.astoriamine.quiz.text.Text;

public interface Node<T> extends Text {

    T evaluate();

}
