package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import de.dhbw.satp.main.Assets;
import de.dhbw.satp.main.JumpAndRunMain;
import de.dhbw.satp.main.NotFinalStatics;

public class InitLoadingScreen implements Screen {

    private final JumpAndRunMain main;

    private final ShapeRenderer shapeRenderer;
    private final Texture texture;
    private final Texture digitsTexture;
    private final Texture percentTexture;
    private final Texture tipTexture;
    private final TextureRegion[][] textureRegion;
    private final TextureRegion[][] digits;

    private float loadFactor = 0.15f;
    private int frameCount = 0;
    private int currentAnimationFrame = 0;

    public InitLoadingScreen(JumpAndRunMain jumpAndRunMain) {
        this.main = jumpAndRunMain;
        shapeRenderer = new ShapeRenderer();

        texture = new Texture("playersprite/skull1.png");
        textureRegion = TextureRegion.split(texture, 13, 20);

        digitsTexture = new Texture("sprite/digits.png");
        digits = TextureRegion.split(digitsTexture, 8, 12);

        percentTexture = new Texture("sprite/percent.png");
        tipTexture = new Texture("menu/tipps/tipp1.png");

    }

    @Override
    public void show() {
        System.out.println("Init Loading Screen started.");

        //pre-load Assets
        loadSynchronizedAssets();
    }

    private void loadSynchronizedAssets() {
        //initial clean
        main.assetManager.clear();

        if (NotFinalStatics.debug) {
            System.out.println("------------------------------ started loading of Assets ------------------------------");
        }

        //SynchronousAssetLoader (synchronized with Render-Thread)
        loadTexturesSync();
        loadShadersSync();
        loadParallaxSync();

        //organize Atlas
        main.assetManager.finishLoading();
        main.assetManager.update();

        if (NotFinalStatics.debug) {
            System.out.println("------------------------------ finished loading of Assets ------------------------------");
            System.out.println("AssetManager dump: \n" + main.assetManager.getDiagnostics());
            System.out.println("------------------------------ finished loading of Assets ------------------------------");
        }
    }

    private void loadShadersSync() {
        main.assetManager.load(Assets.earthQuakeShader);
        main.assetManager.load(Assets.colorShiftShader);
        main.assetManager.load(Assets.passThroughShader);
    }

    private void loadParallaxSync() {
        main.assetManager.load(Assets.defaultBackgroundConfiguration);
        main.assetManager.load(Assets.defaultBackgroundPart0);
        main.assetManager.load(Assets.defaultBackgroundPart1);
        main.assetManager.load(Assets.defaultBackgroundPart2);
    }

    private void loadTexturesSync() {
        //Sprite
        main.assetManager.load(Assets.playerTexture);
        main.assetManager.load(Assets.enemyDefaultSprite);
        main.assetManager.load(Assets.enemySpikeSprite);
        main.assetManager.load(Assets.digitSprite);
        main.assetManager.load(Assets.heartSprite);
        main.assetManager.load(Assets.coinSprite);

        //Menu
        main.assetManager.load(Assets.menuSymbols);
        main.assetManager.load(Assets.menuNumbers);
        main.assetManager.load(Assets.menuButton);
        main.assetManager.load(Assets.menuClearText);
        main.assetManager.load(Assets.menuSkipText);
        main.assetManager.load(Assets.menuBackground);
        main.assetManager.load(Assets.menuSelectLevel);
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
            currentAnimationFrame++;
            if (currentAnimationFrame > 3) {
                currentAnimationFrame = 0;
            }
        }


        main.spriteBatch.begin();
        main.spriteBatch.draw(tipTexture, 10,Gdx.graphics.getHeight() - 250, 1200, 200);
        main.spriteBatch.draw(textureRegion[0][currentAnimationFrame], 50, 80, 65, 100);
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
        tipTexture.dispose();
    }
}
