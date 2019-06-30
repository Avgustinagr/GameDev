package com.fmi.game.development.ryb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fmi.game.development.ryb.RYB;
import com.fmi.game.development.ryb.assets.Assets;

public class MenuScreen implements Screen {

    private RYB ryb;
    private SpriteBatch batch;
    private Texture splash;
    private OrthographicCamera camera;
    private BitmapFont font;

    public MenuScreen(RYB ryb) {
        this.ryb = ryb;
    }

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, RYB.WIDTH , RYB.HEIGHT);
        this.camera.update();
        this.splash = ryb.assets.manager.get(Assets.splash, Texture.class);
        this.font = new BitmapFont(Gdx.files.internal("bitmapfonts/furore.fnt"));
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0 / 255f, 51 / 255f, 102 / 255f, 1); // 	0, 51, 102
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();
        batch.draw(splash, 0, 0);
        this.drawHighScore();
        batch.end();


        if (Gdx.input.justTouched()) {
            ryb.gameState = RYB.GAME_STATE.PLAYING;
            ryb.setScreen(new GameScreen(ryb));
        }
    }

    private void drawHighScore() {
            GlyphLayout glyphLayout = new GlyphLayout();
            String highScore = "High Score " + this.ryb.getHighScore();
            glyphLayout.setText(font, highScore);
            float width = glyphLayout.width;
            font.draw(this.batch, glyphLayout, camera.position.x - width / 2, RYB.HEIGHT/5);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
    }
}
