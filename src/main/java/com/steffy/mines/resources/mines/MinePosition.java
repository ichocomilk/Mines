package com.steffy.mines.resources.mines;

public class MinePosition {

    public int x, y, z;

    public MinePosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return x + ":" + y + ":" + z;
    }
}