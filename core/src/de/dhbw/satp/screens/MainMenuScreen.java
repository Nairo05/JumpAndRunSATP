package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.dhbw.satp.main.Assets;
import de.dhbw.satp.main.JumpAndRunMain;
import de.dhbw.satp.main.FinalStatics;
import de.dhbw.satp.main.NotFinalStatics;
import de.dhbw.satp.scene2d.LevelButton;

public class MainMenuScreen implements Screen {

    private static final int LEVELS = 10;
    private static final String PATH_PREFIX = "particle/";
    private static final String WHITE_FLY_EFFECT = "whitefly.p";

    public Stage stage;

    private final Texture textTexture;
    private final Texture cloudTexture;
    private final JumpAndRunMain jumpAndRunMain;
    private final ParticleEffect particleEffect;

    public MainMenuScreen(JumpAndRunMain jumpAndRunMain) {
        this.jumpAndRunMain = jumpAndRunMain;

        cloudTexture = jumpAndRunMain.assetManager.get(Assets.menuBackground);
        textTexture =  jumpAndRunMain.assetManager.get(Assets.menuSelectLevel);

        Image textImage = new Image(textTexture);
        textImage.setScaling(Scaling.stretch);
        textImage.setWidth(230f);
        textImage.setHeight(36f);
        textImage.setPosition(55f, 160f);

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal(PATH_PREFIX + WHITE_FLY_EFFECT), Gdx.files.internal(PATH_PREFIX));
        particleEffect.getEmitters().first().setPosition(100,100);
        particleEffect.start();

        OrthographicCamera camera = new OrthographicCamera();
        Viewport viewport = new ExtendViewport(FinalStatics.VIRTUAL_WIDTH, FinalStatics.VIRTUAL_HEIGHT, camera);
        SpriteBatch batch = new SpriteBatch();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        Table menuTable = new Table();
        menuTable.top();
        menuTable.padTop(10f);
        menuTable.setFillParent(true);

        stage.addActor(textImage);
        stage.addActor(menuTable);


        LevelButton[] levelButtons = new LevelButton[LEVELS];
        for (int i = 0; i < LEVELS; i++) {
            levelButtons[i] = new LevelButton(i, jumpAndRunMain);
        }

        Table levelTable = new Table();
        levelTable.bottom();
        levelTable.center();
        levelTable.padLeft(30f);
        levelTable.setFillParent(true);

        for(int i = 0; i < LEVELS; i++) {
            if (i % 5 == 0) {
                levelTable.row();
            }
            levelTable.add(levelButtons[i].getGroup())
                    .padTop(60f).padRight(65f);
        }

        stage.addActor(levelTable);
        stage.act(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void show() {
        if (NotFinalStatics.debug) {
            System.out.println("started Main-Menu Screen. PRESS F4 TO SKIP TO A LEVEL...");
        }
    }

    @Override
    public void render(float delta) {
        particleEffect.update(delta);

        Gdx.gl20.glClearColor(0.180f, 0.353f, 0.537f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        jumpAndRunMain.spriteBatch.begin();
        jumpAndRunMain.spriteBatch.draw(cloudTexture, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        particleEffect.draw(jumpAndRunMain.spriteBatch);
        jumpAndRunMain.spriteBatch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        if (NotFinalStatics.debug) {
            System.out.println("MainMenuScreen dispose call");
        }
        stage.dispose();
    }
}
