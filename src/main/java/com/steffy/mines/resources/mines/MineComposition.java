package com.steffy.mines.resources.mines;

public class MineComposition {

    public final int percentage;
    public final String material;

    public MineComposition(String material, int percentage) {
        this.percentage = percentage;
        this.material = material;
    }

    @Override
    public String toString() {
        return material + ":" + percentage + "%";
    }
}
