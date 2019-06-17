package com.darko.danchev.generic.game.model;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.darko.danchev.generic.game.GenericGame;

public class Enemy extends Image {

    private GenericGame genericGame;
    private World physicsWorld;
    private Body body;

    public Enemy(GenericGame genericGame, World physicsWorld, Texture appearance,
                 float x, float y, float width, float height){
        super(appearance);
        this.genericGame = genericGame;
        this.physicsWorld = physicsWorld;
        setPosition(x,y);
        setOrigin(x,y);
        setWidth(width);
        setHeight(height);

        initBody();
    }

    private void initBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;

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

        bodyShape.dispose();
    }

    @Override
    public void act(float delta){
        this.setPosition(body.getPosition().x - getWidth() / 2,body.getPosition().y - getHeight() / 2);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }
}
