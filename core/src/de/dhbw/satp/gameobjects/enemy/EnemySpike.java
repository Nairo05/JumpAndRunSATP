package de.dhbw.satp.gameobjects.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.dhbw.satp.gameobjects.enemy.Enemy;
import de.dhbw.satp.screens.PlayScreen;

public final class EnemySpike extends Enemy {

    public EnemySpike(PlayScreen playScreen, float posXInWorldUnits, float posYInWorldUnits, float width) {
        super(playScreen, posXInWorldUnits, posYInWorldUnits, width);
        texture = playScreen.getAssetManager().get("sprite/enemy/Lethal Scorpion/LethalScorpionIdleSide.png", Texture.class);
        textureRegions = TextureRegion.split(texture, 16, 16);
    }

    @Override
    public void onHeadHit() {
        playScreen.getPlayer().hitEnemy();
    }
}
