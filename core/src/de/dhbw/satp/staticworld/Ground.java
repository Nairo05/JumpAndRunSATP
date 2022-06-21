package de.dhbw.satp.staticworld;

import com.badlogic.gdx.physics.box2d.World;

public class Ground extends StaticObject {

    public Ground(World world, float x, float y, float width, float height) {
        super(world, x, y, width, height);
    }

    public Ground(World world, float[] vertices) {
        super(world, vertices);
    }

    @Override
    protected void defineStaticObject() {
        setFilter(BitFilterDef.GROUND_BIT);
    }
}
