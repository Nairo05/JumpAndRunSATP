package de.dhbw.satp.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameObject {

    void update(float dt);
    void render(SpriteBatch spriteBatch);
}
