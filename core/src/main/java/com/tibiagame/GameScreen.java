package com.tibiagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {
    private TibiaGame game;
    private OrthographicCamera camera;
    private ShapeRenderer sr;
    private GameMap map;
    private Player player;
    private List<Monster> monsters;
    private float spawnTimer;
    private static final float SPAWN_INTERVAL = 5.0f;
    private float attackTimer;
    private static final float ATTACK_COOLDOWN = 0.5f;

    public GameScreen(TibiaGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        sr = new ShapeRenderer();
        map = new GameMap();
        player = new Player(map);
        monsters = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            spawnMonster();
        }
        spawnTimer = SPAWN_INTERVAL;
    }

    private void spawnMonster() {
        int attempts = 0;
        while (attempts < 50) {
            int tx = 2 + (int)(Math.random() * (GameMap.WIDTH - 4));
            int ty = 2 + (int)(Math.random() * (GameMap.HEIGHT - 4));
            float sx = tx * GameMap.TILE_SIZE;
            float sy = ty * GameMap.TILE_SIZE;

            if (!map.isSolidTile(sx, sy) &&
                Math.abs(sx - player.x) > 200) {
                int level = 1 + (int)(Math.random() * player.level);
                monsters.add(new Monster(map, sx, sy, level));
                return;
            }
            attempts++;
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        handleInput();
        player.update(delta);

        spawnTimer -= delta;
        if (spawnTimer <= 0 && monsters.size() < 15) {
            spawnMonster();
            spawnTimer = SPAWN_INTERVAL;
        }

        attackTimer -= delta;
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && attackTimer <= 0) {
            performAttack();
            attackTimer = ATTACK_COOLDOWN;
        }

        Iterator<Monster> it = monsters.iterator();
        while (it.hasNext()) {
            Monster m = it.next();
            m.update(delta, player, monsters);
            if (m.health <= 0) {
                player.addExp(m.expReward);
                it.remove();
            }
        }

        camera.position.set(player.x + 12, player.y + 12, 0);
        camera.update();

        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        map.render(sr, camera.position.x - 400, camera.position.y - 300);
        for (Monster m : monsters) {
            m.render(sr);
        }
        player.render(sr);
        sr.end();

        sr.setProjectionMatrix(camera.view.scl(1).cpy());
        sr.begin(ShapeRenderer.ShapeType.Filled);
        player.renderHUD(sr, 800, 600);
        renderInfo(sr);
        sr.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void performAttack() {
        float ax = player.x + 12;
        float ay = player.y + 12;
        float range = 60;

        for (Monster m : monsters) {
            float dx = (m.x + m.size / 2) - ax;
            float dy = (m.y + m.size / 2) - ay;
            float dist = (float)Math.sqrt(dx * dx + dy * dy);

            if (dist < range) {
                int dmg = 8 + (int)(Math.random() * 5) + player.level;
                m.health -= dmg;
            }
        }
    }

    private void renderInfo(ShapeRenderer sr) {
        String info = "Lv." + player.level + " | EXP: " + player.exp + "/" + player.expToNext;
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, info, 10, 40);
        game.font.draw(game.batch, "Monsters: " + monsters.size(), 10, 20);
        game.font.draw(game.batch, "Click to attack | WASD to move", 10, 580);
    }

    @Override
    public void resize(int w, int h) {
        camera.setToOrtho(false, w, h);
    }

    @Override
    public void show() {}
    @Override
    public void hide() {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void dispose() {
        sr.dispose();
    }
}
