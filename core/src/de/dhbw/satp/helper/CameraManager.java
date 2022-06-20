package de.dhbw.satp.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

import de.dhbw.satp.gameobjects.Player;

public class CameraManager {

    private int camState = 0;
    private int waitCount = 75;

    private final OrthographicCamera camera;

    public CameraManager() {
        this.camera = new OrthographicCamera();

        camera.position.set(2.1f, 1.06f,0f);
        camera.zoom = 1f;
        camera.update();
    }

    public void update(Player player, float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            if (camera.zoom == 1f) {
                camera.zoom = 4f;
            } else {
                camera.zoom = 1f;
            }
        }

        if (player.getX() >= 30f) {
            waitCount--;
            if (waitCount <= 0) {
                if (camera.position.x < 32f) {
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
        if (player.getX() > 2.1f && player.getX() < 29.9f) {
            camera.position.x = player.getX();
        }
        camera.update();

    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}