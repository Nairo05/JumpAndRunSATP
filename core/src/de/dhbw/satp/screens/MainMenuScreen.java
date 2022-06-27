package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.dhbw.satp.main.JumpAndRunMain;
import de.dhbw.satp.main.FinalStatics;
import de.dhbw.satp.scene2d.LevelButton;

public class MainMenuScreen implements Screen {

    private final int LEVELS = 10;

    public Stage stage;
    private SpriteBatch batch;
    private final JumpAndRunMain main;
    private Viewport viewport;
    private OrthographicCamera camera;

    private LevelButton[] levelButtons;
    private final Table levelTable;

    public MainMenuScreen(JumpAndRunMain main) {
        this.main = main;

        camera = new OrthographicCamera();
        Viewport viewport = new ExtendViewport(FinalStatics.VIRTUAL_WIDTH, FinalStatics.VIRTUAL_HEIGHT, camera);
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        levelButtons = new LevelButton[LEVELS];
        for (int i = 0; i < LEVELS; i++) {
            levelButtons[i] = new LevelButton(i);
        }

        levelTable = new Table();
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
        Gdx.gl20.glClearColor(1f, 1f, 0f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        //main.screenManager.nextScreen();
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
        System.out.println("Main-Menu Screen beendet.");
    }
}
