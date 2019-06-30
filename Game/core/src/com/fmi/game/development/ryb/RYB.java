package com.fmi.game.development.ryb;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.fmi.game.development.ryb.assets.Assets;
import com.fmi.game.development.ryb.screen.LoadingScreen;
import com.fmi.game.development.ryb.screen.MenuScreen;

public class RYB extends Game {

    public static float WIDTH = 2520; //pixels
    public static float HEIGHT = 4160;
    public static float WORLD_HEIGHT = 20; // the unit is meters
    public Assets assets;
    public GAME_STATE gameState;
    private int highScore = 0;
    private Preferences preferences;
    public Music music;

    @Override
    public void create() {

        this.assets = new Assets();
        this.setScreen(new LoadingScreen(this));
        //this.assets.load(); // ASYNC
        while (!this.assets.manager.update()) {
            System.out.println("Loading: " + this.assets.manager.getLoadedAssets());
        }
        this.preferences = Gdx.app.getPreferences("highScorePreferences");

        if (preferences.contains("highScore")) {
            this.highScore = preferences.getInteger("highScore", 0);
        } else {
            updateHighScore(0);
            this.highScore = 0;
        }

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        this.assets.dispose();
        music.dispose();
    }

    public void updateHighScore(int newHighScore) {
        preferences.putInteger("highScore", newHighScore);
        preferences.flush();
    }

    public int getHighScore() {
        return this.highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public enum GAME_STATE {
        PLAYING,
        MENU
    }

}
