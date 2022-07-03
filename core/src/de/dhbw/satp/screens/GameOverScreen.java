package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import org.w3c.dom.Text;

import de.dhbw.satp.main.JumpAndRunMain;

public class GameOverScreen implements Screen {

    private JumpAndRunMain jumpAndRunMain;
    private Texture clearTexture;
    private Texture clickToSkip;
    private int counter = 1200;

    public GameOverScreen(JumpAndRunMain jumpAndRunMain) {
        this.jumpAndRunMain = jumpAndRunMain;

        clearTexture = jumpAndRunMain.assetManager.get("menu/ui/levelclear.png");
        clickToSkip = jumpAndRunMain.assetManager.get("menu/ui/clicktoskip.png");
    }

    @Override
    public void show() {
        System.out.println("Ich bin der Game-Over Scrrennnn");
    }

    @Override
    public void render(float delta) {
        counter--;

        if (counter < 1080) {
            if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
                jumpAndRunMain.screenManager.nextScreen();
            }
        }

        Gdx.gl20.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        jumpAndRunMain.spriteBatch.begin();
        jumpAndRunMain.spriteBatch.draw(clearTexture, 60, 70, 300, 100);
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
        System.out.println("GameOverScreen dispose call");
    }
}
