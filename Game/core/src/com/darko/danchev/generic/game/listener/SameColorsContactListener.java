package com.darko.danchev.generic.game.listener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.darko.danchev.generic.game.game.GameWorld;
import com.darko.danchev.generic.game.model.EnemyBlock;
import com.darko.danchev.generic.game.model.Ball;

public class SameColorsContactListener implements ContactListener {

    private GameWorld gameWorld;

    public SameColorsContactListener(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    public void beginContact(Contact contact) {
        String classA = contact.getFixtureA().getBody().getUserData().getClass().getName();
        String classB = contact.getFixtureB().getBody().getUserData().getClass().getName();

        if (classA.equals("com.darko.danchev.generic.game.model.EnemyBlock") && classB.equals("com.darko.danchev.generic.game.model.Ball")) {

            EnemyBlock colorBlock = (EnemyBlock) (contact.getFixtureA().getBody().getUserData());
            Ball ball = (Ball) (contact.getFixtureB().getBody().getUserData());
            System.out.println(ball.getBackgroundColor());
            System.out.println(colorBlock.getEnemyColor());

            if (ball.getBackgroundColor() != colorBlock.getEnemyColor()) {
                ball.setBackgroundColorToColorless();
                ball.die();
            } else {

                updateScore();
                ball.setBackgroundColorToColorless();
                contact.setEnabled(false);
            }
        }
      /*  else if(classA.equals("com.darko.danchev.generic.game.model.ColorBlock") && classB.equals("com.darko.danchev.generic.game.model.Ball")){
            Ball ball = (Ball)(contact.getFixtureB().getBody().getUserData());
            ball.die();

        }*/
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
        gameWorld.setScore(score);
    }

}
