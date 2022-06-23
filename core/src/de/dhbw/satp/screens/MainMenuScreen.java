package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.dhbw.satp.main.JumpAndRunMain;
import de.dhbw.satp.main.FinalStatics;

public class MainMenuScreen implements Screen {

    public Stage stage;
    private final JumpAndRunMain main;
    private Viewport viewport;
    private OrthographicCamera camera;

    private final Table levelTable;

    public MainMenuScreen(JumpAndRunMain main) {
        this.main = main;

        camera = new OrthographicCamera();
        Viewport viewport = new ExtendViewport(FinalStatics.VIRTUAL_WIDTH, FinalStatics.VIRTUAL_HEIGHT, camera);

        levelTable = new Table();
        levelTable.top();
        levelTable.right();
        levelTable.setFillParent(true);

        //stage.addAction(Actions.sequence(Actions.fadeIn(0.3f)));
    }

    @Override
    public void show() {
        System.out.println("Main-Menu Screen gestartet.");
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(1f, 1f, 0f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.screenManager.nextScreen();
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
