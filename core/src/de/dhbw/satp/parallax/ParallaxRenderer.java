package de.dhbw.satp.parallax;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import de.dhbw.satp.main.Statics;

/**
 * Custom Renderer
 *
 * can render n*m Background pieces and adds a Parallax-Effect
 *
 * */
public class ParallaxRenderer implements Disposable {

    //Deactivate this Option to increase the Performance
    private final boolean enableParallax;

    //Offset to avoid "Black-Render-Lines"
    private final float RENDER_OFFSET = 0.005f;

    //Fixed values, load from Configuration-File

    private final float PARALLAX_FACTOR = 26f;
    private final float BG_ROW_COUNT = 3;

    private final float Y_ADDITION_LAYER0 = 0.30f;
    private final float Y_ADDITION_LAYER1 = Y_ADDITION_LAYER0 / 2;
    private final float Y_ADDITION_LAYER2 = -0.02f;

    Array<ParallaxBackgroundLayer> layerArray = new Array<>();

    private float camOld = 0;

    public ParallaxRenderer(boolean enableParallax) {
        this.enableParallax = enableParallax;

        //Add layers
        layerArray.add(new ParallaxBackgroundLayer(new Texture("tmx/backgrounds/background_0.png")));
        layerArray.add(new ParallaxBackgroundLayer(new Texture("tmx/backgrounds/background_1.png")));
        layerArray.add(new ParallaxBackgroundLayer(new Texture("tmx/backgrounds/background_2.png")));

        //Start-Positions
        for (int i = 0; i < layerArray.size; i++) {

            if (i == 0) {
                layerArray.get(i).setyOffset(Y_ADDITION_LAYER0);
            } else if (i == 1) {
                layerArray.get(i).setyOffset(Y_ADDITION_LAYER1);
            } else if (i == 2) {
                layerArray.get(i).setyOffset(Y_ADDITION_LAYER2);
            }

            for (int j = 0; j < 3; j++) {
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
                    layerArray.get(i).getPosition(j).add((layerArray.get(j).getBackgroundWith() - RENDER_OFFSET) * BG_ROW_COUNT, 0);
                } else if (layerArray.get(i).getPosition(j).x > xViewportRight) {
                    layerArray.get(i).getPosition(j).add(-1f * (layerArray.get(j).getBackgroundWith() - RENDER_OFFSET) * BG_ROW_COUNT, 0);
                }
            }

            //Parallax-Effect per Frame
            if (enableParallax) {
                //Movement frameTime * 1/frameTime (Statics.FOREGROUND_FPS) to smooth out
                for (int j = 0; j < layerArray.get(i).getBackgroundCount(); j++) {
                    layerArray.get(i).getPosition(j).add(-1f * speed / PARALLAX_FACTOR * dt * Statics.FOREGROUND_FPS, 0);
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
