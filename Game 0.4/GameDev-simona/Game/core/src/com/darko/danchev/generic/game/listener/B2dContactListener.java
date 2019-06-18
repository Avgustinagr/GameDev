package com.darko.danchev.generic.game.listener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.darko.danchev.generic.game.model.Enemy;
import com.darko.danchev.generic.game.model.Player;

public class B2dContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        String classA = contact.getFixtureA().getBody().getUserData().getClass().getName();
        String classB = contact.getFixtureB().getBody().getUserData().getClass().getName();

        if(classA.equals("com.darko.danchev.generic.game.model.Player") && classB.equals("com.darko.danchev.generic.game.model.Enemy")){
            Player player = (Player)(contact.getFixtureA().getBody().getUserData());
            Enemy enemy = (Enemy) (contact.getFixtureB().getBody().getUserData());
            if (player.getColormove() != enemy.getEnemyColor()){
                player.die();
                System.out.println("dead");
            }
            else
            {
                System.out.println("alive");
                contact.setEnabled(false);
            }
        }
        else if(classA.equals("com.darko.danchev.generic.game.model.EnemyWall") && classB.equals("com.darko.danchev.generic.game.model.Player")){
            Player player = (Player)(contact.getFixtureB().getBody().getUserData());
            player.die();

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
}
