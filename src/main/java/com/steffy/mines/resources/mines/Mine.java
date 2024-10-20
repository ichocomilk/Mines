package com.steffy.mines.resources.mines;


import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Mine {

    private final String string;
    private final List<MineComposition> compositions;

    public transient int ticksSeconds;
    public int resetTime = 0;

    private MinePosition positionOne, positionTwo;
    private MineLocation mineLocation;

    public Mine(String string) {
        this.string = string;
        this.compositions = new ArrayList<>();
    }

    public void add(MineComposition mineComposition) {
        this.compositions.add(mineComposition);
    }

    public void remove(MineComposition mineComposition) {
        this.compositions.remove(mineComposition);
    }

    public void setPositionOne(MinePosition newPositionOne) {
        this.positionOne = newPositionOne;
    }

    public void setPositionTwo(MinePosition newPositionTwo) {
        this.positionTwo = newPositionTwo;
    }

    public void setLocation(Location location) {
        this.mineLocation = new MineLocation(
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch(),
                Objects.requireNonNull(location.getWorld()).getName()
        );
    }
    public MinePosition getPositionOne() {
        return positionOne;
    }
    public MinePosition getPositionTwo() {
        return positionTwo;
    }

    public MineLocation getLocation() {
        return mineLocation;
    }

    public MineComposition getComposition(String string) {
        for(MineComposition mineComposition : compositions) {
            if(mineComposition.material.equals(string.toUpperCase())) {
                return mineComposition;
            }
        }
        return null;
    }

    public List<MineComposition> getCompositions() {
        return Collections.unmodifiableList(compositions);
    }

    @Override
    public String toString() {
        return string;
    }
}
