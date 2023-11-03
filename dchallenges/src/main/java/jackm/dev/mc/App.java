package jackm.dev.mc;

import jackm.dev.mc.commands.*;
import jackm.dev.mc.utils.ChallengeManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import jackm.dev.mc.listeners.EventListener;

public class App extends JavaPlugin {

    private ChallengeManager challengeManager;
    private Location defaultEndLocation;

    @Override
    public void onEnable() {
        this.reloadConfig();
        this.getConfig();
        this.saveDefaultConfig();

        // Initialize the challenge manager
        challengeManager = new ChallengeManager(this, defaultEndLocation); 

        // Register the commands
        getCommand("startchallengegame").setExecutor(new StartChallengeGameCommand(challengeManager));
        getCommand("endchallengegame").setExecutor(new EndChallengeGameCommand(challengeManager));
        getCommand("lb").setExecutor(new LeaderboardCommand(challengeManager));
        getCommand("sethub").setExecutor(new SetHubCommand(challengeManager));
        getCommand("skipgp").setExecutor(new SkipGracePeriodCommand(challengeManager));

        // Event Listener for challenge completion (assuming you've created an EventListener class)
        Bukkit.getPluginManager().registerEvents(new EventListener(challengeManager, this), this);

        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled!");
    }
}