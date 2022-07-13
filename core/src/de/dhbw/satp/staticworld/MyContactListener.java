package de.dhbw.satp.staticworld;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import de.dhbw.satp.gameobjects.DynamicEntity;
import de.dhbw.satp.screens.PlayScreen;

public class MyContactListener implements ContactListener {

    private final PlayScreen playScreen;
    private short playerOnGround = 0;

    public MyContactListener(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        int checksum = contact.getFixtureA().getFilterData().categoryBits + contact.getFixtureB().getFilterData().categoryBits;

        if (checksum == BitFilterDef.PLAYER_ON_GROUND) {
            System.out.println(playerOnGround);
            playerOnGround ++;
        }
        if (checksum == BitFilterDef.PLAYER_CO_ENEMY) {
            playScreen.getPlayer().hitEnemy();
        }
        if (checksum == BitFilterDef.PLAYER_CO_ENEMY_HEAD) {
            if (contact.getFixtureA().getFilterData().categoryBits == BitFilterDef.ENEMY_HEAD_BIT) {
                DynamicEntity entity  = (DynamicEntity) contact.getFixtureA().getUserData();
                if(playScreen.getPlayer().getPlayerBody().getLinearVelocity().y < 0)  {
                    entity.onHeadHit();
                }
            } else if (contact.getFixtureB().getFilterData().categoryBits == BitFilterDef.ENEMY_HEAD_BIT) {
                DynamicEntity entity  = (DynamicEntity) contact.getFixtureB().getUserData();
                if(playScreen.getPlayer().getPlayerBody().getLinearVelocity().y < 0)  {
                    entity.onHeadHit();
                }
            }
        }
        if (checksum == BitFilterDef.PLAYER_REVERSE_VEL) {
            playScreen.getPlayer().jumped();
        }

        if (checksum == BitFilterDef.PLAYER_COLLECT_COIN) {
            if (contact.getFixtureA().getFilterData().categoryBits == BitFilterDef.COLLECITBLE_BIT) {
                DynamicEntity entity  = (DynamicEntity) contact.getFixtureA().getUserData();
                if (MathUtils.random(4) == 0) {
                    playScreen.setEarthQuakeCount(MathUtils.random(60));
                }
                entity.onBodyHit();
                playScreen.getPlayer().collectCoin();
            }
            if (contact.getFixtureB().getFilterData().categoryBits == BitFilterDef.COLLECITBLE_BIT) {
                DynamicEntity entity  = (DynamicEntity) contact.getFixtureB().getUserData();
                entity.onBodyHit();
                if (MathUtils.random(10) == 0) {
                    playScreen.setEarthQuakeCount(MathUtils.random(200));
                }
                playScreen.getPlayer().collectCoin();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        int checksum = contact.getFixtureA().getFilterData().categoryBits + contact.getFixtureB().getFilterData().categoryBits;

        if (checksum == BitFilterDef.PLAYER_ON_GROUND ) {
            playerOnGround --;
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerOnGround() {
        return playerOnGround > 0;
    }


}
