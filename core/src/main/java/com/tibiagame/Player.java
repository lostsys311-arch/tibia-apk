package com.tibiagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {
    public float x, y;
    public float speed = 200;
    public int health = 100;
    public int maxHealth = 100;
    public int mana = 50;
    public int maxMana = 50;
    public int level = 1;
    public int exp = 0;
    public int expToNext = 100;

    private GameMap map;
    private float size = 24;

    public Player(GameMap map) {
        this.map = map;
        x = 15 * GameMap.TILE_SIZE;
        y = 15 * GameMap.TILE_SIZE;
    }

    public void update(float delta) {
        float dx = 0, dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) dy = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) dy = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) dx = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dx = 1;

        if (dx != 0 && dy != 0) {
            dx *= 0.707f;
            dy *= 0.707f;
        }

        float nx = x + dx * speed * delta;
        float ny = y + dy * speed * delta;

        float margin = 2;
        if (!map.isSolidTile(nx + margin, y + margin) &&
            !map.isSolidTile(nx + size - margin, y + margin) &&
            !map.isSolidTile(nx + margin, y + size - margin) &&
            !map.isSolidTile(nx + size - margin, y + size - margin)) {
            x = nx;
        }

        if (!map.isSolidTile(x + margin, ny + margin) &&
            !map.isSolidTile(x + size - margin, ny + margin) &&
            !map.isSolidTile(x + margin, ny + size - margin) &&
            !map.isSolidTile(x + size - margin, ny + size - margin)) {
            y = ny;
        }
    }

    public void render(ShapeRenderer sr) {
        sr.setColor(Color.YELLOW);
        sr.rect(x, y, size, size);

        sr.setColor(Color.WHITE);
        sr.rect(x, y + size + 4, size, 4);
        sr.setColor(Color.RED);
        sr.rect(x, y + size + 4, size * health / maxHealth, 4);

        sr.setColor(Color.WHITE);
        sr.rect(x, y + size + 10, size, 3);
        sr.setColor(Color.BLUE);
        sr.rect(x, y + size + 10, size * mana / maxMana, 3);
    }

    public void renderHUD(ShapeRenderer sr, float viewWidth, float viewHeight) {
        sr.setColor(Color.WHITE);
        sr.rect(10, viewHeight - 30, 200, 20);
        sr.setColor(Color.RED);
        sr.rect(10, viewHeight - 30, 200 * health / maxHealth, 20);

        sr.setColor(Color.WHITE);
        sr.rect(10, viewHeight - 55, 150, 15);
        sr.setColor(Color.BLUE);
        sr.rect(10, viewHeight - 55, 150 * mana / maxMana, 15);

        sr.setColor(Color.YELLOW);
        sr.rect(220, viewHeight - 30, 100, 20);
    }

    public void addExp(int amount) {
        exp += amount;
        while (exp >= expToNext) {
            exp -= expToNext;
            level++;
            expToNext = (int)(expToNext * 1.5f);
            maxHealth += 20;
            health = maxHealth;
            maxMana += 10;
            mana = maxMana;
        }
    }

    public void takeDamage(int dmg) {
        health = Math.max(0, health - dmg);
    }
}
