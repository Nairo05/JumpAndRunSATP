package de.dhbw.satp.gameobjects.collectible;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import de.dhbw.satp.gameobjects.DynamicEntity;
import de.dhbw.satp.main.FinalStatics;
import de.dhbw.satp.screens.PlayScreen;
import de.dhbw.satp.staticworld.BitFilterDef;

public class DynamicHeart extends DynamicEntity {

    private Texture texture;
    private boolean freeze = false;
    private final TextureRegion textureRegion[][];
    private float originX, originY;
    private float rotation = 120f;

    public DynamicHeart(PlayScreen playScreen, float posXInWorldUnits, float posYInWorldUnits) {
        super(playScreen, posXInWorldUnits, posYInWorldUnits);

        texture = playScreen.getAssetManager().get("sprite/heart.png");
        textureRegion = TextureRegion.split(texture, 16,16);

        originX = texture.getWidth() / (FinalStatics.PPM * 1f) / 2;
        originY = texture.getHeight() / (FinalStatics.PPM * 1f) / 2;
        rotation -= MathUtils.random(80);
    }

    @Override
    protected void defineHitBox() {
        FixtureDef bodyFixtureDef = new FixtureDef();

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6f / FinalStatics.PPM);
        bodyFixtureDef.shape = circleShape;
        bodyFixtureDef.filter.categoryBits = BitFilterDef.COLLECITBLE_BIT;
        bodyFixtureDef.filter.maskBits = BitFilterDef.GROUND_BIT | BitFilterDef.PLAYER_BIT;

        fixture = body.createFixture(bodyFixtureDef);
        fixture.setUserData(this);

        circleShape.dispose();
    }

    @Override
    public void onHeadHit() {

    }

    @Override
    public void onBodyHit() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

        spriteBatch.draw(
                textureRegion[0][0],
                body.getPosition().x - texture.getWidth() / (FinalStatics.PPM * 2f),
                body.getPosition().y - texture.getHeight() / (FinalStatics.PPM * 1.4f),
                originX,
                originY,
                texture.getWidth() / (FinalStatics.PPM * 1f),
                texture.getHeight() / (FinalStatics.PPM * 1f),
                1f, 1f,
                rotation,
                true);
    }

    public void freeze() {
        body.setActive(false);
        freeze = true;
    }

    public void unFreeze() {
        body.setActive(true);
        freeze = false;
    }

    public boolean isFreeze() {
        return freeze;
    }

    public float getPositionInWorld() {
        return body.getPosition().x;
    }
}
