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

    public Stage stage;
    private final PlayScreen playScreen;

    private final Table timerTable;

    private int time;
    private final Texture digitsTexture;
    private final TextureRegion[][] digits;
    private int framecount = 0;

    public Hud (PlayScreen playScreen, SpriteBatch batch) {
        this.playScreen = playScreen;
        Viewport viewport = new ExtendViewport(Statics.VIRTUAL_WIDTH, Statics.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        Player player = playScreen.getPlayer();

        time = 120;
        timerTable = new Table();
        timerTable.top();
        timerTable.right();
        timerTable.setFillParent(true);

        digitsTexture = new Texture("sprite/digits.png");
        digits = TextureRegion.split(digitsTexture, 8, 12);

        System.out.println("HUD-Constructor abgearbeitet...");
    }

    public void update(float dt) {

        //Level-Time Management
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
            Image timerImage1 = new Image(digits[0][timerDigit1]);
            Image timerImage2 = new Image(digits[0][timerDigit2]);
            Image timerImage3 = new Image(digits[0][timerDigit3]);

            timerTable.add(timerImage1).padTop(16f).padRight(4f);
            timerTable.add(timerImage2).padTop(16f).padRight(4f);
            timerTable.add(timerImage3).padTop(16f).padRight(16f);
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
