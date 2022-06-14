package de.dhbw.satp.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.dhbw.satp.main.Statics;

public class DebugOnScreenDisplay {

    private boolean debug;
    private final Box2DDebugRenderer debugRenderer;

    private final StageText2D particelMangerInfo;
    private final StageText2D entityManagerInfo;
    private final StageText2D frameInfo;
    private final StageText2D playerInfo;

    public Stage stage;


    public DebugOnScreenDisplay(SpriteBatch spriteBatch) {
        this.debug = false;

        frameInfo = new StageText2D(10f, 520f);
        particelMangerInfo = new StageText2D(10f, 480f);
        entityManagerInfo = new StageText2D(10f, 460f);
        playerInfo = new StageText2D(10f, 420f);

        Viewport viewport = new ExtendViewport(Statics.WINDOW_WIDTH, Statics.WINDOW_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        stage.addActor(frameInfo);
        stage.addActor(playerInfo);
        stage.addActor(entityManagerInfo);
        stage.addActor(particelMangerInfo);

        debugRenderer = new Box2DDebugRenderer();
    }


    public void update(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            debug = !debug;
        }
        if (Gdx.input.isTouched() && Gdx.input.getY() < 100) {
            debug = !debug;
        }

        if (debug) {
            stage.act(dt);
        }
    }

    public void renderStage() {
        if (debug) {
            stage.draw();
        }
    }

    public void renderWithoutBatch(World world, Matrix4 matrix4) {
        if (debug) {
            debugRenderer.render(world, matrix4);
        }
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
    public void setParticelMangerInfo(String particelInfo) {
        this.particelMangerInfo.setText(particelInfo);
    }
}
