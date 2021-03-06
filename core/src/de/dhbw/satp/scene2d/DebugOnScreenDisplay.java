package de.dhbw.satp.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.dhbw.satp.main.NotFinalStatics;

public class DebugOnScreenDisplay {

    public Stage stage;

    private final Box2DDebugRenderer debugRenderer;

    private final StageText2D debuggerInfo;
    private final StageText2D particleManagerInfo;
    private final StageText2D entityManagerInfo;
    private final StageText2D spawnAble;
    private final StageText2D frameInfo;
    private final StageText2D playerInfo;

    public DebugOnScreenDisplay(SpriteBatch spriteBatch) {

        debuggerInfo = new StageText2D(10f, 620);
        debuggerInfo.setText("Debugger enabled, Press F3 to close, F4 to skip Screen, F5 to zoom in and out");

        frameInfo = new StageText2D(10f, 580f);
        particleManagerInfo = new StageText2D(10f, 540f);
        entityManagerInfo = new StageText2D(10f, 520f);
        spawnAble = new StageText2D(10f, 500f);
        playerInfo = new StageText2D(10f, 460f);

        Viewport viewport = new ExtendViewport(NotFinalStatics.WINDOW_WIDTH, NotFinalStatics.WINDOW_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        stage.addActor(debuggerInfo);
        stage.addActor(frameInfo);
        stage.addActor(playerInfo);
        stage.addActor(entityManagerInfo);
        stage.addActor(particleManagerInfo);
        stage.addActor(spawnAble);

        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.setDrawAABBs(true);
        debugRenderer.setDrawVelocities(true);
        debugRenderer.setDrawContacts(true);
        debugRenderer.setDrawBodies(true);
        debugRenderer.setDrawJoints(true);
    }


    public void update(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            NotFinalStatics.debug = !NotFinalStatics.debug;
        }
        if (Gdx.input.isTouched() && Gdx.input.getY() < 100) {
            NotFinalStatics.debug = !NotFinalStatics.debug;
        }

        stage.act(dt);
    }

    public void renderStage() {
        stage.draw();
    }

    public void renderWithoutBatch(World world, Matrix4 matrix4) {
        debugRenderer.render(world, matrix4);
    }

    public void setEntityInfo(String entityInfo) {
        this.entityManagerInfo.setText(entityInfo);
    }
    public void setFrameInfo(String frameInfo) {
        this.frameInfo.setText(frameInfo);
    }
    public void setPlayerInfo(String playerInfo) {
        this.playerInfo.setText(playerInfo);
    }
    public void setSpawnAbleInfo(String text) {
        this.spawnAble.setText(text);
    }
    public void setParticleManagerInfo(String particleInfo) {
        this.particleManagerInfo.setText(particleInfo);
    }
}
