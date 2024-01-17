package me.astoriamine.quiz;

import me.astoriamine.quiz.math.nodes.TruncateNode;
import me.astoriamine.quiz.nodes.ValueNode;
import me.astoriamine.quiz.text.FormattedText;
import me.astoriamine.quiz.util.Lazy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends JavaPlugin {
    private static final int PRICE = 1000;
    private static final int DECIMAL_POINTS = 1;


    private final Lazy<Economy> economy = new Lazy<>(
            () -> {
                Server server = getServer();
                if (server.getPluginManager().getPlugin("Vault") == null)
                    throw new IllegalStateException("Vault plugin not found");
                RegisteredServiceProvider<Economy> rsp = server
                        .getServicesManager()
                        .getRegistration(Economy.class);
                if (rsp == null)
                    throw new IllegalStateException("Economy implementation not found");
                return rsp.getProvider();
            }
    );
    private final AtomicReference<String> solver =
            new AtomicReference<>();
    private final RiddleBroadcast broadcast =
            new RiddleBroadcast(this, expr -> {

                solver.setRelease(null); // clear

                Bukkit.broadcastMessage(
                        new FormattedText(
                                () -> "§cЧат-игра: §7Реши пример: §b%s §7и получи §f%s$",
                                expr.asString(), PRICE
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
                        new Listener() { },
                        // low priority is chosen for optimization purposes,
                        // because we process the event without ignoring cancellation,
                        // but while canceling is possible,
                        // it should be assumed that higher priority events with
                        // ignoring canceled events will not be executed
                        EventPriority.LOWEST,
                        (listener, raw) -> {
                            if (raw instanceof AsyncPlayerChatEvent event)
                                handleChat(event);
                        }, this);
    }

    private void handleChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        try {
            Double value = new TruncateNode(
                    new ValueNode<>(
                            Double.parseDouble(event.getMessage())),
                    DECIMAL_POINTS + 1
            ).evaluate();

            if (value.equals(broadcast.evaluate()) &&
                    solver.compareAndSet(null, player.getName())) {

                getServer().broadcastMessage(String.format(
                        "§cЧат-игра: §7Игрок §b%s §7решил пример!",
                        player.getDisplayName()));
                economy.get()
                        .depositPlayer(player, PRICE);
                event.setCancelled(true);
            }
        } catch (NumberFormatException ignored) {}
    }
}
