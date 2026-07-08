package com.tibiagame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.List;
import java.util.ArrayList;

public class Monster {
    public float x, y;
    public int health;
    public int maxHealth;
    public int damage;
    public int expReward;
    public float speed;
    public float size = 20;

    private GameMap map;
    private float attackCooldown = 0;
    private static final float ATTACK_RATE = 1.0f;

    public Monster(GameMap map, float x, float y, int level) {
        this.map = map;
        this.x = x;
        this.y = y;
        maxHealth = 20 + level * 10;
        health = maxHealth;
        damage = 5 + level * 3;
        expReward = 15 + level * 10;
        speed = 40 + level * 5;
    }

    public void update(float delta, Player player, List<Monster> monsters) {
        attackCooldown -= delta;

        float dx = player.x - x;
        float dy = player.y - y;
        float dist = (float)Math.sqrt(dx * dx + dy * dy);

        if (dist < 200 && dist > 30) {
            dx /= dist;
            dy /= dist;

            float nx = x + dx * speed * delta;
            float ny = y + dy * speed * delta;

            boolean blocked = false;
            for (Monster m : monsters) {
                if (m == this) continue;
                if (Math.abs(m.x - nx) < size && Math.abs(m.y - ny) < size) {
                    blocked = true;
                    break;
                }
            }

            if (!blocked) {
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
        }

        if (dist < 35 && attackCooldown <= 0) {
            player.takeDamage(damage);
            attackCooldown = ATTACK_RATE;
        }
    }

    public void render(ShapeRenderer sr) {
        sr.setColor(Color.RED);
        sr.rect(x, y, size, size);

        sr.setColor(Color.WHITE);
        sr.rect(x, y + size + 2, size, 3);
        sr.setColor(Color.GREEN);
        if (health > 0) {
            sr.rect(x, y + size + 2, size * health / maxHealth, 3);
        }
    }
}
