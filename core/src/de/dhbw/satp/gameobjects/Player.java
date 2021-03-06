package de.dhbw.satp.gameobjects;

import static de.dhbw.satp.main.FinalStatics.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import de.dhbw.satp.main.Assets;
import de.dhbw.satp.screens.PlayScreen;
import de.dhbw.satp.staticworld.BitFilterDef;

public class Player implements GameObject {

    private final Texture texture;
    private final TextureRegion[][] textureRegions;
    private final Body playerBody;
    private final PlayScreen playScreen;

    private int frameCount = 0;
    private int frame = 0;

    private float jumpTime = 17f / PPM;
    private int coins = 0;
    private short lives = 3;

    private boolean hitEnemy = false;
    private boolean playerIsOnGround = false;
    private int freezeTime = 0;
    private short invincibilityFrames = 120;


    public Player(PlayScreen playScreen, float xInWorldUnit, float yInWorldUnit) {
        this.playScreen = playScreen;
        World world = playScreen.getWorld();

        texture = playScreen.getAssetManager().get(Assets.playerTexture);

        textureRegions = TextureRegion.split(texture, 13, 20);

        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.set(xInWorldUnit, yInWorldUnit);
        playerBody = world.createBody(playerDef);

        FixtureDef fixtureDef = new FixtureDef();

        CircleShape bodyCircleShape = new CircleShape();
        bodyCircleShape.setRadius(7f / PPM);
        fixtureDef.shape = bodyCircleShape;
        fixtureDef.filter.categoryBits = BitFilterDef.PLAYER_BIT;
        fixtureDef.filter.maskBits = BitFilterDef.GROUND_BIT | BitFilterDef.ENEMY_BODY_BIT
                | BitFilterDef.ENEMY_HEAD_BIT | BitFilterDef.ONE_WAY_GROUND
                | BitFilterDef.COLLECTIBLE_BIT;
        playerBody.createFixture(fixtureDef);

        EdgeShape foot = new EdgeShape();
        foot.set(new Vector2(-2f / PPM, -8f / PPM), new Vector2(2f / PPM, -8f / PPM));
        fixtureDef.shape = foot;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = BitFilterDef.PLAYER_BIT;
        playerBody.createFixture(fixtureDef);

        EdgeShape foot2 = new EdgeShape();
        foot2.set(new Vector2(-2f / PPM, -10f / PPM), new Vector2(2f / PPM, -10f / PPM));
        fixtureDef.shape = foot2;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = BitFilterDef.PLAYER_REVERSE_VEL_BIT;
        playerBody.createFixture(fixtureDef);

        bodyCircleShape.dispose();

        playerBody.setLinearDamping(2f);

    }

    @Override
    public void update(float dt) {
        playerIsOnGround = playScreen.myContactListener.isPlayerOnGround();

        if (playerBody.getPosition().y < 0) {
            loseLife();
            playerBody.setTransform(1, 1, 0);
        }

        if (lives <= 0) {
            playScreen.loseGame();
        }

        frameCount++;
        if ((frameCount % 6) == 0) {
            frame++;
            if (frame > 3) {
                frame = 0;
            }
        }

        if(freezeTime > 0) {
            freezeTime--;
        }

        if (invincibilityFrames < 120 && invincibilityFrames > 0) {
            invincibilityFrames--;
        } else if (invincibilityFrames <= 0) {
            invincibilityFrames = 120;
        }

        if(hitEnemy) {
            freezeTime = 20;
            if (playerBody.getLinearVelocity().x >= 0f) {
                playerBody.applyLinearImpulse(-2f, 1.5f, playerBody.getWorldCenter().x, playerBody.getWorldCenter().y, true);
            } else {
                playerBody.applyLinearImpulse(2f, 1.5f, playerBody.getWorldCenter().x, playerBody.getWorldCenter().y, true);
            }
            hitEnemy = false;
        }


        if (playScreen.myContactListener.isPlayerOnGround()) {
            jumpTime = 17f / PPM;
        }

        //--------------------------------------------- Mouse and Keyboard ----------------------------------------------------------------
        if (freezeTime <= 0) {
            if ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) && playerBody.getPosition().x <= 30f) {
                if (playerBody.getLinearVelocity().x < 1.5f) {
                    playerBody.applyLinearImpulse(new Vector2(10f * dt, 0), playerBody.getWorldCenter(), true);
                }
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) && playerBody.getPosition().x <= 30f) {
                if (playerBody.getLinearVelocity().x > -1.5f) {
                    playerBody.applyLinearImpulse(new Vector2(-10f * dt, 0), playerBody.getWorldCenter(), true);
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                if (jumpTime == 17f / PPM) {
                    playerBody.applyLinearImpulse(new Vector2(0f, 38f * dt), playerBody.getWorldCenter(), true);
                }
                if (jumpTime > 0) {
                    jumpTime -= dt;
                    playerBody.applyLinearImpulse(new Vector2(0f, 18f * dt), playerBody.getWorldCenter(), true);
                } else {

                }
            }
        }

        // ------------------------------------------------- Android ---------------------------------------------------------------------
        if(freezeTime <= 0) {
            for (int i = 0; i < 3; i++) {

                if (Gdx.input.isTouched(i)) {

                    if (Gdx.input.getX(i) > 0 && Gdx.input.getX(i) < 300) {
                        if (playerBody.getLinearVelocity().x < 1.5f) {
                            playerBody.applyLinearImpulse(new Vector2(10f * dt, 0), playerBody.getWorldCenter(), true);
                        }
                    } else if (Gdx.input.getX(i) > 300) {
                        if (jumpTime == 17f / PPM) {
                            playerBody.applyLinearImpulse(new Vector2(0f, 38f * dt), playerBody.getWorldCenter(), true);
                        }
                        if (jumpTime > 0) {
                            jumpTime -= dt;
                            playerBody.applyLinearImpulse(new Vector2(0f, 18f * dt), playerBody.getWorldCenter(), true);
                        } else {

                        }
                    }
                }
            }
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (playerBody.getLinearVelocity().x < -0.1f) {
            if (!textureRegions[0][frame].isFlipX()) {
                textureRegions[0][frame].flip(true, false);
            }
        }
        if (playerBody.getLinearVelocity().x > 0.1f) {
            if (textureRegions[0][frame].isFlipX()) {
                textureRegions[0][frame].flip(true, false);
            }
        }

        if (Math.abs(playerBody.getLinearVelocity().x) >= 0.001f && Math.abs(playerBody.getLinearVelocity().y) < 0.1f) {
            spriteBatch.draw(textureRegions[0][frame], playerBody.getPosition().x - 0.07f ,playerBody.getPosition().y - 0.07f, 0.13f,0.20f);
        } else {
            spriteBatch.draw(textureRegions[0][0], playerBody.getPosition().x - 0.07f ,playerBody.getPosition().y - 0.07f, 0.13f,0.20f);
        }
    }


    public float getX() {
        return playerBody.getPosition().x;
    }

    public float getY() {
        return playerBody.getPosition().y;
    }

    public void jumped(){
        if (playerBody.getLinearVelocity().y < -1f) {
            playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, 0);
        }
    }

    public Body getPlayerBody() {
        return playerBody;
    }

    public void hitEnemy() {
        hitEnemy = true;
        loseLife();
    }

    public void loseLife() {
        if (invincibilityFrames == 120) {
            lives--;
            invincibilityFrames--;
        }
    }

    public void collectCoin() {
        coins++;
    }

    public int getCoins() {
        return coins;
    }

    public short getLives() {
        return lives;
    }

    public boolean isInvincible() {
        return invincibilityFrames < 120;
    }

    public boolean isPlayerIsOnGround() {
        return playerIsOnGround;
    }
}
