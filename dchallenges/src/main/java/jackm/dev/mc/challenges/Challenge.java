package jackm.dev.mc.challenges;

import org.bukkit.entity.Player;
import org.bukkit.Location;

public interface Challenge {
    void start();
    boolean isCompleted(Player player);
    void stop(Player winner);
    void setEndLocation(Location location);
    String getDescription();
}
