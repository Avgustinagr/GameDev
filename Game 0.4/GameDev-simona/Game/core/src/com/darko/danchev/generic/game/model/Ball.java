package com.darko.danchev.generic.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.darko.danchev.generic.game.GenericGame;

import javax.xml.soap.Text;

public class Ball extends Image {

    private GenericGame genericGame;
    private World physicsWorld;
    private Body body;

    public Ball(GenericGame genericGame, World physicsWorld, Texture texture, float x, float y, float diameter){
        super(texture);
        this.genericGame = genericGame;
        this.physicsWorld = physicsWorld;
        this.setPosition(x,y);
        this.setSize(diameter,diameter);
        this.setOrigin(diameter,diameter);
        this.initBody();

    }

    private void initBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = physicsWorld.createBody(bodyDef);

        CircleShape bodyShape = new CircleShape();
        bodyShape.setRadius(getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 1.25f;
        fixtureDef.friction = 0.25f;
        fixtureDef.restitution = 0.5f; // 0 - 1f

        body.createFixture(fixtureDef);

        bodyShape.dispose();
    }

    public void jump(){
        body.applyForceToCenter(0,750f,false);
    }
    @Override
    public void act(float delta){
        this.setPosition(body.getPosition().x,body.getPosition().y);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }
}
