package de.dhbw.satp.screens;

import com.badlogic.gdx.Screen;

import java.util.HashMap;

import de.dhbw.satp.main.JumpAndRunMain;

public class ScreenManager {

    private final JumpAndRunMain main;
    private SCREEN currentScreen;

    public enum SCREEN {
        INIT_LOADING,
        MAIN_MENU,
        LEVEL_LOADING,
        PLAY,
        GAME_OVER
    }

    private final HashMap<SCREEN, Screen> screenMap;

    public ScreenManager(JumpAndRunMain main) {
        this.main = main;
        this.currentScreen = SCREEN.INIT_LOADING;
        this.screenMap = new HashMap<>();

        screenMap.put(SCREEN.INIT_LOADING, new InitLoadingScreen(main));
        main.setScreen(screenMap.get(SCREEN.INIT_LOADING));
    }

    public void nextScreen() {

        System.out.println("-- Switch Screen command, current: " + currentScreen.name());

        switch (currentScreen) {
            case INIT_LOADING:
            case GAME_OVER:
                switchScreens(SCREEN.MAIN_MENU, new MainMenuScreen(main));
                break;
            case MAIN_MENU:
                switchScreens(SCREEN.LEVEL_LOADING, new LevelLoadingScreen(main));
                break;
            case LEVEL_LOADING:
                switchScreens(SCREEN.PLAY, new PlayScreen(main));
                break;
            case PLAY:
                switchScreens(SCREEN.GAME_OVER, new GameOverScreen(main));
                break;
        }

        System.out.println("-- Switch Screen command, to: " + currentScreen.name());
    }

    private void switchScreens(SCREEN newScreen, Screen screen) {
        SCREEN oldScreen = getCurrentScreen();
        setCurrentScreen(newScreen);
        screenMap.put(newScreen, screen);
        main.setScreen(screen);
        screenMap.get(oldScreen).dispose();
    }

    public SCREEN getCurrentScreen() {
        return currentScreen;
    }
    public void setCurrentScreen(SCREEN currentScreen) {
        this.currentScreen = currentScreen;
    }
}
