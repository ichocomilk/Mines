package com.steffy.mines.managers;

import com.steffy.mines.Mines;
import com.steffy.mines.resources.mines.Mine;
import com.steffy.mines.resources.mines.MineComposition;
import com.steffy.mines.resources.mines.MinePosition;
import com.steffy.mines.utilities.general.Chat;
import com.steffy.mines.utilities.general.Manager;
import com.steffy.mines.utilities.general.Message;

import net.minecraft.world.level.block.state.IBlockData;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_21_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_21_R1.block.CraftBlockType;
import org.bukkit.craftbukkit.v1_21_R1.block.data.CraftBlockData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MineManager extends Manager<Mine> implements Runnable {

    private final Plugin plugin;

    public MineManager(Plugin plugin) {
        super("mines", plugin);
        this.plugin = plugin;
    }

    public final void reset(final Mine mine, final World world) {   
        final List<MineComposition> compositions = mine.getCompositions();
        final String material = compositions.iterator().next().material;
        final IBlockData materialData = ((CraftBlockType<?>)(Material.getMaterial(material)).asBlockType()).getHandle().o();

        final MinePosition pos1 = mine.getPositionOne();
        final MinePosition pos2 = mine.getPositionTwo();
        final int minX = Math.min(pos1.x, pos2.x), minY = Math.min(pos1.y, pos2.y), minZ = Math.min(pos1.z, pos2.z);

        final int xLimit = Math.abs(pos1.x - pos2.x);
        final int yLimit = Math.abs(pos1.y - pos2.y);
        final int zLimit = Math.abs(pos1.z - pos2.z);
        for (int x = 0; x <= xLimit; x++) {
            for (int y = 0; y <= yLimit; y++) {
                for (int z = 0; z <= zLimit; z++) {
                    final Block block = world.getBlockAt(
                        minX + x,
                        minY + y,
                        minZ + z
                    );
                    final CraftBlock craftBlock = ((CraftBlock)block);
                    final IBlockData old = craftBlock.getNMS();
                    if (CraftBlockType.minecraftToBukkit(old.b()) == Material.AIR) {
                        craftBlock.getHandle().a(craftBlock.getPosition(), materialData, 1042);
                    }
                }
            }
        }


        final int maxX = Math.max(pos1.x, pos2.x), maxY = Math.max(pos1.y, pos2.y), maxZ = Math.max(pos1.z, pos2.z);

        final String message = Chat.format(Message.MINE_BROADCAST.toString().replace("{0}", mine.toString()));
        final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        final Location mineLocation = mine.getLocation().toLocation(plugin);
        for (final Player player : players) {
            final Location location = player.getLocation();
            final int x = location.getBlockX();
            final int y = location.getBlockX();
            final int z = location.getBlockX();
            if (
                x >= minX && x <= maxX &&
                y >= minY && y <= maxY &&
                z >= minZ && z <= maxZ
            ) {
                player.teleport(mineLocation);
                player.sendMessage(message);
            }
        }
    }

    public float availableComposition(Mine mine) {
        AtomicReference<Float> currentPercentage = new AtomicReference<>(0.0f);
        mine.getCompositions().forEach(mineComposition -> currentPercentage.updateAndGet(v -> v + mineComposition.percentage));
        return currentPercentage.get();
    }

    public boolean canComposite(Mine mine, float percentage) {
        percentage += availableComposition(mine);
        return percentage <= 100.0f;
    }

    public Mine getMine(String name) {
        for(Mine mine : getTs()) {
            if(mine.toString().equalsIgnoreCase(name)) {
                return mine;
            }
        }
        return null;
    }

    @Override
    public void run() {
        final List<Mine> mines = getTs();
        for (final Mine mine : mines) {
            if (mine.ticksSeconds++ >= mine.resetTime) {
                reset(mine, plugin.getServer().getWorld(mine.getLocation().world));
                mine.ticksSeconds = 0;
            }
        }
    }
}
