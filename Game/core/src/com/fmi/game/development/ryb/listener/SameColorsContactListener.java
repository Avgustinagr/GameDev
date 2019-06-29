package com.fmi.game.development.ryb.listener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.fmi.game.development.ryb.game.GameWorld;
import com.fmi.game.development.ryb.model.Ball;
import com.fmi.game.development.ryb.model.EnemyBlock;

public class SameColorsContactListener implements ContactListener {

    private GameWorld gameWorld;

    public SameColorsContactListener(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    public void beginContact(Contact contact) {
        String classA = contact.getFixtureA().getBody().getUserData().getClass().getName();
        String classB = contact.getFixtureB().getBody().getUserData().getClass().getName();

        if (classA.equals("com.fmi.game.development.ryb.model.EnemyBlock")
                && classB.equals("com.fmi.game.development.ryb.model.Ball")) {

            EnemyBlock colorBlock = (EnemyBlock) (contact.getFixtureA().getBody().getUserData());
            Ball ball = (Ball) (contact.getFixtureB().getBody().getUserData());


            if (ball.getBackgroundColor() != colorBlock.getEnemyColor()) {
                ball.die();
            } else {

                updateScore();
                ball.setBackgroundColorToColorless();
                contact.setEnabled(false);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        contact.setEnabled(false);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        contact.setEnabled(false);
    }

    private void updateScore() {
        int score = gameWorld.getScore() + 1;
        if (score % 5 == 0) {
            gameWorld.decreaseVelocity();
        }
        gameWorld.setScore(score);
    }

}
