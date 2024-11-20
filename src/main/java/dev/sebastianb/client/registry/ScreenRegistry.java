package dev.sebastianb.client.registry;

import dev.sebastianb.client.GameClient;
import dev.sebastianb.client.screen.GameScreen;
import dev.sebastianb.client.screen.MainMenuScreen;
import dev.sebastianb.client.screen.WorldRendererScreen;

public enum ScreenRegistry {
    MENU,
    WORLD_SCREEN;

    private GameScreen gameScreen;

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        if (this.gameScreen == null) {
            this.gameScreen = gameScreen;
        } else {
            throw new RuntimeException("Screen already registered");
        }
    }

    public static void register(GameClient gameClient) {
        for (ScreenRegistry screenRegistry : values()) {
            switch (screenRegistry) {
                case MENU -> screenRegistry.setGameScreen(new MainMenuScreen(gameClient));
                case WORLD_SCREEN -> screenRegistry.setGameScreen(new WorldRendererScreen(gameClient));
            }
        }
    }

}
