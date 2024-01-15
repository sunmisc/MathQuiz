package me.astoriamine.quiz;

import me.astoriamine.quiz.math.ExpressionTree;
import me.astoriamine.quiz.math.nodes.TruncateNode;
import me.astoriamine.quiz.nodes.CachedValueNode;
import me.astoriamine.quiz.nodes.InvalidNode;
import me.astoriamine.quiz.nodes.Node;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class RiddleBroadcast implements Node<Double>, Front {
    private static final int PERIOD = 20_000;
    private static final int DEPTH = 2;
    private static final int MAX_NUMBER = 100;
    private static final int DECIMAL_POINTS = 1;

    private final Plugin plugin;
    private final Back<Double> back;
    private volatile Node<Double> answer =
            new InvalidNode<>();

    public RiddleBroadcast(Plugin plugin, Back<Double> back) {
        this.plugin = plugin;
        this.back = back;
    }


    @Override
    public void start(Exit exit) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (exit.ready())
                    cancel();
                else {
                    ExpressionTree expression = new ExpressionTree(
                            DEPTH,
                            MAX_NUMBER,
                            DECIMAL_POINTS
                    );
                    var cachedValueNode = new CachedValueNode<>(
                            new TruncateNode(
                                    expression,
                                    DECIMAL_POINTS + 1
                            )
                    );
                    back.accept(expression);
                    answer = cachedValueNode;
                }
            }
        }.runTaskTimer(plugin, 0, PERIOD);
    }

    @Override
    public Double evaluate() {
        return answer.evaluate();
    }

    @Override
    public String asString() {
        return answer.asString();
    }
}
