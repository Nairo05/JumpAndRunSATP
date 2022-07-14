package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import de.dhbw.satp.main.Assets;
import de.dhbw.satp.main.JumpAndRunMain;

public class LevelLoadingScreen implements Screen {

    private final ShapeRenderer shapeRenderer;
    private final JumpAndRunMain jumpAndRunMain;
    private float counter = 0;

    public LevelLoadingScreen(JumpAndRunMain jumpAndRunMain) {
        this.jumpAndRunMain = jumpAndRunMain;
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        System.out.println("Ich bin der Level-Loading Scrrennnn");

        jumpAndRunMain.assetManager.load(Assets.level11);
        jumpAndRunMain.assetManager.finishLoading();
    }

    @Override
    public void render(float dt) {
        jumpAndRunMain.assetManager.update();

        Gdx.gl20.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.rect(30,30,counter,32);
        shapeRenderer.end();

        if (counter >= (Gdx.graphics.getWidth() - 80) && jumpAndRunMain.assetManager.isFinished()) {
            jumpAndRunMain.screenManager.nextScreen();
        }

        counter += 2000f * dt;
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
