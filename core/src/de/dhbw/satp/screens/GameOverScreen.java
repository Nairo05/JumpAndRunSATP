package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import de.dhbw.satp.main.Assets;
import de.dhbw.satp.main.JumpAndRunMain;
import de.dhbw.satp.main.NotFinalStatics;

public class GameOverScreen implements Screen {

    private JumpAndRunMain jumpAndRunMain;
    private Texture clearTexture;
    private Texture failedTexture;
    private Texture clickToSkip;
    private int counter = 1200;
    private boolean wonGame;

    public GameOverScreen(JumpAndRunMain jumpAndRunMain, boolean wonGame) {
        this.jumpAndRunMain = jumpAndRunMain;
        this.wonGame = wonGame;

        clearTexture = jumpAndRunMain.assetManager.get(Assets.menuClearText);
        failedTexture = jumpAndRunMain.assetManager.get(Assets.menuFailedText);
        clickToSkip = jumpAndRunMain.assetManager.get(Assets.menuSkipText);
    }

    @Override
    public void show() {
        if (NotFinalStatics.debug) {
            System.out.println("Started Game Over Screen");
        }

        jumpAndRunMain.assetManager.unload(Assets.level11.fileName);
    }

    @Override
    public void render(float dt) {
        jumpAndRunMain.assetManager.update();

        counter--;

        if (counter < 1080) {
            if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
                jumpAndRunMain.screenManager.nextScreen();
            }
        }

        if (wonGame) {
            Gdx.gl20.glClearColor(0f, 0f, 0f, 1f);
            Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        } else {
            Gdx.gl20.glClearColor(1f, 0f, 0f, 1f);
            Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }

        jumpAndRunMain.spriteBatch.begin();
        if (wonGame) {
            jumpAndRunMain.spriteBatch.draw(clearTexture, 60, 70, 300, 100);
        } else {
            jumpAndRunMain.spriteBatch.draw(failedTexture, 60, 70, 300, 100);
        }
        if (counter < 1080) {
            jumpAndRunMain.spriteBatch.draw(clickToSkip, 130, 5, 150, 12);
        }
        jumpAndRunMain.spriteBatch.end();
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
            System.out.println("GameOverScreen dispose call");
        }
    }
}
