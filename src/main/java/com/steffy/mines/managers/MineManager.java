package com.steffy.mines.managers;

import com.steffy.mines.resources.mines.Mine;
import com.steffy.mines.resources.mines.MineComposition;
import com.steffy.mines.resources.mines.MinePosition;
import com.steffy.mines.utilities.general.Chat;
import com.steffy.mines.utilities.general.Manager;
import com.steffy.mines.utilities.general.Message;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class MineManager extends Manager<Mine> {

    private final Plugin plugin;

    public MineManager(Plugin plugin) {
        super("mines", plugin);
        this.plugin = plugin;
    }

    public void reset(Mine mine, World world) {
        LinkedList<Block> blocks = getBounds(mine, true, world);
        float start = System.nanoTime();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < 64; i++) {
                    if (blocks.isEmpty()) {
                        cancel();
                        break;
                    }
                    Block block = blocks.getFirst();
                    Material material = getRandom(mine);
                    if (material != null) block.setType(material);
                    blocks.remove(block);
                }
            }
        }.runTaskTimer(plugin, 0L, 0L);
        getPlayers(mine, world).forEach(uuid -> {
            Player player = plugin.getServer().getPlayer(uuid);
            if(player != null) {
                player.teleport(mine.getLocation().toLocation(plugin));
                float completion = (System.nanoTime() - start) / 1000000.0f;
                player.sendMessage(Chat.format(Message.MINE_BROADCAST.toString()
                        .replace("{0}", mine.toString())
                        .replace("{1}", String.valueOf(completion))
                ));
            }
        });
    }

    private Set<UUID> getPlayers(Mine mine, World world) {
        Set<UUID> uuids = new HashSet<>();

        getBounds(mine, false, world).forEach(block -> plugin.getServer().getOnlinePlayers().forEach(o -> {
            Location location = o.getLocation();
            if(location.getBlockX() == block.getX() &&
                    location.getBlockY() == block.getY() &&
                    location.getBlockZ() == block.getZ()) {
                uuids.add(o.getUniqueId());
            }
        }));
        return uuids;
    }

    private LinkedList<Block> getBounds(Mine mine, boolean air, World world) {
        MinePosition pos1 = mine.getPositionOne();
        MinePosition pos2 = mine.getPositionTwo();

        LinkedList<Block> blocks = new LinkedList<>();

        for (int x = 0; x <= Math.abs(pos1.getX() - pos2.getX()); x++) {
            for (int y = 0; y <= Math.abs(pos1.getY() - pos2.getY()); y++) {
                for (int z = 0; z <= Math.abs(pos1.getZ() - pos2.getZ()); z++) {
                    Block block = world.getBlockAt(
                            (Math.min(pos1.getX(), pos2.getX())) + x,
                            (Math.min(pos1.getY(), pos2.getY())) + y,
                            (Math.min(pos1.getZ(), pos2.getZ())) + z
                    );
                    if(air) {
                        if(block.getType() == Material.AIR) {
                            blocks.add(block);
                        }
                    } else {
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }

    private Material getRandom(Mine mine) {
        List<MineComposition> compositions = mine.getCompositions();
        int totalPercentage = 0;
        for(MineComposition mineComposition : compositions) {
            totalPercentage = totalPercentage + mineComposition.getPercentage();
        }

        if(totalPercentage == 0) return null;

        Random random = new Random();
        int index =  random.nextInt(totalPercentage);
        int sum = 0, i = 0;
        while (sum < index) {
            sum = sum + compositions.get(i++).getPercentage();
        }
        return Material.valueOf(compositions.get(Math.max(0, i - 1)).getMaterial());
    }

    public float availableComposition(Mine mine) {
        AtomicReference<Float> currentPercentage = new AtomicReference<>(0.0f);
        mine.getCompositions().forEach(mineComposition -> currentPercentage.updateAndGet(v -> v + mineComposition.getPercentage()));
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
}
