package com.darko.danchev.generic.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.darko.danchev.generic.game.GenericGame;
import com.darko.danchev.generic.game.assets.enums.Color;


public class Player extends Image {

    private GenericGame genericGame;
    private World physicsWorld;
    private Body body;
    private boolean transition;
    private Color colormove;

    public Player(GenericGame genericGame, World physicsWorld, Texture appearance,float x, float y,
                  float width, float height){
        super(appearance);
        this.setX(x);
        this.setY(y);
        this.setOrigin(x,y);
        this.setWidth(width);
        this.setHeight(height);
        this.genericGame = genericGame;
        this.physicsWorld = physicsWorld;
        this.initBody();
        this.transition = false;
        this.colormove = Color.RED;
    }

    public Color getColormove(){
        return this.colormove;
    }

    public void setColormove(Color colormove){
        this.colormove = colormove;
    }

    private void repositionBody(float y){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(body.getPosition().x + 0.25f,y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        physicsWorld.destroyBody(body);


        body = physicsWorld.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(getWidth() / 2,getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        body.setUserData(this);
        body.setLinearVelocity(5f,0);

        bodyShape.dispose();


    }
    private void initBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = physicsWorld.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(getWidth() / 2,getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        body.setUserData(this);
        body.setLinearVelocity(0,0);

        bodyShape.dispose();

    }

    @Override
    public void act(float delta){
        this.setPosition(body.getPosition().x - getWidth() / 2,body.getPosition().y - getHeight() / 2);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

       /* if(getY() > GenericGame.WORLD_HEIGHT) {
            repositionBody(-getHeight());
            transition = true;
        }else if(getY() < -2*getHeight()){
            repositionBody(GenericGame.WORLD_HEIGHT);
            transition = true;
        } else {
            transition = false;
        }*/
    }
    public boolean getTransition(){
        return this.transition;
    }


    public void die(){
        genericGame.gameState = GenericGame.GAME_STATE.MENU;
    }

}
