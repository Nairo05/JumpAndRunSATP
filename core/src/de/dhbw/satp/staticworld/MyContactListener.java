package de.dhbw.satp.staticworld;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import org.graalvm.compiler.core.phases.EconomyCompilerConfiguration;

import de.dhbw.satp.gameobjects.DynamicEntity;
import de.dhbw.satp.gameobjects.Enemy;
import de.dhbw.satp.gameobjects.Player;
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
        int checksum = contact.getFixtureA().getFilterData().categoryBits + contact.getFixtureB().getFilterData().categoryBits;

        if (checksum == BitFilterDef.PLAYER_ON_GROUND) {
            playerOnGround ++;
        }
        if (checksum == BitFilterDef.PLAYER_CO_ENEMY) {
            playScreen.getPlayer().hitEnemy();
        }
        if (checksum == BitFilterDef.PLAYER_CO_ENEMY_HEAD) {
            if (contact.getFixtureA().getFilterData().categoryBits == BitFilterDef.ENEMY_HEAD_BIT) {
                DynamicEntity entity  = (DynamicEntity) contact.getFixtureA().getUserData();
                if(playScreen.getPlayer().getPlayerBody().getLinearVelocity().y < 0)  {
                    entity.prepareDestroy();
                }
            } else if (contact.getFixtureB().getFilterData().categoryBits == BitFilterDef.ENEMY_HEAD_BIT) {
                DynamicEntity entity  = (DynamicEntity) contact.getFixtureB().getUserData();
                if(playScreen.getPlayer().getPlayerBody().getLinearVelocity().y < 0)  {
                    entity.prepareDestroy();
                }
            }
        }
        if (checksum == BitFilterDef.PLAYER_REVERSE_VEL) {
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
