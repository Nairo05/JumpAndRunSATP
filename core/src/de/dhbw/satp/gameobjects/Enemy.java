package de.dhbw.satp.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import de.dhbw.satp.main.Statics;
import de.dhbw.satp.staticworld.BitFilterDef;

public class Enemy extends DynamicEntity {

    private static final String ENEMY_SPRITE_PATH = "sprite/enemy/Bloated Bedbug/BloatedBedbugIdleSide.png";

    private final float leftBound;
    private final float rightBound;
    private boolean movesLeft = false;

    private Texture texture;
    private TextureRegion[][] textureRegions;
    private int framecount = 0;
    private int frame = 0;


    public Enemy(World world, float posXInWorldUnits, float posYInWorldUnits, float width) {
        super(world, posXInWorldUnits, posYInWorldUnits);
        float roamWidth = width / 2;
        this.leftBound = (posXInWorldUnits - (roamWidth)) / Statics.PPM;
        this.rightBound = (posXInWorldUnits + (roamWidth)) / Statics.PPM;
    }

    @Override
    protected void defineHitBox() {

        texture = new Texture(ENEMY_SPRITE_PATH);
        textureRegions = TextureRegion.split(texture, 16, 16);

        FixtureDef headFixtureDef = new FixtureDef();
        FixtureDef bodyFixtureDef = new FixtureDef();

        PolygonShape polygonShape = new PolygonShape();
        Vector2[] vertices = {
                new Vector2(-5.5f, 8f).scl(1f/Statics.PPM),
                new Vector2(5.5f, 8f).scl(1f/Statics.PPM),
                new Vector2(-2.5f, 3f).scl(1f/Statics.PPM),
                new Vector2(2.5f, 3f).scl(1f/Statics.PPM)
        };
        polygonShape.set(vertices);
        headFixtureDef.shape = polygonShape;
        headFixtureDef.filter.categoryBits = BitFilterDef.ENEMY_HEAD_BIT;
        headFixtureDef.filter.maskBits = BitFilterDef.PLAYER_BIT;
        headFixtureDef.isSensor = true;
        body.createFixture(headFixtureDef).setUserData(this);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6f / Statics.PPM);
        bodyFixtureDef.filter.categoryBits = BitFilterDef.ENEMY_BODY_BIT;
        bodyFixtureDef.filter.maskBits = BitFilterDef.PLAYER_BIT | BitFilterDef.GROUND_BIT;
        bodyFixtureDef.shape = circleShape;

        fixture = body.createFixture(bodyFixtureDef);
        fixture.setUserData(this);

        circleShape.dispose();
    }

    @Override
    public void onHeadHit() {
        prepareDestroy();
    }

    @Override
    public void onBodyHit() {

    }

    @Override
    public void update(float dt) {

        if (toDestroy) {
            destroyBody();
            toDestroy = false;
        }

        framecount++;
        if ((framecount % 6) == 0) {
            frame++;
            if (frame > 3) {
                frame = 0;
            }
        }

        if(movesLeft) {
            body.setLinearVelocity(-0.2f, 0f);
        } else {
            body.setLinearVelocity(0.2f, 0f);
        }

        if(body.getWorldCenter().x <= leftBound) {
            movesLeft = false;
        } else if (body.getWorldCenter().x >= rightBound) {
            movesLeft = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

        if (!isDestroyed()) {
            if (body.getLinearVelocity().x < -0.1f) {
                if (!textureRegions[0][frame].isFlipX()) {
                    textureRegions[0][frame].flip(true, false);
                }
            }
            if (body.getLinearVelocity().x > 0.1f) {
                if (textureRegions[0][frame].isFlipX()) {
                    textureRegions[0][frame].flip(true, false);
                }
            }
        }

        if (Math.abs(body.getLinearVelocity().x) >= 0.001f && Math.abs(body.getLinearVelocity().y) < 0.1f) {
            spriteBatch.draw(textureRegions[0][frame], body.getPosition().x - 0.07f ,body.getPosition().y - 0.07f, 0.13f,0.15f);
        } else {
            spriteBatch.draw(textureRegions[0][0], body.getPosition().x - 0.07f ,body.getPosition().y - 0.07f, 0.13f,0.15f);
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}


