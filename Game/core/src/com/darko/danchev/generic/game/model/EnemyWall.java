package com.darko.danchev.generic.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.darko.danchev.generic.game.GenericGame;
import com.darko.danchev.generic.game.assets.Assets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.darko.danchev.generic.game.GenericGame.WORLD_HEIGHT;

public class EnemyWall {

    private GenericGame genericGame;
    private World physicsWorld;
    private List<Enemy> enemyList;
    private float x;
    private  float y;
    private Stage stage;

    public EnemyWall(GenericGame genericGame, World physicsWorld,Stage stage,float x, float y){
        this.genericGame = genericGame;
        this.physicsWorld = physicsWorld;
        this.stage = stage;
        this.x = x;
        this.y = y;
        initWall();
    }

   /* private void initWall(){
        enemyList = new ArrayList<Enemy>(5);
        Random random = new Random();
        int index = random.nextInt(5);
        for(int i = 0; i < 5; i++){
            if(i != index) {
                Enemy enemy = new Enemy(genericGame, physicsWorld,
                        genericGame.assets.manager.get(Assets.enemy, Texture.class),
                        i*2 + 2, y , 2, 2);
                enemyList.add(enemy);
            }
        }
        for(Enemy e: enemyList){
            stage.addActor(e);
        }

    }*/

   private void initWall(){
       Random random = new Random();
       int index = random.nextInt(7);
       Enemy enemy = new Enemy(genericGame, physicsWorld,
               genericGame.assets.manager.get(Assets.enemies[index], Texture.class),
               8.5f, y , 15, 1.4f);
       float ratio = (float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
       float worldWidth = GenericGame.WORLD_HEIGHT / ratio;
       enemy.setSize(worldWidth+1, 1.5f);
       stage.addActor(enemy);
   }

    public float getX(){
        return x;
    }


    public float getY(){
        return y;
    }

}
