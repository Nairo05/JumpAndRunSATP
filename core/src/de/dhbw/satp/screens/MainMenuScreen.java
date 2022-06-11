package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import de.dhbw.satp.main.JumpAndRunMain;

public class MainMenuScreen implements Screen {

    private final JumpAndRunMain main;
    private int counter = 20;

    public MainMenuScreen(JumpAndRunMain main) {
        this.main = main;
    }

    @Override
    public void show() {
        System.out.println("Ich bin der Main-Menu Scrrennnn");
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(1f, 1f, 0f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (counter > 0) {
            counter--;
        }
        if (counter == 0 ) {
            main.screenManager.nextScreen();
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
        System.out.println("MainMenuScrren gelöscht!!");
    }
}
