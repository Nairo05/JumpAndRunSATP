package de.dhbw.satp.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class StageText2D extends Actor {

    private final BitmapFont font;
    private final float posX;
    private final float posY;

    private String text;

    public StageText2D(float posX, float posY) {
        this.text = "";
        this.posX = posX;
        this.posY = posY;

        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, text, posX, posY);
        super.draw(batch, parentAlpha);
    }

    public void setText(String text) {
        this.text = text;
    }
}
