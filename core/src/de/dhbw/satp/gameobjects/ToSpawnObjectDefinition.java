package de.dhbw.satp.gameobjects;

public class ToSpawnObjectDefinition<T extends DynamicEntity> {

    private final Class<T> blueprint;

    private final float posXInWorldUnits;
    private final float posYInWorldUnits;

    public ToSpawnObjectDefinition(Class<T> blueprint, float posXInWorldUnits, float posYInWorldUnits) {
        this.blueprint = blueprint;

        this.posXInWorldUnits = posXInWorldUnits;
        this.posYInWorldUnits = posYInWorldUnits;
    }

    public float getPosXInWorldUnits() {
        return posXInWorldUnits;
    }

    public float getPosYInWorldUnits() {
        return posYInWorldUnits;
    }

    public Class<T> getBlueprint() {
        return blueprint;
    }
}
