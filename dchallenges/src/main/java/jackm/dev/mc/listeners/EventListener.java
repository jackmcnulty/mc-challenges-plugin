package jackm.dev.mc.listeners;

import jackm.dev.mc.utils.ChallengeManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EventListener implements Listener {

    private final ChallengeManager challengeManager;
    private final JavaPlugin plugin;

    public EventListener(ChallengeManager challengeManager, JavaPlugin plugin) {
        this.challengeManager = challengeManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Bukkit.getScheduler().runTask(plugin, () -> challengeManager.checkCompletion(player));
        }
    }
}