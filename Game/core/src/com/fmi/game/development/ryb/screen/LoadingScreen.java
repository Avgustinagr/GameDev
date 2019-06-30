package com.fmi.game.development.ryb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.fmi.game.development.ryb.RYB;
import com.fmi.game.development.ryb.assets.Assets;


public class LoadingScreen implements Screen {
    private RYB ryb;
    private SpriteBatch batch;
    private Image loadingscreen;
    private OrthographicCamera camera;
    private Stage stage;
    private Texture texture;
    private Sound loaded;

    public LoadingScreen(RYB ryb) {

        this.ryb = ryb;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, RYB.WIDTH , RYB.HEIGHT);
        this.camera.update();
        this.stage = new Stage(new FillViewport(ryb.WIDTH, ryb.HEIGHT));
        //this.loadingscreen = ryb.assets.manager.get(Assets.loadingscreen, Texture.class);
        this.camera.update();
        this.texture = new Texture("splash/loading.png");
        //this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.loadingscreen = new Image(texture);
        this.stage.addActor(loadingscreen);
        this.loadingscreen.setPosition(0,0);
        this.loaded = Gdx.audio.newSound(Gdx.files.internal("splash/loaded.ogg"));

    }


    @Override
    public void show() {
        this.loadingscreen.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1.5f), Actions.delay(4), Actions.run(
        new Runnable() {
            @Override
            public void run() {
                ryb.assets.load();
                while(!ryb.assets.manager.update())
                {
                    System.out.println("Loading: " + ryb.assets.manager.getProgress());
                }
                loaded.play(0.3f);
                ryb.gameState = RYB.GAME_STATE.MENU;
                ryb.setScreen(new MenuScreen(ryb));
                ryb.music = Gdx.audio.newMusic(Gdx.files.internal("splash/fantasytheme.mp3"));
                ryb.music.setLooping(true);
                ryb.music.play();
            }
        })));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0 / 255f, 51 / 255f, 102 / 255f, 1); // 	0, 51, 102
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();
        stage.act();
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
        this.stage.dispose();
    }
}
