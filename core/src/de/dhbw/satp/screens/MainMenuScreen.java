package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.dhbw.satp.main.JumpAndRunMain;
import de.dhbw.satp.main.FinalStatics;
import de.dhbw.satp.scene2d.LevelButton;

public class MainMenuScreen implements Screen {

    private final int LEVELS = 10;

    public Stage stage;
    private final Texture textTexture;
    private final Texture cloudTexture;

    public MainMenuScreen(JumpAndRunMain main) {

        OrthographicCamera camera = new OrthographicCamera();
        Viewport viewport = new ExtendViewport(FinalStatics.VIRTUAL_WIDTH, FinalStatics.VIRTUAL_HEIGHT, camera);
        SpriteBatch batch = new SpriteBatch();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        Table menuTable = new Table();
        menuTable.top();
        menuTable.padTop(10f);
        menuTable.setFillParent(true);

        textTexture = main.assetManager.get("menu/symbols/TEXT_MENU_1.png");
        Image textImage = new Image(textTexture);
        menuTable.add(textImage);

        stage.addActor(menuTable);

        Table cloudTable = new Table();
        cloudTable.bottom();
        cloudTable.setFillParent(true);

        cloudTexture = main.assetManager.get("tmx/backgrounds/background_1.png");
        Image cloudImage = new Image(cloudTexture);
        cloudTable.add(cloudImage).size(FinalStatics.VIRTUAL_WIDTH, FinalStatics.VIRTUAL_HEIGHT);

        stage.addActor(cloudTable);


        LevelButton[] levelButtons = new LevelButton[LEVELS];
        for (int i = 0; i < LEVELS; i++) {
            levelButtons[i] = new LevelButton(i, main);
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

        //stage.addAction(Actions.sequence(Actions.fadeIn(0.3f)));
    }

    @Override
    public void show() {
        System.out.println("started Main-Menu Screen. PRESS F4 TO SKIP TO A LEVEL...");
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0f, 0.7f, 0.93f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        stage.dispose();
        System.out.println("Stopped Main Menu Screen.");
    }
}
