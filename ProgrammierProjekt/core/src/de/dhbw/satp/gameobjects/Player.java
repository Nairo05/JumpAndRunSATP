package de.dhbw.satp.gameobjects;

import static de.dhbw.satp.main.Statics.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class Player implements GameObject, Disposable {

    private World world;
    private Fixture playerFixture;
    private Body playerBody;

    public Player(World world) {
        this.world = world;

        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.set(20f / PPM, 50f / PPM);
        playerBody = world.createBody(playerDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape bodyCircleShape = new CircleShape();
        bodyCircleShape.setRadius(16f / PPM);
        fixtureDef.shape = bodyCircleShape;
        playerFixture = playerBody.createFixture(fixtureDef);

        bodyCircleShape.dispose();

        playerBody.setLinearDamping(2f);

    }

    @Override
    public void update(float dt) {
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
            if (playerBody.getLinearVelocity().y >= 0f) {
                if (playerBody.getLinearVelocity().y < 4f) {
                    playerBody.applyLinearImpulse(new Vector2(0f, 60f * dt), playerBody.getWorldCenter(), true);
                }
            }
        }
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }

    public float getX() {
        return playerBody.getPosition().x;
    }
}
