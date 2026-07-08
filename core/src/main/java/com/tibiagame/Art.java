package com.tibiagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Art {
    public Texture playerTex;
    public Texture monsterTex;
    public Texture attackTex;
    public Texture[] tileTex = new Texture[6];

    public Art() {
        playerTex = makePlayer();
        monsterTex = makeMonster();
        attackTex = makeAttack();
        tileTex[0] = makeGrass();
        tileTex[1] = makeDirt();
        tileTex[2] = makeWater();
        tileTex[3] = makeTree();
        tileTex[4] = makeWall();
        tileTex[5] = makeBrick();
    }

    private Texture makePlayer() {
        Pixmap p = new Pixmap(24, 32, Pixmap.Format.RGBA8888);
        p.setColor(0.9f, 0.8f, 0.1f, 1);
        p.fillCircle(12, 8, 6);
        p.fillRect(8, 14, 8, 12);
        p.fillRect(5, 18, 4, 10);
        p.fillRect(15, 18, 4, 10);
        p.setColor(0.2f, 0.2f, 0.2f, 1);
        p.fillCircle(9, 7, 1);
        p.fillCircle(15, 7, 1);
        p.setColor(0.8f, 0.6f, 0.1f, 1);
        p.fillRect(9, 4, 6, 3);
        Texture t = new Texture(p);
        p.dispose();
        return t;
    }

    private Texture makeMonster() {
        Pixmap p = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        p.setColor(0.8f, 0.1f, 0.1f, 1);
        p.fillCircle(10, 8, 8);
        p.fillRect(6, 8, 8, 8);
        p.setColor(1, 1, 1, 1);
        p.fillCircle(6, 6, 2);
        p.fillCircle(14, 6, 2);
        p.setColor(0, 0, 0, 1);
        p.fillCircle(6, 6, 1);
        p.fillCircle(14, 6, 1);
        p.setColor(0.9f, 0.9f, 0.1f, 1);
        p.fillCircle(10, 10, 2);
        Texture t = new Texture(p);
        p.dispose();
        return t;
    }

    private Texture makeAttack() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(0.9f, 0.9f, 0.9f, 0.7f);
        p.fillTriangle(0, 0, 32, 16, 0, 32);
        p.setColor(1, 1, 1, 0.9f);
        p.fillTriangle(4, 8, 28, 16, 4, 24);
        Texture t = new Texture(p);
        p.dispose();
        return t;
    }

    private Texture makeGrass() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(0.2f, 0.65f, 0.1f, 1);
        p.fill();
        p.setColor(0.15f, 0.55f, 0.05f, 1);
        for (int i = 0; i < 12; i++) {
            int gx = (int)(Math.random() * 28) + 2;
            int gy = (int)(Math.random() * 28) + 2;
            p.drawLine(gx, gy, gx, gy + 3);
            p.drawLine(gx - 1, gy + 1, gx + 1, gy + 1);
        }
        Texture t = new Texture(p);
        p.dispose();
        return t;
    }

    private Texture makeDirt() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(0.5f, 0.38f, 0.2f, 1);
        p.fill();
        p.setColor(0.4f, 0.3f, 0.15f, 1);
        for (int i = 0; i < 8; i++) {
            int dx = (int)(Math.random() * 30) + 1;
            int dy = (int)(Math.random() * 30) + 1;
            p.fillCircle(dx, dy, 2);
        }
        Texture t = new Texture(p);
        p.dispose();
        return t;
    }

    private Texture makeWater() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(0.1f, 0.35f, 0.75f, 1);
        p.fill();
        p.setColor(0.2f, 0.5f, 0.9f, 0.5f);
        for (int i = 0; i < 5; i++) {
            int wy = 4 + i * 6 + (int)(Math.random() * 3);
            p.drawLine(0, wy, 32, wy);
        }
        Texture t = new Texture(p);
        p.dispose();
        return t;
    }

    private Texture makeTree() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(0.15f, 0.5f, 0.05f, 1);
        p.fill();
        p.setColor(0.4f, 0.25f, 0.1f, 1);
        p.fillRect(13, 4, 6, 12);
        p.setColor(0.05f, 0.35f, 0.02f, 1);
        p.fillCircle(16, 18, 12);
        p.setColor(0.1f, 0.45f, 0.05f, 1);
        p.fillCircle(16, 22, 9);
        p.setColor(0.05f, 0.3f, 0.02f, 1);
        p.fillCircle(16, 26, 6);
        Texture t = new Texture(p);
        p.dispose();
        return t;
    }

    private Texture makeWall() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(0.38f, 0.33f, 0.28f, 1);
        p.fill();
        p.setColor(0.45f, 0.4f, 0.35f, 1);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                p.fillRectangle(x * 8, y * 8, 7, 7);
            }
        }
        p.setColor(0.3f, 0.25f, 0.2f, 1);
        p.drawLine(0, 0, 31, 31);
        p.drawLine(31, 0, 0, 31);
        Texture t = new Texture(p);
        p.dispose();
        return t;
    }

    private Texture makeBrick() {
        Pixmap p = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        p.setColor(0.5f, 0.2f, 0.1f, 1);
        p.fill();
        p.setColor(0.3f, 0.12f, 0.05f, 1);
        p.drawLine(0, 8, 32, 8);
        p.drawLine(0, 16, 32, 16);
        p.drawLine(0, 24, 32, 24);
        p.drawLine(16, 0, 16, 8);
        p.drawLine(8, 8, 8, 16);
        p.drawLine(24, 8, 24, 16);
        p.drawLine(16, 16, 16, 24);
        p.drawLine(8, 24, 8, 32);
        p.drawLine(24, 24, 24, 32);
        Texture t = new Texture(p);
        p.dispose();
        return t;
    }

    public void dispose() {
        playerTex.dispose();
        monsterTex.dispose();
        attackTex.dispose();
        for (Texture t : tileTex) t.dispose();
    }
}
