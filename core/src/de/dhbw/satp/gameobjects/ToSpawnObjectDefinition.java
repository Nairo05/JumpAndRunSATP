package de.dhbw.satp.gameobjects;

public class ToSpawnObjectDefinition<T extends DynamicEntity> {

    private final Class<T> blueprint;

    private final float posXInWorldUnits;
    private final float posYInWorldUnits;
    private final float width;

    public ToSpawnObjectDefinition(Class<T> blueprint, float posXInWorldUnits, float posYInWorldUnits, float width) {
        this.blueprint = blueprint;

        this.posXInWorldUnits = posXInWorldUnits;
        this.posYInWorldUnits = posYInWorldUnits;
        this.width = width;
    }

    public float getPosXInWorldUnits() {
        return posXInWorldUnits;
    }

    public float getPosYInWorldUnits() {
        return posYInWorldUnits;
    }

    public float getWidth() {
        return width;
    }

    public Class<T> getBlueprint() {
        return blueprint;
    }
}
