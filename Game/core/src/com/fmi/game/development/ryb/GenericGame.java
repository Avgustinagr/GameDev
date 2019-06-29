package com.fmi.game.development.ryb;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.fmi.game.development.ryb.assets.Assets;
import com.fmi.game.development.ryb.screen.MenuScreen;

public class GenericGame extends Game {

    public enum GAME_STATE {
        PLAYING,
        MENU,
        PAUSE;
    }

    public static float WIDTH = 2520; //pixels
    public static float HEIGHT = 4160;

    public static float WORLD_HEIGHT = 20; // the unit is meters

    public Assets assets;

    public GAME_STATE gameState;
    public int highScore = 0;
    private Preferences preferences;

    private Music music;

    @Override
    public void create() {

        this.assets = new Assets();
        this.assets.load(); // ASYNC
        while (!this.assets.manager.update()) {
            System.out.println("Loading: " + this.assets.manager.getLoadedAssets());
        }
        this.gameState = GAME_STATE.MENU;
        this.preferences = Gdx.app.getPreferences("highScorePreferences");

        if (preferences.contains("highScore")) {
            this.highScore = preferences.getInteger("highScore", 0);
        } else {
            updateHighScore(0);
            this.highScore = 0;
        }

        music = Gdx.audio.newMusic(Gdx.files.internal("splash/fantasytheme.mp3"));
        music.setLooping(true);
        music.play();


        this.setScreen(new MenuScreen(this));
    }

    public void updateHighScore(int newHighScore) {
        preferences.putInteger("highScore", newHighScore);
        preferences.flush();
    }

    @Override
    public void render() {
        switch (gameState){
            case PLAYING:
                super.render(); break;
            case MENU:
                super.render(); break;
            case PAUSE:
                Gdx.gl.glClearColor(34 / 255f, 34 / 255f, 34 / 255f, 1); // 	0, 51, 102
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                if (Gdx.input.isKeyPressed(Input.Keys.Z)){
                    gameState = GAME_STATE.PLAYING;
                }
                break;
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        this.assets.dispose();
        music.dispose();
    }

}
