package com.fmi.game.development.ryb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.fmi.game.development.ryb.RYB;
import com.fmi.game.development.ryb.assets.Assets;
import com.fmi.game.development.ryb.listener.SameColorsContactListener;
import com.fmi.game.development.ryb.model.EnemyBlock;
import com.fmi.game.development.ryb.model.Ball;
import com.fmi.game.development.ryb.screen.MenuScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.fmi.game.development.ryb.RYB.WORLD_HEIGHT;

public class GameWorld {

    private RYB ryb;
    private World physicsWorld;
    public Ball ball;
    public Stage stage;
    private float worldWidth;
    private EnemyBlock currentColorBlock;
    private int score = 0;
    private int blockVelocity;
    int maxEnemies = 2;
    List<EnemyBlock> enemies;
    //private SpriteBatch batch;
    //private Box2DDebugRenderer debugRenderer;
    //private Box2DDebugRenderer debugRenderer;

    public GameWorld(RYB ryb) {
        this.blockVelocity = -4;

        this.ryb = ryb;
        this.physicsWorld = new World(new Vector2(0, -0.5f), true);
        this.physicsWorld.setContactListener(new SameColorsContactListener(this));
        float ratio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        this.worldWidth = WORLD_HEIGHT / ratio;
        this.ball = new Ball(ryb, physicsWorld, ryb.assets.manager.get(Assets.player, Texture.class),
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
        if (ryb.gameState == RYB.GAME_STATE.MENU) {

            if (ryb.highScore < this.score) {
                ryb.highScore = this.score;
                ryb.updateHighScore(this.score);
            }

            ryb.setScreen(new MenuScreen(ryb));
        }
    }

    private void initEnemy() {
        enemies = new ArrayList<EnemyBlock>(2);

        Random random = new Random();
        int index = random.nextInt(7);

        EnemyBlock enemy = new EnemyBlock(ryb, physicsWorld,
                ryb.assets.manager.get(Assets.blocks[index].getFilename(), Texture.class),
                worldWidth / 2, WORLD_HEIGHT, worldWidth, WORLD_HEIGHT / 20, Assets.blocks[index].getColor(), this);

        enemies.add(enemy);


        index = random.nextInt(7);

        EnemyBlock second = new EnemyBlock(ryb, physicsWorld,
                ryb.assets.manager.get(Assets.blocks[index].getFilename(), Texture.class),
                worldWidth / 2,WORLD_HEIGHT + 15, worldWidth, WORLD_HEIGHT / 20, Assets.blocks[index].getColor(), this);

        enemies.add(second);

     //   stage.addActor(enemies.get(0));

        /* for(int i = 0; i < 2; i++) {


            this.currentColorBlock = ;
            float ratio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
            float worldWidth = GenericGame.WORLD_HEIGHT / ratio;
            this.currentColorBlock.setSize(worldWidth + 1, 1.5f);
            stage.addActor(this.currentColorBlock);
        }*/

    }

    private void regenerateEnemy() {

        if (enemies.get(0).getY() < (stage.getCamera().position.y - worldWidth / 2)) {
           enemies.get(0).remove();
            enemies.remove(0);
            System.out.println("removed");

        }
        if (enemies.size() == 1) {

            Random random = new Random();
            int index = random.nextInt(7);
            EnemyBlock enemy = new EnemyBlock(ryb, physicsWorld,
                    ryb.assets.manager.get(Assets.blocks[index].getFilename(), Texture.class),
                    worldWidth / 2, enemies.get(enemies.size() - 1).getY() + 15, worldWidth, WORLD_HEIGHT / 20, Assets.blocks[index].getColor(), this);
            enemies.add(enemy);
        }
    }


    public int getScore() {
        return score;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public float getWorldWidth() {
        return this.worldWidth;
    }

    public int getVelocity() {

        return this.blockVelocity;
    }

    public void decreaseVelocity() {
        this.blockVelocity -= 1;
    }
}
