package de.dhbw.satp.scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class LevelButton extends Actor implements Disposable {

    //Filepaths of Sprites
    private static final String BUTTON_SPRITE_PATH = "menu/ui/BTN_GREEN_SQ.png";
    private static final String ID1_SPRITE_PATH = "menu/menunumbers.png";

    private final Texture buttonTexture;
    private final Texture idTexture;
    private final TextureRegion[][] textureRegions;
    private int id;

    public LevelButton(int id) {
        buttonTexture = new Texture(BUTTON_SPRITE_PATH);
        idTexture = new Texture(ID1_SPRITE_PATH);
        textureRegions = TextureRegion.split(idTexture, 48, 64);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void dispose() {

    }
}
