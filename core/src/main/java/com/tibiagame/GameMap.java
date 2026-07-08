package com.tibiagame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.List;
import java.util.ArrayList;

public class GameMap {
    public static final int WIDTH = 48;
    public static final int HEIGHT = 32;
    public static final int TILE_SIZE = 32;

    private int[][] tiles;
    private List<Rectangle> walls;

    public GameMap() {
        tiles = new int[WIDTH][HEIGHT];
        walls = new ArrayList<>();
        generate();
    }

    private void generate() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                tiles[x][y] = Tiles.GRASS;
            }
        }

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1) {
                    setTile(x, y, Tiles.BRICK);
                }
            }
        }

        addBuilding(10, 10, 6, 5);
        addBuilding(30, 18, 4, 4);

        for (int i = 0; i < 20; i++) {
            int x = 3 + (int)(Math.random() * (WIDTH - 6));
            int y = 3 + (int)(Math.random() * (HEIGHT - 6));
            if (tiles[x][y] == Tiles.GRASS) {
                setTile(x, y, Tiles.TREE);
            }
        }

        for (int i = 0; i < 8; i++) {
            int x = 3 + (int)(Math.random() * (WIDTH - 6));
            int y = 3 + (int)(Math.random() * (HEIGHT - 6));
            if (tiles[x][y] == Tiles.GRASS) {
                setTile(x, y, Tiles.WATER);
            }
        }
    }

    private void addBuilding(int sx, int sy, int w, int h) {
        for (int x = sx; x < sx + w; x++) {
            for (int y = sy; y < sy + h; y++) {
                if (x == sx || x == sx + w - 1 || y == sy || y == sy + h - 1) {
                    if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
                        setTile(x, y, Tiles.WALL);
                    }
                } else {
                    if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
                        setTile(x, y, Tiles.DIRT);
                    }
                }
            }
        }
    }

    public void setTile(int x, int y, int tile) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            tiles[x][y] = tile;
            if (Tiles.isSolid(tile)) {
                walls.add(new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE));
            }
        }
    }

    public int getTile(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return Tiles.BRICK;
        return tiles[x][y];
    }

    public boolean isSolidTile(float px, float py) {
        int tx = (int)(px / TILE_SIZE);
        int ty = (int)(py / TILE_SIZE);
        return Tiles.isSolid(getTile(tx, ty));
    }

    public void render(SpriteBatch batch, Art art, float cameraX, float cameraY) {
        int startX = Math.max(0, (int)(cameraX / TILE_SIZE) - 1);
        int endX = Math.min(WIDTH, startX + 30);
        int startY = Math.max(0, (int)(cameraY / TILE_SIZE) - 1);
        int endY = Math.min(HEIGHT, startY + 22);

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                float px = x * TILE_SIZE;
                float py = y * TILE_SIZE;
                batch.draw(art.tileTex[tiles[x][y]], px, py, TILE_SIZE, TILE_SIZE);
            }
        }
    }
}
