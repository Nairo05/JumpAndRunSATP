package de.dhbw.satp.gameobjects;

import static de.dhbw.satp.main.FinalStatics.PPM;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import de.dhbw.satp.screens.PlayScreen;

public abstract class DynamicEntity implements GameObject, Disposable {

    protected Body body;
    protected Fixture fixture;
    protected World world;
    protected boolean toDestroy = false;
    protected boolean isDestroyed = false;

    public DynamicEntity(PlayScreen playScreen, float posXInWorldUnits, float posYInWorldUnits) {
        this.world = playScreen.getWorld();
        defineEntityAttributes(posXInWorldUnits, posYInWorldUnits);
    }

    protected void defineEntityAttributes(float posX, float posY) {
        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.set(posX / PPM, posY / PPM);
        body = world.createBody(playerDef);

        defineHitBox();
    }

    protected abstract void defineHitBox();
    public abstract void onHeadHit();
    public abstract void onBodyHit();

    public void destroyBody() {
        world.destroyBody(body);
        isDestroyed = true;
    }

    public void prepareDestroy(){
        if (!isDestroyed && !toDestroy) {
            toDestroy = true;
        }
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public float getX() {
        if(!isDestroyed && !toDestroy) {
            return body.getPosition().x;
        } else {
            return -1f;
        }
    }
}
