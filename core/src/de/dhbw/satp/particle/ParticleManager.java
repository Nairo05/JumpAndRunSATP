package de.dhbw.satp.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import de.dhbw.satp.main.NotFinalStatics;

public class ParticleManager implements Disposable {

    private static final String PATH_PREFIX = "particle/";
    private static final String WHITE_FLY_EFFECT = "whitefly.p";
    private static final String RAIN_EFFECT = "rain.p";

    private final Array<ParticleEffect> particles;
    private final int MAX_PARTICLE_IN_WORLD = 4;

    private int currentActive = 0;

    public ParticleManager() {
        particles = new Array<>(MAX_PARTICLE_IN_WORLD);
    }

    public void addParticleEffect(String type, float xInWorldUnits, float yInWorldUnits) {
        if (particles.size < MAX_PARTICLE_IN_WORLD) {
            if (type.equalsIgnoreCase(WHITE_FLY_EFFECT)) {
                ParticleEffect particleEffect = new ParticleEffect();
                particleEffect.load(Gdx.files.internal(PATH_PREFIX + WHITE_FLY_EFFECT), Gdx.files.internal(PATH_PREFIX));
                particleEffect.getEmitters().first().setPosition(xInWorldUnits, yInWorldUnits);
                particleEffect.scaleEffect(0.005f);
                particleEffect.start();

                particles.add(particleEffect);

            } else if (type.equalsIgnoreCase(RAIN_EFFECT)) {
                ParticleEffect particleEffect = new ParticleEffect();
                particleEffect.load(Gdx.files.internal(PATH_PREFIX + RAIN_EFFECT), Gdx.files.internal(PATH_PREFIX));
                particleEffect.getEmitters().first().setPosition(xInWorldUnits - 1.7f, yInWorldUnits + 0.4f);
                particleEffect.scaleEffect(0.004f);
                particleEffect.start();

                particles.add(particleEffect);
            }
        } else {
            if (NotFinalStatics.debug) {
                System.out.println("Particle Limit exceeded");
            }
        }
    }

    public void update(float dt) {
        for (ParticleEffect particleEffect : particles) {
            particleEffect.update(dt);
        }
    }

    public void render(SpriteBatch spriteBatch, Camera camera) {
        currentActive = 0;

        for (ParticleEffect particleEffect : particles) {

            float leftViewPortX = camera.position.x - camera.viewportWidth;
            float rightViewPortX = camera.position.x + camera.viewportWidth;

            if (particleEffect.getEmitters().get(0).getX() > leftViewPortX && particleEffect.getEmitters().get(0).getX() < rightViewPortX) {

                currentActive++;
                particleEffect.draw(spriteBatch);

                if (particleEffect.isComplete()) {
                    particleEffect.reset();
                }
            }
        }
    }

    public int getMaxParticleInWorld() {
        return MAX_PARTICLE_IN_WORLD;
    }

    public int getParticleSize() {
        return particles.size;
    }

    public int getActive() {
        return currentActive;
    }

    @Override
    public void dispose() {
        for (ParticleEffect particleEffect : particles) {
            particleEffect.dispose();
        }
    }
}
