package de.dhbw.satp.screens;

import static de.dhbw.satp.main.Statics.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import de.dhbw.satp.gameobjects.Player;
import de.dhbw.satp.main.Statics;
import de.dhbw.satp.world.MapCreator;


public class PlayScreen implements Screen {

    World world;
    Box2DDebugRenderer debugRenderer;

    OrthographicCamera camera;
    Viewport viewport;

    RayHandler rayHandler;
    PointLight pointLight;

    private final Player player;

    public PlayScreen() {
        world = new World(new Vector2(0f,-9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();
        camera.position.set(1f / PPM,190f / PPM,0f);
        viewport = new ExtendViewport(2f * Statics.VIRTUAL_WIDTH / PPM, 2f * Statics.VIRTUAL_HEIGHT / PPM, camera);

        player = new Player(world);
        new MapCreator(world);

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(.8f);

        pointLight = new PointLight(rayHandler, 100, Color.BLACK, 32 / PPM, 4 * 20 / PPM,10 / PPM);
    }

    @Override
    public void show() {

    }

    private void updateCamera(float dt) {
        camera.position.x = player.getX();
    }

    private void update(float dt) {
        updateCamera(dt);
        camera.update();
        player.update(dt);
        pointLight.setPosition(player.getX(), 20 / PPM);
    }

    @Override
    public void render(float delta) {
        //---------------------------------------------------------------------------------------------------------------- Phase 1 update Phase
        update(delta);

        //---------------------------------------------------------------------------------------------------------------- Phase 2 Physik
        world.step(delta, 6, 2);
        rayHandler.setCombinedMatrix(camera.combined.cpy().scl(PPM));
        rayHandler.update();

        //---------------------------------------------------------------------------------------------------------------- Phase 3 Grafiken
        //OpenGL nutzen
        //OpenGL nutzt immer Floats (f)
        Gdx.gl20.glClearColor(0.5f, 0.8f, 1f, 1f); //Befehl um CLear Farbe zu setzen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);                     //Befehl an Grafikkarte um alles zu übermalen

        //Begin

        //render
        player.render();
        rayHandler.render();

        //end
        debugRenderer.render(world, camera.combined);
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
    }

    public World getWorld() {
        return world;
    }
}
