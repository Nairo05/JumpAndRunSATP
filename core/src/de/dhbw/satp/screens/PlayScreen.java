package de.dhbw.satp.screens;

import static de.dhbw.satp.main.Statics.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;

import de.dhbw.satp.gameobjects.DynamicEntity;
import de.dhbw.satp.gameobjects.EntityManager;
import de.dhbw.satp.gameobjects.Player;
import de.dhbw.satp.gameobjects.TestEntity;
import de.dhbw.satp.gameobjects.ToSpawnObjectDefinition;
import de.dhbw.satp.scene2d.DebugOnScreenDisplay;
import de.dhbw.satp.main.JumpAndRunMain;
import de.dhbw.satp.main.Statics;
import de.dhbw.satp.world.MapCreator;
import de.dhbw.satp.world.MyContactListener;


public class PlayScreen implements Screen {

    private final JumpAndRunMain jumpAndRunMain;

    private final World world;
    public MyContactListener myContactListener;

    private final OrthographicCamera camera;
    private final Viewport viewport;

    private final Player player;
    private final EntityManager entityManager;

    private final DebugOnScreenDisplay debugOnScreenDisplay;

    private final OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    private final ParticleEffect particleEffect;

    public PlayScreen(JumpAndRunMain jumpAndRunMain) {
        this.jumpAndRunMain = jumpAndRunMain;

        world = new World(new Vector2(0f,-9.81f), true);

        entityManager = new EntityManager(world);

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Statics.VIRTUAL_WIDTH / PPM, Statics.VIRTUAL_HEIGHT / PPM, camera);
        camera.position.set(2.1f, 1.06f,0f);
        camera.update();

        myContactListener = new MyContactListener(this);
        world.setContactListener(myContactListener);

        player = new Player(this);
        MapCreator mapCreator = new MapCreator(world);

        debugOnScreenDisplay = new DebugOnScreenDisplay(jumpAndRunMain.spriteBatch);

        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(mapCreator.getMap(), 1f / PPM);

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("whitefly.p") ,Gdx.files.internal(""));
        particleEffect.getEmitters().first().setPosition(2f,1f);
        particleEffect.scaleEffect(0.005f);
        particleEffect.start();
    }

    @Override
    public void show() {

    }

    private void handleDebug(float dt) {
        debugOnScreenDisplay.update(dt);

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            entityManager.spawnDynamicEntity(new ToSpawnObjectDefinition<>(TestEntity.class, 80f,100f));
        }

        debugOnScreenDisplay.setFrameInfo("FRAME INFO |" +
                "   FPS : " + Gdx.graphics.getFramesPerSecond() +
                "   DELTA : " + Gdx.graphics.getDeltaTime());

        debugOnScreenDisplay.setEntityInfo("ENTITY MANAGER |" +
                "   QUEUE : " + entityManager.getQueuedSize() + "/" + entityManager.getMAX_QUEUED_ENTITIES() +
                "   SPAWNED : " + entityManager.getSpawnedSize() + "/" + entityManager.getMAX_ENTITIES_IN_WORLD());

        debugOnScreenDisplay.setPlayerInfo("PLAYER |" +
                "   POSITION : X " + player.getX() + "\n" +
                "                                       Y " + player.getY() + "\n" +
                "                                       VEL Y " + player.getPlayerBody().getLinearVelocity().y);
    }

    private void updateCamera(float dt) {
        //TODO: Camera vom Spieler lösen und Kamera Fahrt Smoother machen - Camera Y align
        if (player.getX() > 2.1f) {
            camera.position.x = player.getX();
        }
        camera.update();
    }


    private void updatePlayer(float dt) {
        player.update(dt);
    }

    private void update(float dt) {
        entityManager.update(dt);
        updateCamera(dt);
        updatePlayer(dt);
        handleDebug(dt);

        particleEffect.update(dt);
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //---------------------------------------------------------------------------------------------------------------- Phase 1 update Phase
        update(delta);

        //---------------------------------------------------------------------------------------------------------------- Phase 2 Physik
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        //---------------------------------------------------------------------------------------------------------------- Phase 3 Grafiken

        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();

        //begin
        jumpAndRunMain.spriteBatch.setProjectionMatrix(camera.combined);
        jumpAndRunMain.spriteBatch.begin();

        //render
        player.render();
        entityManager.render();
        particleEffect.draw(jumpAndRunMain.spriteBatch);
        if (particleEffect.isComplete()) {
            particleEffect.reset();
        }

        //end
        jumpAndRunMain.spriteBatch.end();

        //
        debugOnScreenDisplay.renderWithoutBatch(world, camera.combined);
        debugOnScreenDisplay.renderStage();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        world.dispose();
        player.dispose();
        entityManager.dispose();
    }

    public World getWorld() {
        return world;
    }
    public Player getPlayer() {
        return player;
    }
}