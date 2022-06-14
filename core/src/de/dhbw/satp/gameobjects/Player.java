package de.dhbw.satp.gameobjects;

import static de.dhbw.satp.main.Statics.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import de.dhbw.satp.screens.PlayScreen;
import de.dhbw.satp.world.BitFilterDef;

public class Player implements GameObject, Disposable {

    private Texture texture;

    private final Body playerBody;
    private final PlayScreen playScreen;

    private float jumpTime = 17f / PPM;

    public Player(PlayScreen playScreen, float xInWorldUnit, float yInWorldUnit) {
        this.playScreen = playScreen;
        World world = playScreen.getWorld();

        texture = new Texture("sprite/bullet.png");

        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.set(xInWorldUnit, yInWorldUnit);
        playerBody = world.createBody(playerDef);

        FixtureDef fixtureDef = new FixtureDef();

        CircleShape bodyCircleShape = new CircleShape();
        bodyCircleShape.setRadius(7f / PPM);
        fixtureDef.shape = bodyCircleShape;
        fixtureDef.filter.categoryBits = BitFilterDef.OBJECT_BIT;
        fixtureDef.filter.maskBits = BitFilterDef.GROUND_BIT | BitFilterDef.OBJECT_BIT | BitFilterDef.ENEMY_BIT;
        playerBody.createFixture(fixtureDef);

        EdgeShape foot = new EdgeShape();
        foot.set(new Vector2(-2f / PPM, -8f / PPM), new Vector2(2f / PPM, -8f / PPM));
        fixtureDef.shape = foot;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = BitFilterDef.PLAYER_BIT;
        playerBody.createFixture(fixtureDef);

        bodyCircleShape.dispose();

        playerBody.setLinearDamping(2f);

    }

    @Override
    public void update(float dt) {
        if (playScreen.myContactListener.isPlayerOnGround()) {
            jumpTime = 17f / PPM;
        }
        //--------------------------------------------- Mouse and Keyboard ----------------------------------------------------------------
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (playerBody.getLinearVelocity().x < 1.5f) {
                playerBody.applyLinearImpulse(new Vector2(10f * dt, 0), playerBody.getWorldCenter(), true);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (playerBody.getLinearVelocity().x > -1.5f) {
                playerBody.applyLinearImpulse(new Vector2(-10f * dt, 0), playerBody.getWorldCenter(), true);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //TODO: inital Impulse + variable
            if (jumpTime == 17f / PPM) {
                System.out.println("initial Impuls");
                playerBody.applyLinearImpulse(new Vector2(0f, 38f * dt), playerBody.getWorldCenter(), true);
            }
            if (jumpTime > 0) {
                    jumpTime -= dt;
                    playerBody.applyLinearImpulse(new Vector2(0f, 18f * dt), playerBody.getWorldCenter(), true);
            } else {
                System.out.println("- velocity");
            }
        }

        // ------------------------------------------------- Android ---------------------------------------------------------------------
        for (int i = 0 ; i < 3; i++){

            if (Gdx.input.isTouched(i)){

                if (Gdx.input.getX(i) > 0 && Gdx.input.getX(i) < 300) {
                    if (playerBody.getLinearVelocity().x < 1.5f) {
                        playerBody.applyLinearImpulse(new Vector2(10f * dt, 0), playerBody.getWorldCenter(), true);
                    }
                } else if (Gdx.input.getX(i) > 300) {
                    //TODO: inital Impulse + variable
                    if (jumpTime == 17f / PPM) {
                        System.out.println("initial Impuls");
                        playerBody.applyLinearImpulse(new Vector2(0f, 38f * dt), playerBody.getWorldCenter(), true);
                    }
                    if (jumpTime > 0) {
                        jumpTime -= dt;
                        playerBody.applyLinearImpulse(new Vector2(0f, 18f * dt), playerBody.getWorldCenter(), true);
                    } else {
                        System.out.println("- velocity");
                    }
                }
            }
        }



    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, playerBody.getPosition().x,playerBody.getPosition().y, 0.16f,0.16f);
    }

    @Override
    public void dispose() {

    }

    public float getX() {
        return playerBody.getPosition().x;
    }
    public float getY() {
        return playerBody.getPosition().y;
    }

    public void jumped(){
        if (playerBody.getLinearVelocity().y < 0) {
            playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, 0);
            System.out.println("Velocity x+ fix");
        }
    }

    public Body getPlayerBody() {
        return playerBody;
    }
}
