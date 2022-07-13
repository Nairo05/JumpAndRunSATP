package de.dhbw.satp.gameobjects.collectible;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import de.dhbw.satp.gameobjects.DynamicEntity;
import de.dhbw.satp.main.Assets;
import de.dhbw.satp.main.FinalStatics;
import de.dhbw.satp.screens.PlayScreen;
import de.dhbw.satp.staticworld.BitFilterDef;

public class DynamicCoin extends DynamicEntity {

    private final Texture texture;
    private final TextureRegion[][] textureRegion;

    private final Vector2 speed = new Vector2(0f,0f);
    private final float originX;
    private final float originY;

    private Vector2 textureDistort = new Vector2(0f,0f);
    private int animationCount = 15;
    private float rotation = 120f;
    private boolean freeze = false;
    private boolean inAnimation = false;

    public DynamicCoin(PlayScreen playScreen, float posXInWorldUnits, float posYInWorldUnits) {
        super(playScreen, posXInWorldUnits, posYInWorldUnits);

        texture = playScreen.getAssetManager().get(Assets.coinSprite);
        textureRegion = TextureRegion.split(texture, 16,16);

        originX = texture.getWidth() / (FinalStatics.PPM * 1f) / 2 / 4;
        originY = texture.getHeight() / (FinalStatics.PPM * 1f) / 2;
        rotation -= MathUtils.random(80);
    }

    @Override
    protected void defineHitBox() {
        FixtureDef bodyFixtureDef = new FixtureDef();

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6f / FinalStatics.PPM);
        bodyFixtureDef.shape = circleShape;
        bodyFixtureDef.filter.categoryBits = BitFilterDef.COLLECTIBLE_BIT;
        bodyFixtureDef.filter.maskBits = BitFilterDef.GROUND_BIT;

        fixture = body.createFixture(bodyFixtureDef);
        fixture.setUserData(this);

        FixtureDef fDefHit = new FixtureDef();
        PolygonShape shapeHit = new PolygonShape();
        shapeHit.setAsBox(12f / FinalStatics.PPM,12f / FinalStatics.PPM);
        fDefHit.shape = shapeHit;
        fDefHit.filter.categoryBits = BitFilterDef.COLLECTIBLE_BIT;
        fDefHit.filter.maskBits = BitFilterDef.PLAYER_BIT;
        fDefHit.isSensor = true;

        body.createFixture(fDefHit).setUserData(this);

        circleShape.dispose();
    }

    @Override
    public void onHeadHit() {

    }

    @Override
    public void onBodyHit() {
        prepareDestroy();
        inAnimation = true;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void update(float dt) {
        if (toDestroy && !isDestroyed) {

            textureDistort = body.getPosition();

            destroyBody();
            toDestroy = false;

        }

        if (inAnimation) {

            animationCount--;

            speed.add(-0.015f, 0.01f);
            textureDistort.add(speed);

            if (animationCount < 0) {
                inAnimation = false;
            }
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (!toDestroy && !isDestroyed) {

            spriteBatch.draw(
                    textureRegion[0][0],
                    body.getPosition().x - texture.getWidth() / 4f / (FinalStatics.PPM * 2f),
                    body.getPosition().y - texture.getHeight() / (FinalStatics.PPM * 1.2f),
                    originX,
                    originY,
                    texture.getWidth() / 4f / (FinalStatics.PPM * 1f),
                    texture.getHeight() / (FinalStatics.PPM * 1f),
                    1f, 0.7f,
                    rotation,
                    true);

        }
        if (inAnimation) {

            spriteBatch.draw(
                    textureRegion[0][0],
                    textureDistort.x,
                    textureDistort.y,
                    originX,
                    originY,
                    texture.getWidth() / 4f / (FinalStatics.PPM * 1f),
                    texture.getHeight() / (FinalStatics.PPM * 1f),
                    1f, 0.7f,
                    rotation,
                    true);
        }
    }

    public void freeze() {
        if (!toDestroy && !isDestroyed) {
            body.setActive(false);
            freeze = true;
        }
    }

    public void unFreeze() {
        if (!toDestroy && !isDestroyed) {
            body.setActive(true);
            freeze = false;
        }
    }

    public boolean isFreeze() {
        if (!toDestroy && !isDestroyed) {
            return freeze;
        }
        return true;
    }

    public float getPositionInWorld() {
        if (!toDestroy && !isDestroyed) {
            return body.getPosition().x;
        } else {
            return -1;
        }
    }

    public boolean isInAnimation() {
        return inAnimation;
    }
}
