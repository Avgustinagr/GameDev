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

    public static final float MAX_VELOCITY = -9f;

    private final int MAX_NUMBER_OF_ENEMIES_ON_SCREEN = 2;
    private final int MAX_NUMBER_OF_BLOCKS = 36;
    private final int BLOCK_HEIGHT_DIFFERENCE = 15;
    private float INITIAL_VELOCITY = -4f;
    private final float VELOCITY_DIFFERENCE = 0.5f;

    private RYB ryb;
    private World physicsWorld;
    private Ball ball;
    public Stage stage;
    private float worldWidth;
    private int score;
    private float blockVelocity;
    List<EnemyBlock> enemies;

    private float blockWidth;
    private float blockHeight;

    public GameWorld(RYB ryb) {
        this.blockVelocity = INITIAL_VELOCITY;
        this.ryb = ryb;
        this.physicsWorld = new World(new Vector2(0, -0.5f), true);
        this.physicsWorld.setContactListener(new SameColorsContactListener(this));
        float ratio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        this.worldWidth = WORLD_HEIGHT / ratio;
        this.ball = new Ball(ryb, physicsWorld, ryb.assets.manager.get(Assets.player, Texture.class),
                worldWidth / 2 - 1 , WORLD_HEIGHT / 3, 2, 2);
        this.stage = new Stage(new StretchViewport(worldWidth, WORLD_HEIGHT));

        this.blockWidth = worldWidth + 1;
        this.blockHeight = WORLD_HEIGHT / 20 + 0.5f;

        this.stage.addActor(ball);
        this.initEnemy();
        this.score = 0;
    }

    private void initEnemy() {
        enemies = new ArrayList<EnemyBlock>(MAX_NUMBER_OF_ENEMIES_ON_SCREEN);

        Random random = new Random();
        int index = random.nextInt(MAX_NUMBER_OF_BLOCKS);

        float blockX = worldWidth / 2;
        float blockY = WORLD_HEIGHT;

        for(int i = 0; i < MAX_NUMBER_OF_ENEMIES_ON_SCREEN; i++) {
            EnemyBlock enemy = new EnemyBlock(ryb, physicsWorld,
                    ryb.assets.manager.get(Assets.blocks[index].getFilename(), Texture.class),
                    blockX, blockY , this.blockWidth, this.blockHeight, Assets.blocks[index].getColor(), this);
            blockY += BLOCK_HEIGHT_DIFFERENCE;
            enemies.add(enemy);
            index = random.nextInt(MAX_NUMBER_OF_BLOCKS);
        }

    }

    public void render() {
        this.stage.draw();
        physicsWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    public void update() {

        this.stage.act();
        this.regenerateEnemy();
        int highScore = ryb.getHighScore();

        if (ryb.gameState == RYB.GAME_STATE.MENU) {

            if (highScore < this.score) {
                ryb.setHighScore(this.score);
                ryb.updateHighScore(this.score);
            }

            ryb.setScreen(new MenuScreen(ryb));
        }
    }


    private void regenerateEnemy() {

        if (enemies.get(0).getY() < (stage.getCamera().position.y - worldWidth / 2)) {
            enemies.get(0).remove();
            enemies.remove(0);
        }
        if (enemies.size() == 1) {

            Random random = new Random();
            int index = random.nextInt(MAX_NUMBER_OF_BLOCKS);
            EnemyBlock enemy = new EnemyBlock(ryb, physicsWorld,
                    ryb.assets.manager.get(Assets.blocks[index].getFilename(), Texture.class),
                    worldWidth / 2, enemies.get(enemies.size() - 1).getY() + BLOCK_HEIGHT_DIFFERENCE, this.blockWidth, this.blockHeight, Assets.blocks[index].getColor(), this);
            enemies.add(enemy);
        }
    }


    public int getScore() {
        return score;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public float getVelocity() {
        return this.blockVelocity;
    }

    public void decreaseVelocity() {
        this.blockVelocity -= VELOCITY_DIFFERENCE;
    }

    public Ball getBall() {
        return this.ball;
    }
}
