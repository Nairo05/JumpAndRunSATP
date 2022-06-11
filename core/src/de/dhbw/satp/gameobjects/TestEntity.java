package de.dhbw.satp.gameobjects;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import de.dhbw.satp.main.Statics;
import de.dhbw.satp.world.BitFilterDef;

public class TestEntity extends DynamicEntity {

    private int lifeSpan = 600;

    public TestEntity(World world, float posXInWorldUnits, float posYInWorldUnits) {
        super(world, posXInWorldUnits, posYInWorldUnits);
    }

    @Override
    protected void defineHitBox() {
        FixtureDef fixtureDef = new FixtureDef();

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(7f / Statics.PPM);
        fixtureDef.filter.categoryBits = BitFilterDef.ENEMY_BIT;
        fixtureDef.shape = circleShape;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        circleShape.dispose();
    }

    @Override
    public void onHit() {

    }

    @Override
    public void destroyBody() {
        world.destroyBody(body);
        isDestroyed = true;
    }

    @Override
    public void update(float dt) {
        if (!isDestroyed) {
            lifeSpan--;
            if (toDestroy) {
                destroyBody();
                toDestroy = false;
            }
            if (lifeSpan < 0) {
                prepareDestroy();
            }
        }
    }

    @Override
    public void render() {
        if (!isDestroyed()) {
            //Code
        }
    }

    @Override
    public void dispose() {

    }
}
