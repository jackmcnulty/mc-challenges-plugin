package jackm.dev.mc.commands;

import jackm.dev.mc.utils.ChallengeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LeaderboardCommand implements CommandExecutor {

    private final ChallengeManager challengeManager;

    public LeaderboardCommand(ChallengeManager manager) {
        this.challengeManager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        challengeManager.getLeaderboard();
        return true;
    }
}

