package de.dhbw.satp.world;

import static de.dhbw.satp.main.Statics.PPM;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import de.dhbw.satp.main.Statics;

public abstract class StaticObject {

    protected World world;
    protected Fixture fixture;
    protected Body body;

    public StaticObject(World world, float x, float y, float width) {
        this.world = world;

        BodyDef block = new BodyDef();
        block.type = BodyDef.BodyType.StaticBody;
        block.position.set(x / PPM, y / PPM);
        body = world.createBody(block);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width / PPM / 2, Statics.BLOCK_UNIT / PPM);
        fixtureDef.shape = polygonShape;
        fixture = body.createFixture(fixtureDef);

        polygonShape.dispose();
    }
}
