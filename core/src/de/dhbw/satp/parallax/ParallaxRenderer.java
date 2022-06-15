package de.dhbw.satp.parallax;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import de.dhbw.satp.main.Statics;

public class ParallaxRenderer implements Disposable {

    //Deactivate this Option to increase the Performance
    private final boolean enableParallax;

    //Offset to avoid "Black-Render-Lines"
    private final float RENDER_OFFSET = 0.005f;

    //Fixed values
    private final float PARALLAX_FACTOR = 26f;
    private final float BG_ROW_COUNT = 3;
    private final float VIEWPORT_STRETCH_FACTOR = 1;

    private final float Y_ADDITION_LAYER0 = 0.30f;
    private final float Y_ADDITION_LAYER1 = Y_ADDITION_LAYER0 / 2;
    private final float Y_ADDITION_LAYER2 = -0.02f;

    private final float BG_WIDTH;
    private final float BG_HEIGHT;

    //Overlap2D Order: 2 -> 1 -> 0
    private final Texture bg0;
    private final Texture bg1;
    private final Texture bg2;
    private final Vector2 bg0Position1;
    private final Vector2 bg0Position2;
    private final Vector2 bg0Position3;

    private float camOld = 0;

    public ParallaxRenderer(boolean enableParallax) {
        this.enableParallax = enableParallax;

        //Load Textures
        bg0 = new Texture("tmx/backgrounds/background_0.png");
        bg1 = new Texture("tmx/backgrounds/background_1.png");
        bg2 = new Texture("tmx/backgrounds/background_2.png");

        //Size in World Units
        BG_WIDTH = bg0.getWidth() / (Statics.PPM * VIEWPORT_STRETCH_FACTOR);
        BG_HEIGHT = bg0.getHeight() / (Statics.PPM * VIEWPORT_STRETCH_FACTOR);

        //Start-Positions
        bg0Position1 = new Vector2(0,0);
        bg0Position2 = new Vector2(BG_WIDTH - RENDER_OFFSET,0);
        bg0Position3 = new Vector2(BG_WIDTH * 2 - RENDER_OFFSET * 2f, 0);

    }

    public void update(float dt, Camera camera) {
        //Get Viewport metrics
        float camX = camera.position.x;
        float xViewportLeft = camX - camera.viewportWidth / 2f - BG_WIDTH * 1.2f;
        float xViewportRight = camX + camera.viewportWidth / 2f + BG_WIDTH / 2f;
        float speed = camX - camOld;

        //Replace, if camera cant see
        if (bg0Position1.x < xViewportLeft) {
            bg0Position1.add((2.88f - RENDER_OFFSET) * BG_ROW_COUNT, 0);
        } else if (bg0Position1.x > xViewportRight) {
            bg0Position1.add(-1f * (2.88f - RENDER_OFFSET) * BG_ROW_COUNT, 0);
        }
        if (bg0Position2.x < xViewportLeft) {
            bg0Position2.add((2.88f - RENDER_OFFSET) * BG_ROW_COUNT, 0);
        }  else if (bg0Position2.x > xViewportRight) {
            bg0Position2.add(-1f * (2.88f - RENDER_OFFSET) * BG_ROW_COUNT, 0);
        }
        if (bg0Position3.x < xViewportLeft) {
            bg0Position3.add((2.88f - RENDER_OFFSET) * BG_ROW_COUNT,0);
        }  else if (bg0Position3.x > xViewportRight) {
            bg0Position3.add(-1f * (2.88f - RENDER_OFFSET) * BG_ROW_COUNT, 0);
        }

        //Parallax-Effect per Frame
        if (enableParallax) {
            //Movement frameTime * 1/frameTime (Statics.FOREGROUND_FPS) to smooth out
            bg0Position1.add(-1f * speed / PARALLAX_FACTOR * dt * Statics.FOREGROUND_FPS, 0);
            bg0Position2.add(-1f * speed / PARALLAX_FACTOR * dt * Statics.FOREGROUND_FPS, 0);
            bg0Position3.add(-1f * speed / PARALLAX_FACTOR * dt * Statics.FOREGROUND_FPS, 0);
        }

        //Get Viewport metrics
        camOld = camX;
    }

    public void render(SpriteBatch spriteBatch) {
        //draw Backgrounds
       spriteBatch.draw(bg0, bg0Position1.x, bg0Position1.y + Y_ADDITION_LAYER0, BG_WIDTH, BG_HEIGHT);
       spriteBatch.draw(bg0, bg0Position2.x, bg0Position2.y + Y_ADDITION_LAYER0, BG_WIDTH, BG_HEIGHT);
       spriteBatch.draw(bg0, bg0Position3.x, bg0Position3.y + Y_ADDITION_LAYER0, BG_WIDTH, BG_HEIGHT);

        spriteBatch.draw(bg1, bg0Position1.x, bg0Position1.y + Y_ADDITION_LAYER1, BG_WIDTH, BG_HEIGHT);
        spriteBatch.draw(bg1, bg0Position2.x, bg0Position2.y + Y_ADDITION_LAYER1, BG_WIDTH, BG_HEIGHT);
        spriteBatch.draw(bg1, bg0Position3.x, bg0Position3.y + Y_ADDITION_LAYER1, BG_WIDTH, BG_HEIGHT);

        spriteBatch.draw(bg2, bg0Position1.x, bg0Position1.y + Y_ADDITION_LAYER2, BG_WIDTH, BG_HEIGHT);
        spriteBatch.draw(bg2, bg0Position2.x, bg0Position2.y + Y_ADDITION_LAYER2, BG_WIDTH, BG_HEIGHT);
        spriteBatch.draw(bg2, bg0Position3.x, bg0Position3.y + Y_ADDITION_LAYER2, BG_WIDTH, BG_HEIGHT);

    }

    @Override
    public void dispose() {
        //release Video-RAM
        bg0.dispose();
        bg1.dispose();
        bg2.dispose();
    }
}
