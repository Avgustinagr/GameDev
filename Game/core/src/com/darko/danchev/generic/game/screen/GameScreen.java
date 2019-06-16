package com.darko.danchev.generic.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.darko.danchev.generic.game.GenericGame;
import com.darko.danchev.generic.game.assets.Assets;
import com.darko.danchev.generic.game.game.GameWorld;

import java.util.ArrayList;
import java.util.List;

import static com.darko.danchev.generic.game.GenericGame.WORLD_HEIGHT;

public class GameScreen implements Screen {

    private GenericGame genericGame;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameWorld gameWorld;
    private Texture background;
    private List<ImageButton> buttons;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
    private Stage stage;



    public GameScreen(GenericGame genericGame) {

        this.genericGame = genericGame;

    }

    @Override
    public void show() {

        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false,GenericGame.WIDTH,GenericGame.HEIGHT);
        this.gameWorld = new GameWorld(this.genericGame);
        this.background = genericGame.assets.manager.get(Assets.background, Texture.class);

        float ratio = (float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
        float worldWidth = WORLD_HEIGHT / ratio;
        this.stage = new Stage(new StretchViewport(worldWidth, WORLD_HEIGHT));
        stage.clear();
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
        float third = worldWidth/3;
        for(ImageButton button: buttons){
            button.setPosition(i, -1);
            button.setSize(third, 5);
            this.stage.addActor(button);
            i = i+ third;
        }

        //buttons.add(genericGame.assets.manager.get(Assets.redbutton, Texture.class));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(55/255f, 51/255f, 102/255f, 1); // 	0, 51, 102
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background,0,0);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        gameWorld.render();
        gameWorld.update();
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
