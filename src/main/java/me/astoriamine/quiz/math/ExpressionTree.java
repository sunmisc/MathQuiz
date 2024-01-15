package me.astoriamine.quiz.math;

import me.astoriamine.quiz.math.nodes.*;
import me.astoriamine.quiz.nodes.Node;
import me.astoriamine.quiz.nodes.NodeEnvelope;
import me.astoriamine.quiz.nodes.ValueNode;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ExpressionTree extends NodeEnvelope<Double> {


    public ExpressionTree(int depth, int bound, int decimalPoints) {
        super(randomNode(depth, bound, decimalPoints));
    }

    private static Node<Double> randomNode(int depth, int bound, int decimalPoints) {
        Random random = ThreadLocalRandom.current();
        if (depth <= 0)
            return new TruncateNode(
                    new ValueNode<>(bound * random.nextDouble()),
                    decimalPoints
            );

        Node<Double> left = randomNode(depth - 1, bound, decimalPoints);
        Node<Double> right = randomNode(depth - 1, bound, decimalPoints);

        return switch (random.nextInt(4)) {
            case 0 -> new SumNode(left, right);
            case 1 -> new SubNode(left, right);
            case 2 -> new MultiplicationNode(left, right);
            case 3 -> new DivisionNode(left, right);
            default -> new TruncateNode(
                    new ValueNode<>(bound * random.nextDouble()),
                    decimalPoints
            );
        };
    }
}
