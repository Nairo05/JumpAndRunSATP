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
    private Viewport viewport;
    private PlayScreen playScreen;
    private Player player;

    private Table timerTable;

    private int time;
    private int timerDigit1 = 0;
    private int timerDigit2 = 0;
    private int timerDigit3 = 0;
    private Image timerImage1;
    private Image timerImage2;
    private Image timerImage3;
    private Texture digitsTexture;
    private TextureRegion[][] digits;
    private int framecount = 0;
    private boolean finishedLevel = false;

    public Hud (PlayScreen playScreen, SpriteBatch batch) {
        this.playScreen = playScreen;
        viewport = new ExtendViewport(Statics.VIRTUAL_WIDTH, Statics.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        player = playScreen.getPlayer();

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
        if ((framecount % Statics.FOREGROUND_FPS) == 0) { //TODO: Das zu exakten Sekunden machen
            if (!finishedLevel) {
                time--;
                System.out.println(time);
            }
        }

        timerDigit3 = time % 10;
        timerDigit2 = (time / 10) % 10;
        timerDigit1 = (time / 100) % 10;

        if (time >= 0) {
            timerTable.clear();
            timerImage1 = new Image(digits[0][timerDigit1]);
            timerImage2 = new Image(digits[0][timerDigit2]);
            timerImage3 = new Image(digits[0][timerDigit3]);

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
