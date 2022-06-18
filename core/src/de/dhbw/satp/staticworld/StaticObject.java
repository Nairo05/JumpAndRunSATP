package de.dhbw.satp.staticworld;

import static de.dhbw.satp.main.Statics.PPM;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class StaticObject {

    protected World world;
    protected Fixture fixture;
    protected Body body;

    public StaticObject(World world, float x, float y, float width, float height) {
        this.world = world;

        BodyDef block = new BodyDef();
        block.type = BodyDef.BodyType.StaticBody;
        block.position.set((x + width / 2) / PPM, (y + height / 2) / PPM);
        body = world.createBody(block);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width / PPM / 2, height / PPM / 2);
        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = BitFilterDef.GROUND_BIT;
        fixtureDef.filter.maskBits = BitFilterDef.PLAYER_BIT | BitFilterDef.ENEMY_BODY_BIT | BitFilterDef.PLAYER_REVERSE_VEL_BIT;
        fixture = body.createFixture(fixtureDef);

        polygonShape.dispose();
    }
}
