package jackm.dev.mc.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class LeaderboardManager {

    private final Map<UUID, Integer> leaderboard = new HashMap<>();

    public void incrementScore(Player player) {
        leaderboard.put(player.getUniqueId(), leaderboard.getOrDefault(player.getUniqueId(), 0) + 1);
    }

    public void displayLeaderboard() {
        Bukkit.broadcastMessage(ChatColor.GOLD + "------- Leaderboard -------");

        // Sorting the leaderboard based on scores in descending order.
        Map<UUID, Integer> sortedLeaderboard = leaderboard.entrySet()
                .stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        for (Map.Entry<UUID, Integer> entry : sortedLeaderboard.entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());
            if (player != null) {
                Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + ": " + ChatColor.WHITE + entry.getValue());
            }
        }

        Bukkit.broadcastMessage(ChatColor.GOLD + "--------------------------");
    }

    public void resetLeaderboard() {
        leaderboard.clear();
    }
}
