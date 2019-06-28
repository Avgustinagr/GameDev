package com.fmi.game.development.ryb.model;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.fmi.game.development.ryb.GenericGame;
import com.fmi.game.development.ryb.assets.enums.Color;
import com.fmi.game.development.ryb.game.GameWorld;

public class EnemyBlock extends Image {

    private GenericGame genericGame;
    private World physicsWorld;
    private Body body;
    private Color color;
    private boolean scored;
    private GameWorld gameWorld;
    private float velocity;
    private int lastScoreChangedVelocity;

    public EnemyBlock(GenericGame genericGame, World physicsWorld, Texture appearance,
                      float x, float y, float width, float height, Color color, GameWorld gameWorld) {
        super(appearance);
        this.genericGame = genericGame;
        this.physicsWorld = physicsWorld;
        setPosition(x, y);
        setOrigin(x, y);
        setWidth(width);
        setHeight(height);
        this.color = color;
        this.scored = false;
        this.gameWorld = gameWorld;
        this.lastScoreChangedVelocity = 0;
        initBody();
    }

    public Color getEnemyColor() {
        return color;
    }

    public void die() {
        genericGame.gameState = GenericGame.GAME_STATE.MENU;
    }

    private void initBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = physicsWorld.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(getWidth() / 2, getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.1f;

        body.createFixture(fixtureDef);
        body.setUserData(this);
        this.velocity = -5f;
        body.setLinearVelocity(0,  this.velocity);

        bodyShape.dispose();
    }



    @Override
    public void act(float delta) {
        this.setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        changeVelocity();

    }

    public boolean isScored() {
        return this.scored;
    }

    public void makeScored() {
        this.scored = true;
    }

    private void changeVelocity() {
        int currentScore = this.gameWorld.getScore();

        if( this.velocity > -15f && currentScore%5 == 0 && currentScore > 0 && this.lastScoreChangedVelocity < currentScore) {
            this.velocity -= 1f;
            this.lastScoreChangedVelocity = currentScore;
        }

       this.body.setLinearVelocity(0, this.velocity);
    }
}
