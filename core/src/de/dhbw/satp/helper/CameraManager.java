package de.dhbw.satp.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

import de.dhbw.satp.gameobjects.Player;
import de.dhbw.satp.main.NotFinalStatics;

public class CameraManager {

    private static final float LEVEL_START = 2.1f;
    private static final float LEVEL_END = 29.9f;
    private static final float FINAL_CAM_POSITION = LEVEL_END + 2f;

    private boolean toReset = false;

    private final OrthographicCamera camera;

    private int camState = 0;
    private int waitCount = 75;

    public CameraManager() {
        this.camera = new OrthographicCamera();

        camera.position.set(2.1f, 1.06f,0f);
        camera.zoom = 1f;
        camera.update();
    }

    public void update(Player player, float dt) {
        if (NotFinalStatics.debug) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
                if (camera.zoom == 1f) {
                    camera.zoom = 4f;
                } else {
                    camera.zoom = 1f;
                }
            }
        }

        if (player.isInvincible() && !toReset) {
            toReset = true;
        }

        if (toReset) {
            if (camera.position.x > player.getX() && camera.position.x > LEVEL_START) {
                camera.position.x -= dt * 3;
            } else {
                toReset = false;
            }
        }

        if (player.getX() > LEVEL_END) {
            waitCount--;
            if (waitCount <= 0) {
                if (camera.position.x < FINAL_CAM_POSITION) {
                    camera.position.x += dt / 2;
                }
            }
        }

        if (player.getY() > 1.5f && player.isPlayerIsOnGround()) {
            camState = 1;
        } else if (player.getY() < 1.25 && player.isPlayerIsOnGround()) {
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

        if (player.getX() > LEVEL_START && player.getX() < LEVEL_END) {
            camera.position.x = player.getX();
        }

        camera.update();

    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public boolean isOutOfMap() {
        return camera.position.x >= FINAL_CAM_POSITION;
    }
}