package com.tibiagame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TibiaGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public Art art;
    public SoundManager sound;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        art = new Art();
        sound = new SoundManager();
        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        art.dispose();
        sound.dispose();
        if (screen != null) screen.dispose();
    }
}
