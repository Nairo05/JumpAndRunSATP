package de.dhbw.satp.parallax;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import de.dhbw.satp.main.Statics;

public class ParallaxRenderer implements Disposable {

    private final float RENDER_OFFSET = 0.005f;
    private final float PARALLAY_FACTOR = 26f;
    private final float BG_ROW_COUNT = 3;

    private final float Y_ADDITION_LAYER0 = 0.30f;
    private final float Y_ADDITION_LAYER1 = Y_ADDITION_LAYER0 / 2;
    private final float Y_ADDITION_LAYER2 = -0.02f;

    private final float BG_WIDTH;
    private final float BG_HEIGTH;

    // 2 auf 1 auf 0
    private final Texture bg0;
    private final Texture bg1;
    private final Texture bg2;
    private final Vector2 bg0Position1;
    private final Vector2 bg0Position2;
    private final Vector2 bg0Position3;

    private float camalt = 0;

    public ParallaxRenderer() {
        bg0 = new Texture("tmx/backgrounds/background_0.png");
        bg1 = new Texture("tmx/backgrounds/background_1.png");
        bg2 = new Texture("tmx/backgrounds/background_2.png");

        BG_WIDTH = bg0.getWidth() / (Statics.PPM * 1f);
        BG_HEIGTH = bg0.getHeight() / (Statics.PPM * 1f);

        bg0Position1 = new Vector2(0,0);
        bg0Position2 = new Vector2(BG_WIDTH - RENDER_OFFSET,0);
        bg0Position3 = new Vector2(BG_WIDTH * 2 - RENDER_OFFSET * 2f, 0);

    }

    public void update(float dt, Camera camera) {
        float camX = camera.position.x;
        float xViewportLeft = camX - camera.viewportWidth / 2 - BG_WIDTH;
        float speed = camX - camalt;

        if (bg0Position1.x < xViewportLeft) {
            bg0Position1.add((2.88f - RENDER_OFFSET) * BG_ROW_COUNT, 0);
        }
        if (bg0Position2.x < xViewportLeft) {
            bg0Position2.add((2.88f - RENDER_OFFSET) * BG_ROW_COUNT, 0);
        }
        if (bg0Position3.x < xViewportLeft) {
            bg0Position3.add((2.88f - RENDER_OFFSET) * BG_ROW_COUNT,0);
        }

        bg0Position1.add(-speed / PARALLAY_FACTOR, 0);
        bg0Position2.add(-speed / PARALLAY_FACTOR, 0);
        bg0Position3.add(-speed / PARALLAY_FACTOR, 0);

        camalt = camX;

    }

    public void render(SpriteBatch spriteBatch) {
       spriteBatch.draw(bg0, bg0Position1.x, bg0Position1.y + Y_ADDITION_LAYER0, BG_WIDTH, BG_HEIGTH);
       spriteBatch.draw(bg0, bg0Position2.x, bg0Position2.y + Y_ADDITION_LAYER0, BG_WIDTH, BG_HEIGTH);
       spriteBatch.draw(bg0, bg0Position3.x, bg0Position3.y + Y_ADDITION_LAYER0, BG_WIDTH, BG_HEIGTH);

        spriteBatch.draw(bg1, bg0Position1.x, bg0Position1.y + Y_ADDITION_LAYER1, BG_WIDTH, BG_HEIGTH);
        spriteBatch.draw(bg1, bg0Position2.x, bg0Position2.y + Y_ADDITION_LAYER1, BG_WIDTH, BG_HEIGTH);
        spriteBatch.draw(bg1, bg0Position3.x, bg0Position3.y + Y_ADDITION_LAYER1, BG_WIDTH, BG_HEIGTH);

        spriteBatch.draw(bg2, bg0Position1.x, bg0Position1.y + Y_ADDITION_LAYER2, BG_WIDTH, BG_HEIGTH);
        spriteBatch.draw(bg2, bg0Position2.x, bg0Position2.y + Y_ADDITION_LAYER2, BG_WIDTH, BG_HEIGTH);
        spriteBatch.draw(bg2, bg0Position3.x, bg0Position3.y + Y_ADDITION_LAYER2, BG_WIDTH, BG_HEIGTH);

    }

    @Override
    public void dispose() {
        bg0.dispose();
        bg1.dispose();
        bg2.dispose();
    }
}
