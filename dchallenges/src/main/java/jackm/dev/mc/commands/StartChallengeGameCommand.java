package jackm.dev.mc.commands;

import jackm.dev.mc.utils.ChallengeManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartChallengeGameCommand implements CommandExecutor {

    private final ChallengeManager challengeManager;

    public StartChallengeGameCommand(ChallengeManager manager) {
        this.challengeManager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command!");
            return true;
        }

        challengeManager.startGame(sender);
        return true;
    }
}
