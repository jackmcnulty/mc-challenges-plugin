package jackm.dev.mc.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;

import java.util.Random;

public class RandomWorldLocation {

    public static Location getRandomLocation(World world, int minX, int maxX, int minZ, int maxZ) {
        Random random = new Random();
        Location randomLocation = null;
        boolean isSafeLocation = false;

        while (!isSafeLocation) {
            int x = minX + random.nextInt(maxX - minX + 1);
            int z = minZ + random.nextInt(maxZ - minZ + 1);
            int y = world.getHighestBlockYAt(x, z);

            randomLocation = new Location(world, x, y, z);

            // Check if the biome is an ocean or if the block is lava
            if (randomLocation.getBlock().getBiome() != Biome.OCEAN && 
                randomLocation.getBlock().getBiome() != Biome.DEEP_OCEAN &&
                randomLocation.getBlock().getType() != Material.LAVA &&
                randomLocation.getBlock().getType() != Material.WATER &&
                y > 55) {
                isSafeLocation = true;
            }
        }

        return randomLocation;
    }
}
