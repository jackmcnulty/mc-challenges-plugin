package jackm.dev.mc.challenges;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.World;

import jackm.dev.mc.utils.RandomWorldLocation;

public class DiamondChallenge implements Challenge {
    private Location endLocation;
    private long startTime;

    public DiamondChallenge(Location endLocation) {
        this.endLocation = endLocation;
    }

    @Override
    public void start() {
        // Announce the challenge to all players
        Bukkit.broadcastMessage(ChatColor.BLUE + "Starting the diamond challenge! " + getDescription());

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
        return player.getInventory().contains(Material.DIAMOND); //This is two diamonds for whatever reason and they must be picked up from ground in sequence
    }

    @Override
    public void stop(Player winner) {
        long timeTaken = System.currentTimeMillis() - startTime;
        
        if (winner == null) {
            return;
        } else {
            Bukkit.broadcastMessage(ChatColor.GREEN + winner.getName() + " has won the diamond challenge in " + (timeTaken / 1000) + " seconds!"); // Announce winner then teleport to hub location
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
        return "The first player to get a diamond in their inventory wins!";
    }
}
