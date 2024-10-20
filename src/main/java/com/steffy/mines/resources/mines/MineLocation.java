package com.steffy.mines.resources.mines;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class MineLocation {

    private final double x, y, z;
    private final float yaw, pitch;
    public final String world;

    MineLocation(double x, double y, double z, float yaw, float pitch, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    public Location toLocation(Plugin plugin) {
        World world = plugin.getServer().getWorld(this.world);
        if(world == null) return null;
        Location location = new Location(world, x, y, z);
        location.setYaw(yaw);
        location.setPitch(pitch);
        return location;
    }
}