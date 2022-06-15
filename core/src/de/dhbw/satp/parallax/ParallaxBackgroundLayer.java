package de.dhbw.satp.parallax;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import de.dhbw.satp.main.Statics;

public class ParallaxBackgroundLayer implements Disposable {

    private final float VIEWPORT_STRETCH_FACTOR = 1f;
    private float parallaxFactor = 26f;

    private final Texture bg;
    private float yOffset;
    private float rowCount = 3;

    private final Array<Vector2> positions;

    private final float backgroundWith;
    private final float backgroundHeigth;

    public ParallaxBackgroundLayer(Texture texture) {
        bg = texture;
        positions = new Array<>();
        yOffset = 0f;

        backgroundWith = bg.getWidth() / (Statics.PPM * VIEWPORT_STRETCH_FACTOR);
        backgroundHeigth = bg.getHeight() / (Statics.PPM * VIEWPORT_STRETCH_FACTOR);
    }

    public void addBackGround(float x) {
        positions.add(new Vector2(x, 0f));
    }

    public int getBackgroundCount() {
        return positions.size;
    }

    public Vector2 getPosition(int index) {
        if (index < positions.size) {
            return positions.get(index);
        }
        return Vector2.Zero;
    }

    public Texture getBg() {
        return bg;
    }

    public float getBackgroundWith() {
        return backgroundWith;
    }

    public float getBackgroundHeigth() {
        return backgroundHeigth;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public float getParallaxFactor() {
        return parallaxFactor;
    }

    public void setParallaxFactor(float parallaxFactor) {
        this.parallaxFactor = parallaxFactor;
    }

    public float getRowCount() {
        return rowCount;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    @Override
    public void dispose() {
        bg.dispose();
    }
}
