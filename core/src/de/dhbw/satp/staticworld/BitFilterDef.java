package de.dhbw.satp.staticworld;

public class BitFilterDef {

    //TODO: enumeration ?
    //Default Bit
    public static final short NO_CLIP_BIT = 0;

    //Identity Bits - Category and Filter Mask Bits
    public static final short PLAYER_BIT = 1;
    public static final short GROUND_BIT = 2;
    public static final short ENEMY_BIT = 4;
    public static final short OBJECT_BIT = 8;

    //Colliding Bits
    public static final short PLAYER_ON_GROUND = PLAYER_BIT + GROUND_BIT;
    public static final short ENTITY_CO_ENTITY = ENEMY_BIT + ENEMY_BIT;
    public static final short PLAYER_CO_ENEMY = PLAYER_BIT + ENEMY_BIT;
}