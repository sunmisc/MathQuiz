package me.astoriamine.quiz;

import me.astoriamine.quiz.math.nodes.TruncateNode;
import me.astoriamine.quiz.nodes.ValueNode;
import me.astoriamine.quiz.text.FormattedText;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends JavaPlugin {

    private static final int DECIMAL_POINTS = 1;

    private final Set<String> solvers =
            ConcurrentHashMap.newKeySet();

    private final RiddleBroadcast broadcast =
            new RiddleBroadcast(this, expr -> {

                solvers.clear();

                Bukkit.broadcastMessage(
                        new FormattedText(
                                () -> "[Quiz] Solve this: %s",
                                expr.asString()
                        ).asString()
                );
            });

    @Override
    public void onEnable() {
        broadcast.start(() -> !isEnabled());

        getServer()
                .getPluginManager()
                .registerEvent(
                        AsyncPlayerChatEvent.class,
                        new Listener() {},
                        EventPriority.NORMAL,
                        (listener, raw) -> {
                            if (raw instanceof AsyncPlayerChatEvent event) {
                                try {
                                    Player player = event.getPlayer();

                                    Double value = new TruncateNode(
                                            new ValueNode<>(
                                                    Double.parseDouble(event.getMessage())),
                                            DECIMAL_POINTS + 1
                                    ).evaluate();

                                    if (value.equals(broadcast.evaluate()) &&
                                            solvers.add(player.getName())) {
                                        player.sendMessage("Your answer is correct!");
                                        event.setCancelled(true);
                                    }
                                } catch (NumberFormatException ignored) {}
                            }}, this);
    }
}
