package com.darko.danchev.generic.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.darko.danchev.generic.game.GenericGame;
import com.darko.danchev.generic.game.assets.Assets;
import com.darko.danchev.generic.game.listener.SameColorsContactListener;
import com.darko.danchev.generic.game.model.EnemyBlock;
import com.darko.danchev.generic.game.model.Ball;
import com.darko.danchev.generic.game.screen.MenuScreen;

import java.util.Random;

import static com.darko.danchev.generic.game.GenericGame.WORLD_HEIGHT;

public class GameWorld {

    private GenericGame genericGame;
    private World physicsWorld;
    public Ball ball;
    private Stage stage;
    private float worldWidth;
    private EnemyBlock currentColorBlock;
    private int score;

    //private SpriteBatch batch;
    //private Box2DDebugRenderer debugRenderer;
    //private Box2DDebugRenderer debugRenderer;

    public GameWorld(GenericGame genericGame) {
        this.genericGame = genericGame;
        this.physicsWorld = new World(new Vector2(0, -0.5f), true);
        this.physicsWorld.setContactListener(new SameColorsContactListener(this));
        float ratio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        this.worldWidth = WORLD_HEIGHT / ratio;
        this.ball = new Ball(genericGame, physicsWorld, genericGame.assets.manager.get(Assets.player, Texture.class),
                worldWidth / 2 - 1, WORLD_HEIGHT / 3, 2, 2);
        this.stage = new Stage(new StretchViewport(worldWidth, WORLD_HEIGHT));

        this.stage.addActor(ball);

        this.initEnemy();
        this.score = 0;
        //this.batch = new SpriteBatch();
        //this.debugRenderer = new Box2DDebugRenderer();

    }

    public void render() {
        this.stage.draw();
        physicsWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    public void update() {
        this.stage.act();
/*        if(!this.ball.getTransition()) {
            this.stage.getCamera().position.y = ball.getY();
        }*/

        this.regenerateEnemy();
        if (genericGame.gameState == GenericGame.GAME_STATE.MENU) {

            if (genericGame.highScore < score) {
                genericGame.highScore = score;
                genericGame.updateHighScore(score);
            }

            genericGame.setScreen(new MenuScreen(genericGame));
        }
    }

    private void initEnemy() {

        Random random = new Random();
        int index = random.nextInt(4);
        this.currentColorBlock = new EnemyBlock(genericGame, physicsWorld,
                genericGame.assets.manager.get(Assets.blocks[index].getFilename(), Texture.class),
                worldWidth / 2, WORLD_HEIGHT, worldWidth, WORLD_HEIGHT / 20, Assets.blocks[index].getColor());
        float ratio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        float worldWidth = GenericGame.WORLD_HEIGHT / ratio;
        this.currentColorBlock.setSize(worldWidth + 1, 1.5f);
        stage.addActor(this.currentColorBlock);

    }

    private void regenerateEnemy() {
        Random random = new Random();
        int index = random.nextInt(4);
        this.currentColorBlock = new EnemyBlock(genericGame, physicsWorld,
                genericGame.assets.manager.get(Assets.blocks[index].getFilename(), Texture.class),
                8.5f, this.currentColorBlock.getY() + 15, 15, 1.4f, Assets.blocks[index].getColor());
        float ratio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        float worldWidth = GenericGame.WORLD_HEIGHT / ratio;
        currentColorBlock.setSize(worldWidth + 1, 1.5f);
        stage.addActor(currentColorBlock);
    }


    public int getScore() {
        return score;
    }


    public void setScore(int score) {
        this.score = score;
    }
}
