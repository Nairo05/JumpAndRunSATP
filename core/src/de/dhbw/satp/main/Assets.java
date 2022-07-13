package de.dhbw.satp.main;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

import de.dhbw.satp.main.assetfragments.ParallaxAsset;
import de.dhbw.satp.main.assetfragments.ShaderAsset;

//The class contains the access string and type of each asset in the game
public class Assets {

    //Textures
    public static final AssetDescriptor<Texture> playerTexture = new AssetDescriptor<>("playersprite/skull1.png", Texture.class);
    public static final AssetDescriptor<Texture> defaultBackgroundPart0 = new AssetDescriptor<>("tmx/backgrounds/background_0.png", Texture.class);
    public static final AssetDescriptor<Texture> defaultBackgroundPart1 = new AssetDescriptor<>("tmx/backgrounds/background_1.png", Texture.class);
    public static final AssetDescriptor<Texture> defaultBackgroundPart2 = new AssetDescriptor<>("tmx/backgrounds/background_2.png", Texture.class);
    public static final AssetDescriptor<Texture> enemyDefaultSprite = new AssetDescriptor<>("sprite/enemy/Bloated Bedbug/BloatedBedbugIdleSide.png", Texture.class);
    public static final AssetDescriptor<Texture> enemySpikeSprite = new AssetDescriptor<>("sprite/enemy/Lethal Scorpion/LethalScorpionIdleSide.png", Texture.class);
    public static final AssetDescriptor<Texture> digitSprite = new AssetDescriptor<>("sprite/digits.png", Texture.class);
    public static final AssetDescriptor<Texture> heartSprite = new AssetDescriptor<>("sprite/heart.png", Texture.class);
    public static final AssetDescriptor<Texture> coinSprite = new AssetDescriptor<>("sprite/spr_coin_strip4.png", Texture.class);
    public static final AssetDescriptor<Texture> menuSymbols = new AssetDescriptor<>("menu/symbols/TEXT_MENU_1.png", Texture.class);
    public static final AssetDescriptor<Texture> menuNumbers = new AssetDescriptor<>("menu/menunumbers.png", Texture.class);
    public static final AssetDescriptor<Texture> menuButton = new AssetDescriptor<>("menu/ui/BTN_GREEN_SQ.png", Texture.class);
    public static final AssetDescriptor<Texture> menuClearText = new AssetDescriptor<>("menu/ui/levelclear.png", Texture.class);
    public static final AssetDescriptor<Texture> menuSkipText = new AssetDescriptor<>("menu/ui/clicktoskip.png", Texture.class);
    public static final AssetDescriptor<Texture> menuBackground = new AssetDescriptor<>("menu/ui/background.png", Texture.class);
    public static final AssetDescriptor<Texture> menuSelectLevel = new AssetDescriptor<>("menu/ui/selectlevel.png", Texture.class);

    //Shaders high
    public static final AssetDescriptor<ShaderAsset> earthQuakeShader = new AssetDescriptor<>("shaders/high/earthquake", ShaderAsset.class);
    public static final AssetDescriptor<ShaderAsset> colorShiftShader = new AssetDescriptor<>("shaders/high/colorshift", ShaderAsset.class);
    public static final AssetDescriptor<ShaderAsset> passThroughShader = new AssetDescriptor<>("shaders/high/passthrough", ShaderAsset.class);

    //Shaders low
    public static final AssetDescriptor<ShaderAsset> earthQuakeShaderLow = new AssetDescriptor<>("shaders/low/earthquake", ShaderAsset.class);
    public static final AssetDescriptor<ShaderAsset> colorShiftShaderLow = new AssetDescriptor<>("shaders/low/colorshift", ShaderAsset.class);
    public static final AssetDescriptor<ShaderAsset> passThroughShaderLow = new AssetDescriptor<>("shaders/low/passthrough", ShaderAsset.class);

    //Parallax Configurations
    public static final AssetDescriptor<ParallaxAsset> defaultBackgroundConfiguration = new AssetDescriptor<>("tmx/1-1.parallax", ParallaxAsset.class);

    //Level
    public static final AssetDescriptor<TiledMap> level11 = new AssetDescriptor<>("tmx/1-1.tmx", TiledMap.class);
}
