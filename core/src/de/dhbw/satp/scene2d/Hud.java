package de.dhbw.satp.scene2d;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.dhbw.satp.gameobjects.Player;
import de.dhbw.satp.main.Statics;
import de.dhbw.satp.screens.PlayScreen;

public class Hud implements Disposable {

    //Filepath of Sprites
    private static final String DIGIT_SPRITE_PATH = "sprite/digits.png";

    public Stage stage;
    private final PlayScreen playScreen;

    //Level timer
    private final Table timerTable;
    private final Texture digitsTexture;
    private int time;
    private int framecount = 0;

    private final Image[] images;

    public Hud (PlayScreen playScreen, SpriteBatch batch) {

        this.playScreen = playScreen;
        Viewport viewport = new ExtendViewport(Statics.VIRTUAL_WIDTH, Statics.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        //Level timer
        time = 120;
        timerTable = new Table();
        timerTable.top();
        timerTable.right();
        timerTable.setFillParent(true);

        digitsTexture = new Texture(DIGIT_SPRITE_PATH);
        TextureRegion[][] digits = TextureRegion.split(digitsTexture, 8, 12);

        images = new Image[10];

        for (int i = 0; i < 10; i++) {
            images[i] = new Image(digits[0][i]);
        }

    }

    public void update(float dt) {

        //Level timer Management -----------------------------------------------------
        framecount++;
        if ((framecount % Statics.FOREGROUND_FPS) == 0) {
            boolean finishedLevel = false;
            if (!finishedLevel) {
                time--;
                System.out.println(time);
            }
        }

        int timerDigit3 = time % 10;
        int timerDigit2 = (time / 10) % 10;
        int timerDigit1 = (time / 100) % 10;

        if (time >= 0) {
            timerTable.clear();

            timerTable.add(images[timerDigit1]).padTop(16f).padRight(4f);
            timerTable.add(images[timerDigit2]).padTop(16f).padRight(4f);
            timerTable.add(images[timerDigit3]).padTop(16f).padRight(16f);
        } else {
            playScreen.endGame();
        }

        stage.addActor(timerTable);

        stage.act(dt);
    }

    public void renderStage() {
            stage.draw();
    }


    @Override
    public void dispose() {
        stage.dispose();
        digitsTexture.dispose();
    }
}
