package jackm.dev.mc.commands;

import jackm.dev.mc.utils.ChallengeManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHubCommand implements CommandExecutor {

    private final ChallengeManager challengeManager;

    public SetHubCommand(ChallengeManager manager) {
        this.challengeManager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            challengeManager.setEndLocation(player.getLocation());
            player.sendMessage(ChatColor.GREEN + "End location set!");
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return false;
        }
    }
}