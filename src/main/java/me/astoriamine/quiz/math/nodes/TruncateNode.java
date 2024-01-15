package me.astoriamine.quiz.math.nodes;

import me.astoriamine.quiz.nodes.Node;

public class TruncateNode implements Node<Double> {

    private final Node<Double> n;
    private final int digits;

    public TruncateNode(Node<Double> n, int digits) {
        this.n = n;
        this.digits = digits;
    }

    public static void main(String[] args) {
        int i = 2;

        System.out.println(Math.pow(10, i));
        System.out.println(10 << i);
    }

    @Override
    public Double evaluate() {
        double scale = Math.pow(10, digits);

        return Math.round(n.evaluate() * scale) / scale;
    }

    @Override
    public String asString() {
        return String.valueOf(evaluate());
    }
    @Override
    public String toString() {
        return asString();
    }
}
