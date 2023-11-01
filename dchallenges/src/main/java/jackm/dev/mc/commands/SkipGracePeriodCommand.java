package jackm.dev.mc.commands;

import jackm.dev.mc.utils.ChallengeManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkipGracePeriodCommand implements CommandExecutor {

    private final ChallengeManager challengeManager;

    public SkipGracePeriodCommand(ChallengeManager manager) {
        this.challengeManager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command!");
            return true;
        }

        challengeManager.skipGracePeriod();
        return true;
    }
}
