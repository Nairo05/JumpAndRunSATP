package de.dhbw.satp.main;

import com.badlogic.gdx.Game;

import de.dhbw.satp.screens.PlayScreen;

public class JumpAndRunMain extends Game {
	
	@Override
	public void create () {
		PlayScreen playScreen = new PlayScreen();

		setScreen(playScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	//egal
	@Override
	public void dispose () {
		super.dispose();
	}
}
