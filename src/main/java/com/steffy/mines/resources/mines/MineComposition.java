package com.steffy.mines.resources.mines;

public class MineComposition {

    private final String material;
    private final int percentage;

    public MineComposition(String material, int percentage) {
        this.material = material;
        this.percentage = percentage;
    }

    public int getPercentage() {
        return percentage;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String toString() {
        return material + ":" + percentage + "%";
    }
}
