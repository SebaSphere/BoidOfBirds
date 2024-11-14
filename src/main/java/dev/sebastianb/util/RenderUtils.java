package dev.sebastianb.util;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class RenderUtils {


    // TODO: actually make the window scale dynamically + icon textures
    public static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle(Constants.GAME_NAME);
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowedMode(800, 500); // this line changes the size of the window
        configuration.setWindowIcon("assets/icon.png");

        return configuration;
    }

}
