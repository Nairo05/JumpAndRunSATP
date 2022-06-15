package de.dhbw.satp.screens;

import static de.dhbw.satp.main.Statics.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.dhbw.satp.gameobjects.EntityManager;
import de.dhbw.satp.parallax.ParallaxRenderer;
import de.dhbw.satp.particle.ParticleManager;
import de.dhbw.satp.gameobjects.Player;
import de.dhbw.satp.gameobjects.TestEntity;
import de.dhbw.satp.gameobjects.ToSpawnObjectDefinition;
import de.dhbw.satp.scene2d.DebugOnScreenDisplay;
import de.dhbw.satp.main.JumpAndRunMain;
import de.dhbw.satp.main.Statics;
import de.dhbw.satp.staticworld.MapCreator;
import de.dhbw.satp.staticworld.MyContactListener;


public class PlayScreen implements Screen {

    private final JumpAndRunMain jumpAndRunMain;
    private int camState = 0;

    private boolean toEnd = false;

    private final World world;
    public MyContactListener myContactListener;

    private final OrthographicCamera camera;
    private final Viewport viewport;

    private final Player player;
    private final EntityManager entityManager;
    private final ParticleManager particleManager;

    private final DebugOnScreenDisplay debugOnScreenDisplay;

    private final ParallaxRenderer parallaxRenderer;
    private final OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    public PlayScreen(JumpAndRunMain jumpAndRunMain) {
        this.jumpAndRunMain = jumpAndRunMain;

        world = new World(new Vector2(0f,-9.81f), true);

        entityManager = new EntityManager(world);
        particleManager = new ParticleManager();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Statics.VIRTUAL_WIDTH / PPM, Statics.VIRTUAL_HEIGHT / PPM, camera);
        camera.position.set(2.1f, 1.06f,0f);
        camera.zoom = 1f;
        camera.update();

        myContactListener = new MyContactListener(this);
        world.setContactListener(myContactListener);

        MapCreator mapCreator = new MapCreator(this);
        player = new Player(this, mapCreator.getPlayerRectangle().x, mapCreator.getPlayerRectangle().y);

        debugOnScreenDisplay = new DebugOnScreenDisplay(jumpAndRunMain.spriteBatch);

        parallaxRenderer = new ParallaxRenderer(true);
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(mapCreator.getMap(), 1f / PPM);
    }

    @Override
    public void show() {

    }

    private void handleDebug(float dt) {
        debugOnScreenDisplay.update(dt);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            if (camera.zoom == 1f) {
                camera.zoom = 4f;
            } else {
                camera.zoom = 1f;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            entityManager.spawnDynamicEntity(new ToSpawnObjectDefinition<>(TestEntity.class, 80f,100f));
        }

        debugOnScreenDisplay.setFrameInfo("FRAME INFO |" +
                "   FPS : " + Gdx.graphics.getFramesPerSecond() +
                "   DELTA : " + Gdx.graphics.getDeltaTime());

        debugOnScreenDisplay.setEntityInfo("ENTITY MANAGER |" +
                "   QUEUE : " + entityManager.getQueuedSize() + "/" + entityManager.getMAX_QUEUED_ENTITIES() +
                "   SPAWNED : " + entityManager.getSpawnedSize() + "/" + entityManager.getMAX_ENTITIES_IN_WORLD() +
                "   ACTIVE: ??");

        debugOnScreenDisplay.setPlayerInfo("PLAYER |" +
                "   POSITION : X " + player.getX() + "\n" +
                "                                       Y " + player.getY() + "\n" +
                "                                       VEL X " + player.getPlayerBody().getLinearVelocity().x + "\n" +
                "                                       VEL Y " + player.getPlayerBody().getLinearVelocity().y + "\n" +
                "                                       STATE " + player.getLives() + " IsInvincible: " + player.isInvincible());

        debugOnScreenDisplay.setParticelMangerInfo("PARTIC MANAGER |" +
                "   QUEUE: " + "0/0" +
                "   EMITTER : " + particleManager.getParticleSize() + "/" + particleManager.getMAX_PARTICLE_IN_WORLD() +
                "   ACTIVE: " + particleManager.getActive());
    }

    private void updateCamera(float dt) {
        //TODO: Camera vom Spieler lösen und Kamera Fahrt Smoother machen - Camera Y align
        if (player.getY() > 1.5f && myContactListener.isPlayerOnGround()) {
            camState = 1;
        } else if (player.getY() < 1.25 && myContactListener.isPlayerOnGround()) {
            camState = 2;
        }
        if (camState == 1) {
            if (camera.position.y < 2.1f) {
                camera.position.y += dt * 1.2f;
            }
        }
        if (camState == 2) {
            if (camera.position.y > 1.06f) {
                camera.position.y -= dt * 1.4;
            }
        }
        if (player.getX() > 2.1f && player.getX() < 29.9f) {
            camera.position.x = player.getX();
        }
        camera.update();
    }


    private void updatePlayer(float dt) {
        player.update(dt);
    }

    private void update(float dt) {
        entityManager.update(dt);
        particleManager.update(dt);

        updateCamera(dt);
        updatePlayer(dt);
        handleDebug(dt);

        parallaxRenderer.update(dt, camera);
    }

    @Override
    public void render(float delta) {
        if (toEnd) {
            jumpAndRunMain.screenManager.nextScreen();
            return;
        }

        Gdx.gl20.glClearColor(0.180f, 0.353f, 0.537f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //---------------------------------------------------------------------------------------------------------------- Phase 1 update Phase
        update(delta);

        //---------------------------------------------------------------------------------------------------------------- Phase 2 Physik
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        //---------------------------------------------------------------------------------------------------------------- Phase 3 Grafiken

        //begin (1/2)
        jumpAndRunMain.spriteBatch.setProjectionMatrix(camera.combined);
        jumpAndRunMain.spriteBatch.begin();

        //render | Batch Renderer
        parallaxRenderer.render(jumpAndRunMain.spriteBatch);

        //end (1/2)
        jumpAndRunMain.spriteBatch.end();

        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();

        //begin (2/2)
        jumpAndRunMain.spriteBatch.setProjectionMatrix(camera.combined);
        jumpAndRunMain.spriteBatch.begin();

        //render | Batch Renderer
        player.render(jumpAndRunMain.spriteBatch);
        entityManager.render(jumpAndRunMain.spriteBatch);
        particleManager.render(jumpAndRunMain.spriteBatch);

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
        parallaxRenderer.dispose();
        entityManager.dispose();
    }

    public World getWorld() {
        return world;
    }
    public Player getPlayer() {
        return player;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
    public ParticleManager getParticleManager() {
        return particleManager;
    }

    public void endGame() {
        toEnd = true;
    }
}