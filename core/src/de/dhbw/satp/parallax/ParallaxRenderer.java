package de.dhbw.satp.parallax;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import de.dhbw.satp.main.Statics;

/**
 * Custom Renderer
 *
 * can render n*m Background pieces and adds a Parallax-Effect to each
 *
 * */
public class ParallaxRenderer implements Disposable {

    //Deactivate this Option to increase the Performance
    private final boolean enableParallax = true;

    //Offset to avoid "Black-Render-Lines"
    private final float RENDER_OFFSET = 0.005f;

    private final Array<ParallaxBackgroundLayer> layerArray = new Array<>();

    private float camOld = 0;

    public ParallaxRenderer(ParallaxConfiguration parallaxConfiguration) {

        layerArray.addAll(parallaxConfiguration.getPBgLayers());

        //Start-Position of ech Background
        for (int i = 0; i < layerArray.size; i++) {
            for (int j = 0; j < layerArray.get(i).getRowCount(); j++) {
                layerArray.get(i).addBackGround(j * layerArray.get(i).getBackgroundWith() - j * RENDER_OFFSET);
            }
        }
    }

    public void update(float dt, Camera camera) {

        //Get Viewport metrics
        float camX = camera.position.x;
        float speed = camX - camOld;

        for (int i = 0; i < layerArray.size; i++) {

            //Get Viewport metrics
            float xViewportLeft = camX - camera.viewportWidth / 2f - layerArray.get(i).getBackgroundWith() * 1.2f;
            float xViewportRight = camX + camera.viewportWidth / 2f + layerArray.get(i).getBackgroundWith() / 2f;

            //If camera cant see Background -> replace
            for (int j = 0; j < layerArray.get(i).getBackgroundCount(); j++) {
                if (layerArray.get(i).getPosition(j).x < xViewportLeft) {
                    layerArray.get(i).getPosition(j).add((layerArray.get(j).getBackgroundWith() - RENDER_OFFSET) * layerArray.get(j).getRowCount(), 0);
                } else if (layerArray.get(i).getPosition(j).x > xViewportRight) {
                    layerArray.get(i).getPosition(j).add(-1f * (layerArray.get(j).getBackgroundWith() - RENDER_OFFSET) * layerArray.get(j).getRowCount(), 0);
                }
            }

            //Parallax-Effect per Frame
            if (enableParallax) {
                //Movement frameTime * 1/frameTime (Statics.FOREGROUND_FPS) to smooth out
                for (int j = 0; j < layerArray.get(i).getBackgroundCount(); j++) {
                    layerArray.get(i).getPosition(j).add(-1f * speed / layerArray.get(i).getParallaxFactor() * dt * Statics.FOREGROUND_FPS, 0);
                }
            }
        }

        //Get Viewport metrics
        camOld = camX;
    }

    public void render(SpriteBatch spriteBatch) {
        //draw Backgrounds
        for (int i = 0; i < layerArray.size; i++) {
            for (int j = 0; j < layerArray.get(i).getBackgroundCount(); j++) {
                spriteBatch.draw(layerArray.get(i).getBg(), layerArray.get(i).getPosition(j).x, layerArray.get(i).getPosition(j).y + layerArray.get(i).getyOffset(), layerArray.get(i).getBackgroundWith(), layerArray.get(i).getBackgroundHeigth());
            }
        }

    }

    @Override
    public void dispose() {
        //release Video-RAM
        for (int i = 0; i < layerArray.size; i++) {
            layerArray.get(i).dispose();
        }
    }
}