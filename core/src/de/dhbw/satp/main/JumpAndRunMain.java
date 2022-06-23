package de.dhbw.satp.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
		screenManager = new ScreenManager(this);

	}

	@Override
	public void render () {
		if(Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
			screenManager.nextScreen();
		}
		super.render();
	}


	@Override
	public void dispose () {
		super.dispose();
	}
}
