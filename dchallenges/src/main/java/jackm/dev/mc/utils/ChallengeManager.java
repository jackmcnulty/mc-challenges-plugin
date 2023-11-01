package jackm.dev.mc.utils;

import jackm.dev.mc.challenges.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChallengeManager {

    private Location defaultEndLocation;
    private final JavaPlugin plugin;
    private BukkitTask gracePeriodTask;
    private final LeaderboardManager leaderboardManager = new LeaderboardManager();
    private final List<Challenge> availableChallenges;
    private Challenge currentChallenge;
    private final Random random = new Random();
    
    public ChallengeManager(JavaPlugin plugin, Location endLocation) {
        this.plugin = plugin;
        this.availableChallenges = Arrays.asList(
            new DiamondChallenge(endLocation)
        );
    }

    /*
     * Checks to see if /sethub has been run, if so invokes nextChallenge()
     * Invoked by StartChallengeGameCommand.java
     * @param sender The command sender for sending messages back to the player
     */
    public void startGame(CommandSender sender) {
        if (!isEndLocationSet()) {
            sender.sendMessage(ChatColor.RED + "The end location has not been set yet. Please set it using /sethub.");
            return;
        }
        nextChallenge();
    }

    /*
     * Selects random challenge from availableChallenges and invokes that particular challenges start() method
     * Invoked by ChallengeManager.startGame()
     */
    private void nextChallenge() {
        int index = random.nextInt(availableChallenges.size());
        startChallenge(availableChallenges.get(index));
    }

    private void startChallenge(Challenge challenge) {
        if (currentChallenge != null) {
            stopCurrentChallenge();
        }

        this.currentChallenge = challenge;
        challenge.start();
    }

    /*
     * Ends the game and invokes the leaderboardManager to display the leaderboard then resets it
     * Invoked by EndChallengeGameCommand.java
     */
    public void stopGame() {
        stopCurrentChallenge();
        Bukkit.broadcastMessage(ChatColor.RED + "Game has been stopped.");
        leaderboardManager.displayLeaderboard();
        leaderboardManager.resetLeaderboard();
    }

    /*
     * Stops the current challenge
     * Invoked by ChallengeManager.stopGame()
     */
    private void stopCurrentChallenge() {
        if (currentChallenge == null) return;
        currentChallenge.stop(null);
        currentChallenge = null;
    }

    /*
     * Checks to see if the current challenge is completed by the player, if so it will update/display the leaderboard and start the grace period
     * Invoked by EventListener.java
     * @param player The player to check completion for
     */
    public void checkCompletion(Player player) {
        if (currentChallenge == null) return;

        if (currentChallenge.isCompleted(player)) {
            updateLeaderboard(player);
            leaderboardManager.displayLeaderboard();
            currentChallenge.stop(player);
            startGracePeriod();
        }
    }

    /*
     * Starts the grace period and invokes nextChallenge() after the grace period is over
     * Invoked by ChallengeManager.checkCompletion()
     */
    private void startGracePeriod() {
        int gracePeriodDuration = plugin.getConfig().getInt("grace-period-duration", 60);
        
        Bukkit.broadcastMessage(plugin.getConfig().getString("grace-period-start-message", "Grace period started. Next challenge in 1 minute."));

        long delayTicks = 20L * gracePeriodDuration;
    
        gracePeriodTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            nextChallenge();
            gracePeriodTask = null;
        }, delayTicks); // This schedules a delay of 20 ticks/second * 60 seconds = 1 minute
    }    

    /*
     * Cancels the grace period and invokes nextChallenge()
     * Invoked by SkipGracePeriodCommand.java
     */
    public void skipGracePeriod() {
        if (gracePeriodTask != null) {
            gracePeriodTask.cancel();
            gracePeriodTask = null;
            nextChallenge();
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("grace-period-message", "&aGrace period started. Next challenge in 1 minute.")));
        } else {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("no-grace-period-message", "&cNo grace period is currently active.")));

        }
    }

    /*
     * Setter for the defaultEndLocation to the location passed by /sethub
     * Invoked by SetHubCommand.java
     * @param newEndLocation The new end location to set
     */
    public void setEndLocation(Location newEndLocation) {
        for (Challenge challenge : availableChallenges) {
            challenge.setEndLocation(newEndLocation);
        }
        this.defaultEndLocation = newEndLocation;
    }

    /*
     * Checks to see if the defaultEndLocation has been set
     * Invoked by ChallengeManager.startGame()
     * @return true if the defaultEndLocation has been set, false otherwise
     */
    public boolean isEndLocationSet() {
        return this.defaultEndLocation != null;
    }

    /*
     * Invokes the leaderboardManager to display the leaderboard
     * Invoked by LeaderboardCommand.java
     */
    public void getLeaderboard() {
        leaderboardManager.displayLeaderboard();
    }

    /*
     * Updates the leaderboard with the player passed by incrementing their score by 1
     * Invoked by ChallengeManager.checkCompletion()
     * @param player The player to update the leaderboard with
     */
    private void updateLeaderboard(Player player) {
        leaderboardManager.incrementScore(player);
    }
}