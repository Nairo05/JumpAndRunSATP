package de.dhbw.satp.world;

import com.badlogic.gdx.physics.box2d.World;

import de.dhbw.satp.screens.PlayScreen;

public class Ground extends StaticObject{

    public Ground(World world, float x, float y, float width) {
        super(world, x, y, width);
    }
}
