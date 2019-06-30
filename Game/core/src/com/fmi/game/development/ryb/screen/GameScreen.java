package com.fmi.game.development.ryb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.fmi.game.development.ryb.RYB;
import com.fmi.game.development.ryb.game.GameWorld;

import java.util.List;

import static com.fmi.game.development.ryb.RYB.WORLD_HEIGHT;


public class GameScreen implements Screen {

    private RYB ryb;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameWorld gameWorld;
    private List<ImageButton> buttons;

    private Stage stage;
    private BitmapFont font;
    private ButtonsHandler buttonsHandler;

    public GameScreen(RYB ryb) {
        this.ryb = ryb;
    }

    @Override
    public void show() {

        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, RYB.WIDTH, RYB.HEIGHT);
        this.gameWorld = new GameWorld(this.ryb);
        this.font = new BitmapFont(Gdx.files.internal("bitmapfonts/furore.fnt"));

        float ratio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        float worldWidth = WORLD_HEIGHT * ratio;
        this.stage = new Stage(new StretchViewport(worldWidth, WORLD_HEIGHT));
        stage.clear();

        this.buttonsHandler = new ButtonsHandler(worldWidth, this.stage, this.ryb, this.gameWorld);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(34 / 255f, 34 / 255f, 34 / 255f, 1); //     0, 51, 102
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        buttonsHandler.changeBackground();
        batch.end();
        gameWorld.render();

        batch.begin();
        drawScore();
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        gameWorld.update();
    }


    private void drawScore() {
        GlyphLayout glyphLayout = new GlyphLayout();
        String currentScore = "Score " + this.gameWorld.getScore();
        glyphLayout.setText(font, currentScore);
        float width = glyphLayout.width;
        font.draw(this.batch, glyphLayout, camera.position.x - width / 2, RYB.HEIGHT - 50);
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
        batch.dispose();
    }
}