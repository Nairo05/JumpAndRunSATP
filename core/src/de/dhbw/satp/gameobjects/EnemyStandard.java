package de.dhbw.satp.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.dhbw.satp.screens.PlayScreen;

public class EnemyStandard extends Enemy {

    public EnemyStandard(PlayScreen playScreen, float posXInWorldUnits, float posYInWorldUnits, float width) {
        super(playScreen, posXInWorldUnits, posYInWorldUnits, width);
        texture = playScreen.getAssetManager().get("sprite/enemy/Bloated Bedbug/BloatedBedbugIdleSide.png", Texture.class);
        textureRegions = TextureRegion.split(texture, 16, 16);
    }



}
