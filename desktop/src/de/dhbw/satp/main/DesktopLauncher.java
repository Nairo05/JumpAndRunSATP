package de.dhbw.satp.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import de.dhbw.satp.main.JumpAndRunMain;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	//TODO: Add JFrame Launcher
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Test JumpAndRunSATP"); //TODO: Invent nice name ;)
		config.setForegroundFPS(Statics.FOREGROUND_FPS);
		config.setWindowedMode(Statics.WINDOW_WIDTH, Statics.WINDOW_HEIGHT);
		new Lwjgl3Application(new JumpAndRunMain(), config);
	}
}
