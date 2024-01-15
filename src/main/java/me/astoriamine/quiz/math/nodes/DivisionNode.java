package me.astoriamine.quiz.math.nodes;

import me.astoriamine.quiz.nodes.Node;
import me.astoriamine.quiz.nodes.NodeEnvelope;
import me.astoriamine.quiz.text.Text;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class DivisionNode extends NodeEnvelope<Double> {

    @SafeVarargs
    public DivisionNode(Node<Double>... src) {
        this(List.of(src));

    }
    public DivisionNode(Collection<Node<Double>> src) {
        super(new Node<>() {
            @Override
            public Double evaluate() {
                return src.stream()
                        .mapToDouble(Node::evaluate)
                        .reduce((a, b) -> a / b)
                        .orElseThrow();
            }
            @Override
            public String asString() {
                return src.stream()
                        .map(Text::asString)
                        .collect(Collectors.joining(
                                " / ", "(", ")"));
            }
        });
    }
}
