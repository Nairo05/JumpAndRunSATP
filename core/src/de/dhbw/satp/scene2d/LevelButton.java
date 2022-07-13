package de.dhbw.satp.scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;

import de.dhbw.satp.main.Assets;
import de.dhbw.satp.main.JumpAndRunMain;
import de.dhbw.satp.main.NotFinalStatics;

public class LevelButton extends Actor implements Disposable {

    private final JumpAndRunMain main;
    private final Texture buttonTexture;
    private final Texture idTexture;
    private final Group group;
    private final int id;


    public LevelButton(int id, JumpAndRunMain main) {
        this.id = id;
        this.main = main;

        buttonTexture = main.assetManager.get(Assets.menuButton);
        idTexture = main.assetManager.get(Assets.menuNumbers);
        TextureRegion[][] textureRegions = TextureRegion.split(idTexture, 48, 64);

        Image imageButton = new Image(buttonTexture);
        imageButton.setScaling(Scaling.fit);
        imageButton.setWidth(69 * 0.75f);
        imageButton.setHeight(52 * 0.75f);

        Image imageNumber = new Image(textureRegions[0][id % 11]);
        imageNumber.setSize(30f, 30f);
        imageNumber.setPosition(8f, 3f);

        group = new Group();
        group.addActor(imageButton);
        group.addActor(imageNumber);
        group.addListener(new MenuClickListener());

    }

    public Group getGroup() {
        return group;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void dispose() {
    }

    private class MenuClickListener extends ClickListener {

        @Override
        public void clicked(InputEvent event, float x, float y) {
            if (NotFinalStatics.debug) {
                System.out.println("Clicked " + id + " on: " + x + " " + y);
            }
            NotFinalStatics.levelPath = "tmx/1-" + id + ".tmx";
            main.screenManager.nextScreen();
        }
    }
}