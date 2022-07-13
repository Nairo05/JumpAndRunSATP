package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import de.dhbw.satp.main.Assets;
import de.dhbw.satp.main.JumpAndRunMain;

public class LevelLoadingScreen implements Screen {

    private final JumpAndRunMain jumpAndRunMain;
    private int counter = 20;

    public LevelLoadingScreen(JumpAndRunMain jumpAndRunMain) {
        this.jumpAndRunMain = jumpAndRunMain;
    }

    @Override
    public void show() {
        System.out.println("Ich bin der Level-Loading Scrrennnn");

        //TODO nicht hier main und wo anders JumpandRunMain
        jumpAndRunMain.assetManager.load(Assets.level11);
        jumpAndRunMain.assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        jumpAndRunMain.assetManager.update();

        Gdx.gl20.glClearColor(0f, 0f, 1f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (counter > 0) {
            counter--;
        }
        if (counter == 0 && jumpAndRunMain.assetManager.isFinished()) {
            jumpAndRunMain.screenManager.nextScreen();
        }
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
        System.out.println("LevelLoadingScreen dispose call");
    }
}
