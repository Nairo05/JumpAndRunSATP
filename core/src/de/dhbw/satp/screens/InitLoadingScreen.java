package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import org.w3c.dom.Text;

import de.dhbw.satp.main.JumpAndRunMain;
import de.dhbw.satp.main.assetfragments.ParallaxAsset;
import de.dhbw.satp.main.assetfragments.ShaderAsset;

public class InitLoadingScreen implements Screen {

    private final JumpAndRunMain main;
    private final ShapeRenderer shapeRenderer;
    private float loadFactor = 0.15f;
    private final Texture texture;
    private final TextureRegion[][] textureRegion;
    private int frameCount = 0;
    private int currentFrame = 0;
    private final Texture digitsTexture;
    private final Texture percentTexture;
    private final TextureRegion[][] digits;
    private final Texture tippTexture;

    public InitLoadingScreen(JumpAndRunMain main) {
        this.main = main;
        shapeRenderer = new ShapeRenderer();
        texture = new Texture("playersprite/skull1.png");
        textureRegion = TextureRegion.split(texture, 13, 20);
        digitsTexture = new Texture("sprite/digits.png");
        digits = TextureRegion.split(digitsTexture, 8, 12);
        percentTexture = new Texture("sprite/percent.png");
        tippTexture = new Texture("menu/tipps/tipp1.png");

    }

    @Override
    public void show() {
        System.out.println("Init Loading Screen started.");

        //load all Assets
        loadSynchronizedAssets();
    }

    private void loadSynchronizedAssets() {
        //initial clean
        main.assetManager.clear();

        System.out.println("------------------------------ started loading of Assets ------------------------------");

        //SynchronousAssetLoader (synchronized with Render-Thread)
        loadTexturesSync();
        loadShadersSync();
        loadParallaxSync();

        //organize Atlas
        main.assetManager.finishLoading();
        main.assetManager.update();

        System.out.println("------------------------------ finished loading of Assets ------------------------------");
        System.out.println("AssetManager dump: \n" + main.assetManager.getDiagnostics());
        System.out.println("------------------------------ finished loading of Assets ------------------------------");
    }

    private void loadShadersSync() {
        main.assetManager.load("shaders/earthquake", ShaderAsset.class);
        main.assetManager.load("shaders/colorshift", ShaderAsset.class);
        main.assetManager.load("shaders/passthrough", ShaderAsset.class);
    }

    private void loadParallaxSync() {
        main.assetManager.load("tmx/1-1.parallax", ParallaxAsset.class);
        main.assetManager.load("tmx/backgrounds/background_0.png", Texture.class);
        main.assetManager.load("tmx/backgrounds/background_1.png", Texture.class);
        main.assetManager.load("tmx/backgrounds/background_2.png", Texture.class);
    }

    private void loadTexturesSync() {
        //Sprite
        main.assetManager.load("playersprite/skull1.png", Texture.class);
        main.assetManager.load("sprite/enemy/Bloated Bedbug/BloatedBedbugIdleSide.png", Texture.class);
        main.assetManager.load("sprite/enemy/Lethal Scorpion/LethalScorpionIdleSide.png", Texture.class);
        main.assetManager.load("sprite/digits.png", Texture.class);
        main.assetManager.load("sprite/heart.png", Texture.class);
        main.assetManager.load("sprite/spr_coin_strip4.png", Texture.class);

        //Menu
        main.assetManager.load("menu/symbols/TEXT_MENU_1.png", Texture.class);
        main.assetManager.load("menu/menunumbers.png", Texture.class);
        main.assetManager.load("menu/ui/BTN_GREEN_SQ.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        main.assetManager.update();

        loadFactor += 0.0025f;
        frameCount++;

        float loadingBar = loadFactor * (Gdx.graphics.getWidth() - 64);
        int digit1 = (int) ((loadFactor * 1000) / 100) % 10;
        int digit2 = (int) ((loadFactor * 1000) / 10) % 10;

        Gdx.gl20.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.rect(30,30,loadingBar,32);
        shapeRenderer.end();

        if (loadingBar >= (Gdx.graphics.getWidth() - 80) && main.assetManager.isFinished()) {
            main.screenManager.nextScreen();
        }


        if (frameCount % 10 == 0) {
            currentFrame++;
            if (currentFrame > 3) {
                currentFrame = 0;
            }
        }


        main.spriteBatch.begin();
        main.spriteBatch.draw(tippTexture, 10,Gdx.graphics.getHeight() - 250, 1200, 200);
        main.spriteBatch.draw(textureRegion[0][currentFrame], 50, 80, 65, 100);
        main.spriteBatch.draw(digits[0][digit1], loadingBar - 52, 65, 24, 36);
        main.spriteBatch.draw(digits[0][digit2], loadingBar - 26, 65, 24, 36);
        main.spriteBatch.draw(percentTexture, loadingBar, 65, 24, 36);
        main.spriteBatch.end();
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
        System.out.println("InitLoadingScreen dispose call");
        shapeRenderer.dispose();
        texture.dispose();
        digitsTexture.dispose();
        percentTexture.dispose();
    }
}
