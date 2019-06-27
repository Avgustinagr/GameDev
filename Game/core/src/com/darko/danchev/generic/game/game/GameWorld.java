package com.darko.danchev.generic.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.darko.danchev.generic.game.GenericGame;
import com.darko.danchev.generic.game.assets.Assets;
import com.darko.danchev.generic.game.listener.B2dContactListener;
import com.darko.danchev.generic.game.model.Enemy;
import com.darko.danchev.generic.game.model.Player;
import com.darko.danchev.generic.game.screen.MenuScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.darko.danchev.generic.game.GenericGame.WORLD_HEIGHT;

public class GameWorld {

    private GenericGame genericGame;
    private World physicsWorld;
    public Player player;
    private Stage stage;
    private float worldWidth;
    private Enemy currentEnemy;
    private int score;

    //private SpriteBatch batch;
    //private Box2DDebugRenderer debugRenderer;

    public GameWorld(GenericGame genericGame){
        this.genericGame = genericGame;
        this.physicsWorld = new World(new Vector2(0,1f),true);
        this.physicsWorld.setContactListener(new B2dContactListener(this));
        float ratio = (float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
        this.worldWidth = WORLD_HEIGHT / ratio;
        this.player = new Player(genericGame,physicsWorld,genericGame.assets.manager.get(Assets.player, Texture.class),
                worldWidth /2, WORLD_HEIGHT / 2 ,2,2);
        this.stage = new Stage(new StretchViewport(worldWidth, WORLD_HEIGHT));

        this.stage.addActor(player);

        this.initEnemy();
        this.score = 0;
        //this.batch = new SpriteBatch();
        //this.debugRenderer = new Box2DDebugRenderer();

    }

    public void render(){
        this.stage.draw();
        physicsWorld.step(Gdx.graphics.getDeltaTime(),6,2);
    }

    public void update(){
        this.stage.act();
        if(!this.player.getTransition()) {
            this.stage.getCamera().position.y = player.getY();
        }
        /*if(Gdx.input.justTouched()){
            this.player.jump(1);
        }*/
       /* if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.player.jump(0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.player.jump(1);
        }
*/

        this.regenerateEnemy();
        if(genericGame.gameState == GenericGame.GAME_STATE.MENU){

            if(genericGame.highScore < score){
                genericGame.highScore = score;
                genericGame.updateHighScore(score);
            }

            genericGame.setScreen(new MenuScreen(genericGame));
        }
    }

    private void initEnemy(){

        Random random = new Random();
        int index = random.nextInt(4);
        this.currentEnemy = new Enemy(genericGame, physicsWorld,
                genericGame.assets.manager.get(Assets.blocks[index].getFilename(), Texture.class),
                8.5f,  15 , 15, 1.4f, Assets.blocks[index].getColor());
        float ratio = (float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
        float worldWidth = GenericGame.WORLD_HEIGHT / ratio;
        this.currentEnemy.setSize(worldWidth + 1, 1.5f);
        stage.addActor(this.currentEnemy);

    }

    private void regenerateEnemy(){
        /*if(enemyWalls.get(0).getY() < stage.getCamera().position.y - WORLD_HEIGHT / 2){
            enemyWalls.remove(0);
        }
        if(enemyWalls.size() == 7){
            EnemyWall enemyWall = new EnemyWall(genericGame,physicsWorld,stage,enemyWalls.get(enemyWalls.size() -1).getX(), enemyWalls.get(enemyWalls.size() -1).getY() + 15);
            enemyWalls.add(enemyWall);
        }
*/
        Random random = new Random();
        int index = random.nextInt(4);
        this.currentEnemy = new Enemy(genericGame, physicsWorld,
                genericGame.assets.manager.get(Assets.blocks[index].getFilename(), Texture.class),
                8.5f,  this.currentEnemy.getY() + 15 , 15, 1.4f, Assets.blocks[index].getColor());
        float ratio = (float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
        float worldWidth = GenericGame.WORLD_HEIGHT / ratio;
        currentEnemy.setSize(worldWidth+1, 1.5f);
        stage.addActor(currentEnemy);
    }


    public int getScore() {
        return score;
    }


    public void setScore(int score) {
        this.score = score;
    }
}