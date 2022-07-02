package de.dhbw.satp.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.awt.peer.DialogPeer;

public class ParticleManager implements Disposable {

    private AssetManager assetManager;
    private final int MAX_PARTICLE_IN_WORLD = 4;
    private final Array<ParticleEffect> particles;
    private int currentActive = 0;

    public ParticleManager(AssetManager assetManager) {
        this.assetManager  = assetManager;

        particles = new Array<>(MAX_PARTICLE_IN_WORLD);
    }

    public void addParticleEffect(String type, float xInWorldUnits, float yInWorldUnits) {
        if (particles.size < MAX_PARTICLE_IN_WORLD) {
            if (type.equalsIgnoreCase("WhiteFly.p")) {
                ParticleEffect particleEffect = new ParticleEffect();
                particleEffect.load(Gdx.files.internal("particle/whitefly.p"), Gdx.files.internal("particle/"));
                particleEffect.getEmitters().first().setPosition(xInWorldUnits, yInWorldUnits);
                particleEffect.scaleEffect(0.005f);
                particleEffect.start();

                particles.add(particleEffect);

            } else if (type.equalsIgnoreCase("Rain.p")) {
                ParticleEffect particleEffect = new ParticleEffect();
                particleEffect.load(Gdx.files.internal("particle/rain.p"), Gdx.files.internal("particle/"));
                particleEffect.getEmitters().first().setPosition(xInWorldUnits - 1.7f, yInWorldUnits + 0.4f);
                particleEffect.scaleEffect(0.004f);
                particleEffect.start();

                particles.add(particleEffect);
            }
        } else {
            System.out.println("Particle Limit exceeded");
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

            //Only render, if camera is showing them
            float leftViewPortX = camera.position.x - camera.viewportWidth;
            float rightViewPortX = camera.position.x + camera.viewportWidth;

            if (particleEffect.getEmitters().get(0).getX() > leftViewPortX && particleEffect.getEmitters().get(0).getX() < rightViewPortX) {

                currentActive++;
                particleEffect.draw(spriteBatch);

                //endless Loop
                if (particleEffect.isComplete()) {
                    particleEffect.reset();
                }
            }
        }
    }

    public int getMAX_PARTICLE_IN_WORLD() {
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
