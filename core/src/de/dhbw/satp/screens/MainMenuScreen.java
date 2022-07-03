package de.dhbw.satp.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.dhbw.satp.main.JumpAndRunMain;
import de.dhbw.satp.main.FinalStatics;
import de.dhbw.satp.scene2d.LevelButton;

public class MainMenuScreen implements Screen {

    private final int LEVELS = 10;
    private final float scaleFactor = 2.1f;

    public Stage stage;
    private final Texture textTexture;
    private final Texture cloudTexture;
    private JumpAndRunMain main;

    private ParticleEffect particleEffect;

    public MainMenuScreen(JumpAndRunMain main) {
        this.main = main;

        cloudTexture = main.assetManager.get("menu/ui/background.png");
        textTexture =  main.assetManager.get("menu/ui/selectlevel.png");

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particle/whitefly.p"), Gdx.files.internal("particle/"));
        particleEffect.getEmitters().first().setPosition(100,100);
        particleEffect.start();

        OrthographicCamera camera = new OrthographicCamera();
        Viewport viewport = new ExtendViewport(FinalStatics.VIRTUAL_WIDTH, FinalStatics.VIRTUAL_HEIGHT, camera);
        SpriteBatch batch = new SpriteBatch();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        Table menuTable = new Table();
        menuTable.top();
        menuTable.padTop(10f);
        menuTable.setFillParent(true);

        stage.addActor(menuTable);


        LevelButton[] levelButtons = new LevelButton[LEVELS];
        for (int i = 0; i < LEVELS; i++) {
            levelButtons[i] = new LevelButton(i, main);
        }

        Table levelTable = new Table();
        levelTable.bottom();
        levelTable.center();
        levelTable.padLeft(30f);
        levelTable.setFillParent(true);

        for(int i = 0; i < LEVELS; i++) {
            if (i % 5 == 0) {
                levelTable.row();
            }
            levelTable.add(levelButtons[i].getGroup())
                    .padTop(60f).padRight(65f);
        }

        stage.addActor(levelTable);
        stage.act(Gdx.graphics.getDeltaTime());

        //stage.addAction(Actions.sequence(Actions.fadeIn(0.3f)));
    }

    @Override
    public void show() {
        System.out.println("started Main-Menu Screen. PRESS F4 TO SKIP TO A LEVEL...");
    }

    @Override
    public void render(float delta) {
        particleEffect.update(delta);

        Gdx.gl20.glClearColor(0.180f, 0.353f, 0.537f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.spriteBatch.begin();
        main.spriteBatch.draw(cloudTexture, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        main.spriteBatch.draw(textTexture, 160, Gdx.graphics.getHeight() - 160);
        particleEffect.draw(main.spriteBatch);
        main.spriteBatch.end();

        stage.draw();
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
        System.out.println("MainMenuScreen dispose call");
        stage.dispose();
    }
}
