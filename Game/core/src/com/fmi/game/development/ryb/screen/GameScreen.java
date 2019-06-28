package com.fmi.game.development.ryb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.fmi.game.development.ryb.GenericGame;
import com.fmi.game.development.ryb.assets.Assets;
import com.fmi.game.development.ryb.assets.enums.Color;
import com.fmi.game.development.ryb.game.GameWorld;

import java.util.ArrayList;
import java.util.List;

import static com.fmi.game.development.ryb.GenericGame.WORLD_HEIGHT;

public class GameScreen implements Screen {

    private GenericGame genericGame;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameWorld gameWorld;
    private List<ImageButton> buttons;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
    private Stage stage;
    private BitmapFont font;

    public GameScreen(GenericGame genericGame) {

        this.genericGame = genericGame;

    }

    @Override
    public void show() {

        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, GenericGame.WIDTH, GenericGame.HEIGHT);
        this.gameWorld = new GameWorld(this.genericGame);
        this.font = new BitmapFont(Gdx.files.internal("bitmapfonts/furore.fnt"));

        float ratio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        float worldWidth = WORLD_HEIGHT / ratio;
        this.stage = new Stage(new StretchViewport(worldWidth, WORLD_HEIGHT));
        stage.clear();
        setUpButtons(worldWidth);

        //buttons.add(genericGame.assets.manager.get(Assets.redbutton, Texture.class));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(34 / 255f, 34 / 255f, 34 / 255f, 1); // 	0, 51, 102
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        changeBackground();
        //batch.draw(background,0,0);
        batch.end();
        gameWorld.render();

        batch.begin();
        drawScore();
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.X)){
            genericGame.gameState = GenericGame.GAME_STATE.PAUSE;
        }



        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        gameWorld.update();
        gameWorld.update();
    }

    private void helperChangeBackground(int r, int g, int b, Color backgroundColor) {
        /* Helps to change background according to the given color
        * Used in - flagFilterBackground() */
        Gdx.gl.glClearColor(r/ 255f, g / 255f, b / 255f, 1); //
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameWorld.ball.setBackgroundColor(backgroundColor);
    }

    private void flagFilterBackground( int flag){
        /* Helps to change backhound according to the given flag
        * Used in - changeBackground() */

        switch (flag){
            case 1:
                helperChangeBackground(242, 4, 48, Color.RED); break;
            case 3:
                helperChangeBackground(242, 182, 4, Color.YELLOW); break;
            case 5:
                helperChangeBackground(4, 107, 242, Color.BLUE); break;
            case 4:
                helperChangeBackground(237, 87, 20, Color.ORANGE); break;
            case 6:
                helperChangeBackground(117, 60, 143, Color.PURPLE); break;
            case 8:
                helperChangeBackground(100, 180, 96, Color.GREEN); break;
        }
    }

    private void changeBackground() {
        int flag = 0;
        /*
         RED = 1
         YELLOW = 3
         BLUE = 5
         ORANGE = 4  (RED + YELLOW)
         PURPLE = 6 (RED + BLUE)
         GREEN = 8 (BLUE + YELLOW)
         */
        // red part
        if ((Gdx.input.getX() < Gdx.graphics.getWidth() / 3f
                && Gdx.input.isTouched()) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {

            flag += 1;
        }
        //yellow part
        if ((Gdx.input.getX() > (Gdx.graphics.getWidth() / 3f) * 2
                && Gdx.input.isTouched()) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {

            flag += 3;
        }

        // blue part
        if (((Gdx.input.getX() > Gdx.graphics.getWidth() / 3f && Gdx.input.getX() < (Gdx.graphics.getWidth() / 3f) * 2)
                && Gdx.input.isTouched()) || Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {

            flag += 5;
        }
        gameWorld.ball.setBackgroundColor(Color.COLORLESS);
        flagFilterBackground(flag);

    }

    private void setUpButtons(float worldWidth) {
        /* Sets up colour menu at the bottom */

        buttons = new ArrayList<ImageButton>(3);
        myTextureRegion = new TextureRegion(genericGame.assets.manager.get(Assets.redbutton, Texture.class));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable); //Set the button up
        buttons.add(button);
        myTextureRegion = new TextureRegion(genericGame.assets.manager.get(Assets.bluebutton, Texture.class));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable);
        buttons.add(button);
        myTextureRegion = new TextureRegion(genericGame.assets.manager.get(Assets.yellowbutton, Texture.class));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable);
        buttons.add(button);
        float i = 0;
        float third = worldWidth / 3;
        for (ImageButton button : buttons) {
            button.setPosition(i, -1);
            button.setSize(third, 5);
            this.stage.addActor(button);
            i = i + third;
        }
    }


    private void drawScore() {
        GlyphLayout glyphLayout = new GlyphLayout();
        String currentScore = "Score " + this.gameWorld.getScore();
        glyphLayout.setText(font, currentScore);
        float width = glyphLayout.width;
        font.draw(this.batch, glyphLayout, camera.position.x - width / 2, GenericGame.HEIGHT - 50);
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
