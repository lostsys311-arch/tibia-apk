package com.tibiagame;

public class Tiles {
    public static final int GRASS = 0;
    public static final int DIRT = 1;
    public static final int WATER = 2;
    public static final int TREE = 3;
    public static final int WALL = 4;
    public static final int BRICK = 5;

    public static boolean isSolid(int tile) {
        return tile == TREE || tile == WALL || tile == BRICK || tile == WATER;
    }
}
