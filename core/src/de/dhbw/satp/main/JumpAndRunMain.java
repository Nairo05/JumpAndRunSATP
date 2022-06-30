package de.dhbw.satp.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.dhbw.satp.main.assetfragments.ParallaxAsset;
import de.dhbw.satp.main.assetfragments.ParallaxFragmentAssetLoader;
import de.dhbw.satp.main.assetfragments.ShaderAsset;
import de.dhbw.satp.main.assetfragments.ShaderFragmentAssetLoader;
import de.dhbw.satp.screens.PlayScreen;
import de.dhbw.satp.screens.ScreenManager;

public class JumpAndRunMain extends Game {

	//TODO: Asset Manager

	public SpriteBatch spriteBatch;
	public AssetManager assetManager;
	public ScreenManager screenManager;

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		assetManager = new AssetManager();
		assetManager.setLoader(ParallaxAsset.class, new ParallaxFragmentAssetLoader(new InternalFileHandleResolver()));
		assetManager.setLoader(ShaderAsset.class, new ShaderFragmentAssetLoader(new InternalFileHandleResolver()));
		screenManager = new ScreenManager(this);
	}

	@Override
	public void render () {
		if (Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
			screenManager.nextScreen();
		}
		super.render();
	}


	@Override
	public void dispose () {
		super.dispose();
	}
}
