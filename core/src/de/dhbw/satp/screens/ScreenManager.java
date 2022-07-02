package de.dhbw.satp.screens;

import com.badlogic.gdx.Screen;

import java.util.HashMap;

import de.dhbw.satp.main.JumpAndRunMain;

public class ScreenManager {
    //TODO: Use abstract class, avoid boilerplate code

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
                setCurrentScreen(SCREEN.MAIN_MENU);

                MainMenuScreen mainMenuScreen = new MainMenuScreen(main);
                screenMap.put(SCREEN.MAIN_MENU, mainMenuScreen);
                main.setScreen(mainMenuScreen);

                screenMap.get(SCREEN.INIT_LOADING).dispose();
                break;
            case MAIN_MENU:
                setCurrentScreen(SCREEN.LEVEL_LOADING);

                LevelLoadingScreen levelLoadingScreen = new LevelLoadingScreen(main);
                screenMap.put(SCREEN.LEVEL_LOADING, levelLoadingScreen);
                main.setScreen(levelLoadingScreen);

                screenMap.get(SCREEN.MAIN_MENU).dispose();
                break;
            case LEVEL_LOADING:
                setCurrentScreen(SCREEN.PLAY);

                PlayScreen playScreen = new PlayScreen(main);
                screenMap.put(SCREEN.PLAY, playScreen);
                main.setScreen(playScreen);

                screenMap.get(SCREEN.LEVEL_LOADING).dispose();
                break;
            case PLAY:
                setCurrentScreen(SCREEN.GAME_OVER);

                GameOverScreen gameOverScreen = new GameOverScreen();
                screenMap.put(SCREEN.GAME_OVER, gameOverScreen);
                main.setScreen(gameOverScreen);

                screenMap.get(SCREEN.PLAY).dispose();
                break;
            case GAME_OVER:
                setCurrentScreen(SCREEN.MAIN_MENU);

                MainMenuScreen mainMenuScreen2 = new MainMenuScreen(main);
                screenMap.put(SCREEN.MAIN_MENU, mainMenuScreen2);
                main.setScreen(mainMenuScreen2);

                screenMap.get(SCREEN.GAME_OVER).dispose();
                break;
        }

        System.out.println("-- Switch Screen command, to: " + currentScreen.name());
    }

    public SCREEN getCurrentScreen() {
        return currentScreen;
    }
    public void setCurrentScreen(SCREEN currentScreen) {
        this.currentScreen = currentScreen;
    }
}
