package de.dhbw.satp.gameobjects.collectible;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

import de.dhbw.satp.gameobjects.DynamicEntity;
import de.dhbw.satp.gameobjects.ToSpawnObjectDefinition;
import de.dhbw.satp.screens.PlayScreen;

public class CollectibleManager {

    private final PlayScreen playScreen;
    private int activeCount = 0;
    private final int MAX_IN_WORLD = 64;
    private final int MAX_QUEUED_ENTITIES = 128;
    private int intItCount = 0;

    private Array<DynamicCoin> inWorldObjects;
    private final Queue<ToSpawnObjectDefinition<? extends DynamicEntity>> queuedEntities;

    public CollectibleManager(PlayScreen playScreen) {
        this.playScreen = playScreen;
        inWorldObjects = new Array<>(MAX_IN_WORLD);
        queuedEntities = new Queue<>(MAX_QUEUED_ENTITIES);
    }

    public void update(float dt, Camera camera) {

        if (!queuedEntities.isEmpty()) {
            if (inWorldObjects.size < MAX_IN_WORLD) {
                ToSpawnObjectDefinition<? extends DynamicEntity> currentSpawnDef = queuedEntities.first();
                queuedEntities.removeFirst();

                DynamicCoin dynamicCoin = new DynamicCoin(playScreen, currentSpawnDef.getPosXInWorldUnits(), currentSpawnDef.getPosXInWorldUnits());
                dynamicCoin.freeze();

                inWorldObjects.add(dynamicCoin);
            }
        }

        float camCordsLeft = camera.position.x - 2.5f;
        float camCordsRight = camera.position.x + 2.5f;

        for (int i = 0; i < inWorldObjects.size ; i++) {

            DynamicCoin dynamicCoin = inWorldObjects.get(i);

            if (dynamicCoin.isDestroyed() && !dynamicCoin.isInAnimation()) {
                inWorldObjects.removeIndex(i);
                activeCount--;
                return;
            }

            if (dynamicCoin.isFreeze()) {
                if (dynamicCoin.getPositionInWorld() > camCordsLeft && dynamicCoin.getPositionInWorld() < camCordsRight) {
                    dynamicCoin.unFreeze();
                    activeCount++;
                }
            }
            if (!dynamicCoin.isFreeze()) {
                if (dynamicCoin.getPositionInWorld() < camCordsLeft || dynamicCoin.getPositionInWorld() > camCordsRight) {
                    dynamicCoin.freeze();
                    activeCount--;
                }
            }

            dynamicCoin.update(dt);

        }
    }

    public void render(SpriteBatch spriteBatch) {
        for (DynamicCoin dynamicCoin : inWorldObjects) {
            if (!dynamicCoin.isFreeze() || dynamicCoin.isInAnimation()) {
                dynamicCoin.render(spriteBatch);
            }
        }
    }

    public DynamicCoin createCoinAndRegister(float x, float y) {
        DynamicCoin dynamicCoin = new DynamicCoin(playScreen, x,y);

        inWorldObjects.add(dynamicCoin);
        intItCount++;

        return dynamicCoin;
    }

    public void aSyncSpawn(Class<? extends DynamicEntity> className, float x, float y) {
        //TODO remove width
        queuedEntities.addLast(new ToSpawnObjectDefinition<>(className, x, y, 2f));
    }

    public int getActiveCount() {
        return activeCount;
    }

    public int getCount() {
        return inWorldObjects.size;
    }

    public int getSize() {
        return MAX_IN_WORLD;
    }

    public int getQueued() {
        return queuedEntities.size;
    }

    public int getQueuedMaxSize() {
        return MAX_QUEUED_ENTITIES;
    }

    public int getIntItCount() {
        return intItCount;
    }
}
