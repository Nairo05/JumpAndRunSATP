package de.dhbw.satp.world;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import de.dhbw.satp.gameobjects.AsyncContextWarning;
import de.dhbw.satp.screens.PlayScreen;

public class MyContactListener implements ContactListener {

    private final PlayScreen playScreen;
    private short playerOnGround = 0;

    public MyContactListener(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    @AsyncContextWarning
    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getFilterData().categoryBits + contact.getFixtureB().getFilterData().categoryBits == BitFilterDef.PLAYER_ON_GROUND) {
            playerOnGround ++;
            playScreen.getPlayer().jumped();
        }
    }

    @AsyncContextWarning
    @Override
    public void endContact(Contact contact) {
        if (contact.getFixtureA().getFilterData().categoryBits + contact.getFixtureB().getFilterData().categoryBits == BitFilterDef.PLAYER_ON_GROUND) {
            playerOnGround --;
        }

    }

    @AsyncContextWarning
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @AsyncContextWarning
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerOnGround() {
        return playerOnGround > 0;
    }


}
