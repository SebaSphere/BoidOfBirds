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
        int[] monitorSize = {Lwjgl3ApplicationConfiguration.getDisplayMode().width, Lwjgl3ApplicationConfiguration.getDisplayMode().height};
        configuration.setWindowSizeLimits(monitorSize[0] / 3, monitorSize[1] / 3, monitorSize[0], monitorSize[1]);

        return configuration;
    }

}
