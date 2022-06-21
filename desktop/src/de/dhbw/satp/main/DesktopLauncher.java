package de.dhbw.satp.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	//TODO: Add JFrame Launcher
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Test JumpAndRunSATP"); //TODO: Invent nice name ;)
		config.setForegroundFPS(FinalStatics.FOREGROUND_FPS);
		config.setWindowedMode(FinalStatics.WINDOW_WIDTH, FinalStatics.WINDOW_HEIGHT);
		new Lwjgl3Application(new JumpAndRunMain(), config);
	}
}
