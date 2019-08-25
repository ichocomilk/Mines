package com.steffy.mines.resources.mines;


import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Mine {

    private final String string;
    private final List<MineComposition> compositions;

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

    public void setPositionOne(MinePosition positionOne) {
        this.positionOne = positionOne;
    }

    public void setPositionTwo(MinePosition positionTwo) {
        this.positionTwo = positionTwo;
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
            if(mineComposition.getMaterial().equalsIgnoreCase(string)) {
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
