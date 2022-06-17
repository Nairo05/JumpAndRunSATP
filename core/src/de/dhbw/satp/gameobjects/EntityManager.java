package de.dhbw.satp.gameobjects;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Queue;

public class EntityManager implements Disposable {

    //TODO: Gegner mit Stacheln, auf den man nicht springen darf   ...für Herr Uhl ;)

    private final int MAX_ENTITIES_IN_WORLD = 16;
    private final int MAX_QUEUED_ENTITIES = 32;

    private final World world;

    private final Array<DynamicEntity> dynamicEntityArrayList;
    private final Queue<ToSpawnObjectDefinition<? extends DynamicEntity>> queuedEntities;

    public EntityManager(World world) {
        queuedEntities = new Queue<>(MAX_QUEUED_ENTITIES);
        dynamicEntityArrayList = new Array<>();
        this.world = world;
    }

    public void spawnDynamicEntity(ToSpawnObjectDefinition<? extends DynamicEntity> toSpawnObjectDefinition) {
        if (queuedEntities.size < MAX_QUEUED_ENTITIES) {
            queuedEntities.addLast(toSpawnObjectDefinition);
        }
    }

    private void updateDynamicEntities(float dt) {
        if (!queuedEntities.isEmpty()) {
            if (dynamicEntityArrayList.size < MAX_ENTITIES_IN_WORLD) {

                ToSpawnObjectDefinition<? extends DynamicEntity> currentSpawnDef = queuedEntities.first();
                queuedEntities.removeFirst();

                if (currentSpawnDef.getBlueprint() == TestEntity.class) {
                    dynamicEntityArrayList.add(new TestEntity(world, currentSpawnDef.getPosXInWorldUnits(), currentSpawnDef.getPosYInWorldUnits()));
                }
            }
        }
        for (int i = 0; i < dynamicEntityArrayList.size; i++) {
            dynamicEntityArrayList.get(i).update(dt);
            if (dynamicEntityArrayList.get(i).isDestroyed()) {
                dynamicEntityArrayList.removeIndex(i);
            }
        }
    }

    public void update(float dt) {
        updateDynamicEntities(dt);
    }

    public void render(SpriteBatch spriteBatch) {
        for(DynamicEntity entity : dynamicEntityArrayList) {
            //TODO: Prüfen, ob eingefroren
            entity.render(spriteBatch);
        }
    }

    public int getMAX_ENTITIES_IN_WORLD() {
        return MAX_ENTITIES_IN_WORLD;
    }

    public int getMAX_QUEUED_ENTITIES() {
        return MAX_QUEUED_ENTITIES;
    }

    public int getSpawnedSize() {
        return this.dynamicEntityArrayList.size;
    }

    public int getQueuedSize() {
        return this.queuedEntities.size;
    }

    @Override
    public void dispose() {
        for(DynamicEntity dynamicEntity : dynamicEntityArrayList) {
            dynamicEntity.dispose();
        }
    }
}
