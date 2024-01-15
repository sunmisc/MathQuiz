package me.astoriamine.quiz.math.nodes;

import me.astoriamine.quiz.nodes.Node;
import me.astoriamine.quiz.nodes.NodeEnvelope;
import me.astoriamine.quiz.text.Text;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class MultiplicationNode extends NodeEnvelope<Double> {

    @SafeVarargs
    public MultiplicationNode(Node<Double>... src) {
        this(List.of(src));
    }


    public MultiplicationNode(Collection<Node<Double>> src) {
        super(new Node<>() {
            @Override
            public Double evaluate() {
                return src.stream()
                        .unordered()
                        .mapToDouble(Node::evaluate)
                        .reduce(1, (a, b) -> a * b);
            }
            @Override
            public String asString() {
                return src.stream()
                        .map(Text::asString)
                        .collect(Collectors.joining(
                                " * ", "(", ")"));
            }
        });
    }
}
