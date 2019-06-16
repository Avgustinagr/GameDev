package com.darko.danchev.generic.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.darko.danchev.generic.game.GenericGame;

public class Wall extends Image {

    private GenericGame genericGame;
    private World physicsWorld;
    private Body body;

    public Wall(GenericGame genericGame, World physicsWorld, Texture texture,float x,float y,
                float width,float height){
        super(texture);
        this.genericGame = genericGame;
        this.physicsWorld = physicsWorld;
        this.setPosition(x,y);
        this.setOrigin(x,y);
        this.setSize(width,height);
        this.initBody();
    }

    private void initBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = physicsWorld.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(getWidth(),getHeight());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 10f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.25f;

        body.createFixture(fixtureDef);

        bodyShape.dispose();
    }

    @Override
    public void act(float delta){
        this.setPosition(body.getPosition().x + getWidth() / 2,body.getPosition().y + getHeight() / 2);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }
}
