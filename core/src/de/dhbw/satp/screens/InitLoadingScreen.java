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

    private final JumpAndRunMain jumpAndRunMain;

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
        this.jumpAndRunMain = jumpAndRunMain;
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
        jumpAndRunMain.assetManager.clear();

        if (NotFinalStatics.debug) {
            System.out.println("------------------------------ started loading of Assets ------------------------------");
        }

        //SynchronousAssetLoader (synchronized with Render-Thread)
        loadTexturesSync();
        loadShadersSync();
        loadParallaxSync();

        //organize Atlas
        jumpAndRunMain.assetManager.finishLoading();
        jumpAndRunMain.assetManager.update();

        if (NotFinalStatics.debug) {
            System.out.println("------------------------------ finished loading of Assets ------------------------------");
            System.out.println("AssetManager dump: \n" + jumpAndRunMain.assetManager.getDiagnostics());
            System.out.println("------------------------------ finished loading of Assets ------------------------------");
        }
    }

    private void loadShadersSync() {
        jumpAndRunMain.assetManager.load(Assets.earthQuakeShader);
        jumpAndRunMain.assetManager.load(Assets.colorShiftShader);
        jumpAndRunMain.assetManager.load(Assets.passThroughShader);
    }

    private void loadParallaxSync() {
        jumpAndRunMain.assetManager.load(Assets.defaultBackgroundConfiguration);
        jumpAndRunMain.assetManager.load(Assets.defaultBackgroundPart0);
        jumpAndRunMain.assetManager.load(Assets.defaultBackgroundPart1);
        jumpAndRunMain.assetManager.load(Assets.defaultBackgroundPart2);
    }

    private void loadTexturesSync() {
        //Sprite
        jumpAndRunMain.assetManager.load(Assets.playerTexture);
        jumpAndRunMain.assetManager.load(Assets.enemyDefaultSprite);
        jumpAndRunMain.assetManager.load(Assets.enemySpikeSprite);
        jumpAndRunMain.assetManager.load(Assets.digitSprite);
        jumpAndRunMain.assetManager.load(Assets.heartSprite);
        jumpAndRunMain.assetManager.load(Assets.coinSprite);

        //Menu
        jumpAndRunMain.assetManager.load(Assets.menuSymbols);
        jumpAndRunMain.assetManager.load(Assets.menuNumbers);
        jumpAndRunMain.assetManager.load(Assets.menuButton);
        jumpAndRunMain.assetManager.load(Assets.menuClearText);
        jumpAndRunMain.assetManager.load(Assets.menuFailedText);
        jumpAndRunMain.assetManager.load(Assets.menuSkipText);
        jumpAndRunMain.assetManager.load(Assets.menuBackground);
        jumpAndRunMain.assetManager.load(Assets.menuSelectLevel);
    }

    @Override
    public void render(float dt) {
        jumpAndRunMain.assetManager.update();

        loadFactor += (dt / 4f);
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

        if (loadingBar >= (Gdx.graphics.getWidth() - 80) && jumpAndRunMain.assetManager.isFinished()) {
            jumpAndRunMain.screenManager.nextScreen();
        }


        if (frameCount % 10 == 0) {
            currentAnimationFrame++;
            if (currentAnimationFrame > 3) {
                currentAnimationFrame = 0;
            }
        }


        jumpAndRunMain.spriteBatch.begin();
        jumpAndRunMain.spriteBatch.draw(tipTexture, 10,Gdx.graphics.getHeight() - 250, 1200, 200);
        jumpAndRunMain.spriteBatch.draw(textureRegion[0][currentAnimationFrame], 50, 80, 65, 100);
        jumpAndRunMain.spriteBatch.draw(digits[0][digit1], loadingBar - 52, 65, 24, 36);
        jumpAndRunMain.spriteBatch.draw(digits[0][digit2], loadingBar - 26, 65, 24, 36);
        jumpAndRunMain.spriteBatch.draw(percentTexture, loadingBar, 65, 24, 36);
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
        System.out.println("InitLoadingScreen dispose call");
        shapeRenderer.dispose();
        texture.dispose();
        digitsTexture.dispose();
        percentTexture.dispose();
        tipTexture.dispose();
    }
}
