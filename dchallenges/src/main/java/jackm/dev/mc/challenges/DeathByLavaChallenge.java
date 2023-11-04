package jackm.dev.mc.challenges;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.entity.EntityDamageEvent;

import jackm.dev.mc.utils.RandomWorldLocation;

public class DeathByLavaChallenge implements Challenge {
    private Location endLocation;
    private long startTime;

    public DeathByLavaChallenge(Location endLocation) {
        this.endLocation = endLocation;
    }

    @Override
    public void start() {
        // Announce the challenge to all players
        Bukkit.broadcastMessage(ChatColor.RED + "Starting the lava death challenge! " + getDescription());

        // Store the start time for later tracking
        startTime = System.currentTimeMillis();

        World world = Bukkit.getWorld("world");
        Location randomLocation = RandomWorldLocation.getRandomLocation(world, -20000, 20000, -20000, 20000);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(randomLocation); // Teleport all players to the random location
        }
    }

    @Override
    public boolean isCompleted(Player player) {
        if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void stop(Player winner) {
        long timeTaken = System.currentTimeMillis() - startTime;
        
        if (winner == null) {
            return;
        } else {
            Bukkit.broadcastMessage(ChatColor.GREEN + winner.getName() + " has won the lava death challenge in " + (timeTaken / 1000) + " seconds!"); // Announce winner then teleport to hub location
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(endLocation);
        }
    }

    @Override
    public void setEndLocation(Location location) { // Setter
        this.endLocation = location;
    }

    @Override
    public String getDescription() {
        return "Be the first player to die by lava!";
    }
}
