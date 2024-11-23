package dev.sebastianb.boids.client.registry;

import dev.sebastianb.boids.client.GameClient;
import dev.sebastianb.boids.client.screen.GameScreen;
import dev.sebastianb.boids.client.screen.MainMenuScreen;
import dev.sebastianb.boids.client.screen.WorldRendererScreen;
import dev.sebastianb.boids.world.WorldLevelStage;

public enum ScreenRegistry {
    MENU,
    WORLD_SCREEN;

    private GameScreen gameScreen;

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public static void register(GameClient gameClient, WorldLevelStage worldLevelStage) {
        for (ScreenRegistry screenRegistry : values()) {
            switch (screenRegistry) {
                case MENU -> screenRegistry.setGameScreen(new MainMenuScreen(gameClient));
                case WORLD_SCREEN -> screenRegistry.setGameScreen(new WorldRendererScreen(gameClient, worldLevelStage));
            }
        }
    }

}
