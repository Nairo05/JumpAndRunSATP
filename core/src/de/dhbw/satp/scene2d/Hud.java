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
import de.dhbw.satp.main.FinalStatics;
import de.dhbw.satp.main.NotFinalStatics;
import de.dhbw.satp.screens.PlayScreen;

public class Hud implements Disposable {

    //Filepath of Sprites
    private static final String DIGIT_SPRITE_PATH = "sprite/digits.png";
    private static final String HEART_SPRITE_PATH = "sprite/heart.png";
    private static final String COIN_SPRITE_PATH = "sprite/spr_coin_strip4.png";

    public Stage stage;
    private final PlayScreen playScreen;
    private final Player player;

    //Tables
    private final Table timerTable;
    private final Table livesTable;
    private final Table coinsTable;

    //Textures
    private final Texture digitsTexture;
    private final Texture livesTexture;
    private final Texture ermeraldTexture;
    private final TextureRegion[][] digits;
    private final TextureRegion[][] ermeraldIcon;

    private int time;
    private int framecount = 0;
    private int lives;

    //Images
    private final Image[] liveImages;
    private final Image emeraldImage;

    public Hud (PlayScreen playScreen, SpriteBatch batch) {

        this.playScreen = playScreen;
        player = playScreen.getPlayer();
        Viewport viewport = new ExtendViewport(FinalStatics.VIRTUAL_WIDTH, FinalStatics.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        //Level timer
        time = 300;
        timerTable = new Table();
        timerTable.top();
        timerTable.right();
        timerTable.setFillParent(true);

        digitsTexture = playScreen.getAssetManager().get(DIGIT_SPRITE_PATH);
        digits = TextureRegion.split(digitsTexture, 8, 12);


        //Lives
        lives = 3;
        livesTable = new Table();
        livesTable.top();
        livesTable.left();
        livesTable.setFillParent(true);

        livesTexture = playScreen.getAssetManager().get(HEART_SPRITE_PATH);

        liveImages = new Image[3];

        for (int i = 0; i < 3; i++) {
            liveImages[i] = new Image(livesTexture);
        }


        //Coins
        coinsTable = new Table();
        coinsTable.top();
        coinsTable.left();
        coinsTable.padTop(18f).padLeft(6f);
        coinsTable.setFillParent(true);

        ermeraldTexture = playScreen.getAssetManager().get(COIN_SPRITE_PATH);
        ermeraldIcon = TextureRegion.split(ermeraldTexture, 16, 16);
        emeraldImage = new Image(ermeraldIcon[0][0]);

    }

    public void update(float dt) {

        //Level timer Management -----------------------------------------------------
        framecount++;
        if ((framecount % NotFinalStatics.FOREGROUND_FPS) == 0) {
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

            timerTable.add(timerImage1).padTop(4f).padRight(4f);
            timerTable.add(timerImage2).padTop(4f).padRight(4f);
            timerTable.add(timerImage3).padTop(4f).padRight(8f);
        } else {
            playScreen.endGame();
        }

        stage.addActor(timerTable);

        //Lives Management ------------------------------------------------------------
        livesTable.clear();
        livesTable.add(liveImages[0]).padTop(2f).padLeft(6f);
        for (int i = 1; i < player.getLives(); i++) {
            livesTable.add(liveImages[i]).padTop(2f);
        }

        stage.addActor(livesTable);

        //Coins Management-------------------------------------------------------------
        int coinDigit1 = (player.getCoins() / 10) % 10;
        int coinDigit2 = player.getCoins() % 10;

        Image coin1 = new Image(digits[0][coinDigit1]);
        Image coin2 = new Image(digits[0][coinDigit2]);

        coinsTable.clear();
        coinsTable.add(emeraldImage);
        coinsTable.add(coin1).padLeft(3f);
        coinsTable.add(coin2).padLeft(2f);

        stage.addActor(coinsTable);

        //------------
        stage.act(dt);
    }

    public void renderStage() {
            stage.draw();
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
