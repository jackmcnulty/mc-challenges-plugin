package jackm.dev.mc.listeners;

import jackm.dev.mc.utils.ChallengeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EventListener implements Listener {

    private final ChallengeManager challengeManager;

    public EventListener(ChallengeManager challengeManager) {
        this.challengeManager = challengeManager;
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            challengeManager.checkCompletion(player);
        }
    }
}