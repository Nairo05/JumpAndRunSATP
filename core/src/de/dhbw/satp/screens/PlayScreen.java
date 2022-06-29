package de.dhbw.satp.screens;

import static de.dhbw.satp.main.FinalStatics.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.dhbw.satp.gameobjects.Enemy;
import de.dhbw.satp.gameobjects.EntityManager;
import de.dhbw.satp.gameobjects.Player;
import de.dhbw.satp.gameobjects.ToSpawnObjectDefinition;
import de.dhbw.satp.helper.CameraManager;
import de.dhbw.satp.main.FinalStatics;
import de.dhbw.satp.main.JumpAndRunMain;
import de.dhbw.satp.parallax.ParallaxConfiguration;
import de.dhbw.satp.parallax.ParallaxRenderer;
import de.dhbw.satp.particle.ParticleManager;
import de.dhbw.satp.scene2d.DebugOnScreenDisplay;
import de.dhbw.satp.scene2d.Hud;
import de.dhbw.satp.staticworld.MapCreator;
import de.dhbw.satp.staticworld.MyContactListener;


public class PlayScreen implements Screen {

    private final JumpAndRunMain jumpAndRunMain;

    private boolean toEnd = false;

    private final Player player;
    private final World world;
    public MyContactListener myContactListener;

    private final Viewport viewport;
    private final CameraManager cameraManager;

    private final EntityManager entityManager;
    private final ParticleManager particleManager;

    private final DebugOnScreenDisplay debugOnScreenDisplay;
    private final Hud hud;

    private final ParallaxRenderer parallaxRenderer;
    private final OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    public PlayScreen(JumpAndRunMain jumpAndRunMain) {
        this.jumpAndRunMain = jumpAndRunMain;

        world = new World(new Vector2(0f,-9.81f), true);

        entityManager = new EntityManager(this);
        particleManager = new ParticleManager();

        cameraManager = new CameraManager();
        viewport = new ExtendViewport(FinalStatics.VIRTUAL_WIDTH / PPM, FinalStatics.VIRTUAL_HEIGHT / PPM, cameraManager.getCamera());

        myContactListener = new MyContactListener(this);
        world.setContactListener(myContactListener);

        MapCreator mapCreator = new MapCreator(this);
        player = new Player(this, mapCreator.getPlayerRectangle().x, mapCreator.getPlayerRectangle().y);

        debugOnScreenDisplay = new DebugOnScreenDisplay(jumpAndRunMain.spriteBatch);
        hud = new Hud(this, jumpAndRunMain.spriteBatch);

        ParallaxConfiguration parallaxConfiguration = new ParallaxConfiguration();
        parallaxConfiguration.load("tmx/1-1.parallax");
        parallaxRenderer = new ParallaxRenderer(parallaxConfiguration);

        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(mapCreator.getMap(), 1f / PPM);
    }

    @Override
    public void show() {

    }

    private void handleDebug(float dt) {
        debugOnScreenDisplay.update(dt);

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            entityManager.spawnDynamicEntity(new ToSpawnObjectDefinition<>(Enemy.class, 80f, 70f, 32f));
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
                "                   Velocity        VEL X " + player.getPlayerBody().getLinearVelocity().x + "\n" +
                "                                       VEL Y " + player.getPlayerBody().getLinearVelocity().y + "\n" +
                "                   Lives            STATE " + player.getLives() + " IsInvincible: " + player.isInvincible());

        debugOnScreenDisplay.setParticleManagerInfo("PARTIC MANAGER |" +
                "   QUEUE: " + "0/0" +
                "   EMITTER : " + particleManager.getParticleSize() + "/" + particleManager.getMAX_PARTICLE_IN_WORLD() +
                "   ACTIVE: " + particleManager.getActive());
    }



    private void updatePlayer(float dt) {
        player.update(dt);
    }

    private void update(float dt) {
        entityManager.update(dt);
        particleManager.update(dt);

        cameraManager.update(player, dt);
        updatePlayer(dt);
        handleDebug(dt);
        hud.update(dt);

        parallaxRenderer.update(dt, cameraManager.getCamera());
    }

    @Override
    public void render(float delta) {
        if (toEnd) {
            jumpAndRunMain.screenManager.nextScreen();
            return;
        }

        //---------------------------------------------------------------------------------------------------------------- Phase 1 update
        update(delta);

        //---------------------------------------------------------------------------------------------------------------- Phase 2 physics
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        //---------------------------------------------------------------------------------------------------------------- Phase 3 graphics

        //Clear Screen
        Gdx.gl20.glClearColor(0.180f, 0.353f, 0.537f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //begin (1/2)
        jumpAndRunMain.spriteBatch.setProjectionMatrix(cameraManager.getCamera().combined);
        jumpAndRunMain.spriteBatch.begin();

        //render | Batch Renderer
        parallaxRenderer.render(jumpAndRunMain.spriteBatch);

        //end (1/2)
        jumpAndRunMain.spriteBatch.end();

        //cant render while Batch-Rendering
        orthogonalTiledMapRenderer.setView(cameraManager.getCamera());
        orthogonalTiledMapRenderer.render();

        //begin (2/2)
        jumpAndRunMain.spriteBatch.setProjectionMatrix(cameraManager.getCamera().combined);
        jumpAndRunMain.spriteBatch.begin();

        //render | Batch Renderer
        player.render(jumpAndRunMain.spriteBatch);
        entityManager.render(jumpAndRunMain.spriteBatch);
        particleManager.render(jumpAndRunMain.spriteBatch, cameraManager.getCamera());

        //end (2/2)
        jumpAndRunMain.spriteBatch.end();

        //Hud
        debugOnScreenDisplay.renderWithoutBatch(world, cameraManager.getCamera().combined);
        debugOnScreenDisplay.renderStage();

        hud.renderStage();
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
        hud.dispose();
        particleManager.dispose();
        parallaxRenderer.dispose();
        entityManager.dispose();
    }

    public World getWorld() {
        return world;
    }
    public Player getPlayer() {
        return player;
    }

    public ParticleManager getParticleManager() {
        return particleManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void endGame() {
        toEnd = true;
    }

    public AssetManager getAssetManager() {
        return jumpAndRunMain.assetManager;
    }
}