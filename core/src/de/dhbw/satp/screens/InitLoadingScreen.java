package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import de.dhbw.satp.main.FinalStatics;
import de.dhbw.satp.main.JumpAndRunMain;

public class InitLoadingScreen implements Screen {

    private final JumpAndRunMain main;
    private int counter = 20;
    private ShapeRenderer shapeRenderer;
    private int load;

    public InitLoadingScreen(JumpAndRunMain main) {
        this.main = main;
        this.loadTextures();
        shapeRenderer = new ShapeRenderer();
        load = 32;
    }

    @Override
    public void show() {
        System.out.println("Init Loading Screen started.");
    }

    private void loadTextures() {
        main.assetManager.clear();
        main.assetManager.load("playersprite/skull1.png", Texture.class);
        main.assetManager.load("sprite/enemy/Bloated Bedbug/BloatedBedbugIdleSide.png", Texture.class);
        main.assetManager.load("menu/symbols/TEXT_MENU_1.png", Texture.class);
        main.assetManager.load("tmx/backgrounds/background_0.png", Texture.class);
        main.assetManager.load("tmx/backgrounds/background_1.png", Texture.class);
        main.assetManager.load("tmx/backgrounds/background_2.png", Texture.class);
        main.assetManager.load("menu/menunumbers.png", Texture.class);
        main.assetManager.load("menu/ui/BTN_GREEN_SQ.png", Texture.class);
        main.assetManager.load("sprite/digits.png", Texture.class);
        main.assetManager.load("sprite/heart.png", Texture.class);
        main.assetManager.finishLoading();
        main.assetManager.update();
    }

    @Override
    public void render(float delta) {

        Gdx.gl20.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.rect(30,30,load,32);
        shapeRenderer.end();

        if (load < Gdx.graphics.getWidth() - 64) {
            load += 10;
        } else {
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
        System.out.println("Ich ürde gelöscht ich bin initloading");
    }
}
