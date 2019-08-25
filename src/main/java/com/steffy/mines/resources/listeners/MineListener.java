package com.steffy.mines.resources.listeners;

import com.google.gson.reflect.TypeToken;
import com.steffy.mines.Mines;
import com.steffy.mines.managers.MineManager;
import com.steffy.mines.resources.mines.Mine;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

import java.util.List;

public class MineListener implements Listener {

    private final Mines mines;
    private final MineManager mineManager;

    public MineListener(Mines mines) {
        this.mines = mines;
        this.mineManager = mines.getMineManager();
    }

    @EventHandler
    public void onWorldSave(WorldSaveEvent event) {
        World world = mines.getServer().getWorlds().get(0);
        if(world == null) return;

        if(event.getWorld().getName().equalsIgnoreCase(world.getName())) {
            mineManager.serialize();
            mineManager.deserialize(new TypeToken<List<Mine>>(){}.getType());
            mineManager.getTs().forEach(mine -> mineManager.reset(mine, event.getWorld()));
        }
    }
}
