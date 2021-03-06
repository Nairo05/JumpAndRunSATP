package de.dhbw.satp.staticworld;

public class BitFilterDef {

    //Default Bit
    public static final short NO_CLIP_BIT = 0;

    //Identity Bits - Category and Filter Mask Bits
    public static final short PLAYER_BIT = 1;
    public static final short PLAYER_REVERSE_VEL_BIT = 2;
    public static final short GROUND_BIT = 4;
    public static final short ENEMY_HEAD_BIT = 8;
    public static final short ENEMY_BODY_BIT = 16;
    public static final short ONE_WAY_GROUND = 32;
    public static final short COLLECTIBLE_BIT = 64;

    //Colliding Bits
    public static final short PLAYER_REVERSE_VEL = PLAYER_REVERSE_VEL_BIT + GROUND_BIT;
    public static final short PLAYER_ON_GROUND = PLAYER_BIT + GROUND_BIT;
    public static final short ENEMY_CO_ENEMY = ENEMY_BODY_BIT + ENEMY_BODY_BIT;
    public static final short PLAYER_CO_ENEMY = PLAYER_BIT + ENEMY_BODY_BIT;
    public static final short PLAYER_CO_ENEMY_HEAD = PLAYER_BIT + ENEMY_HEAD_BIT;
    public static final short PLAYER_COLLECT_COIN = PLAYER_BIT + COLLECTIBLE_BIT;
}