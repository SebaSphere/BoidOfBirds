package dev.sebastianb;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import dev.sebastianb.client.GameClient;
import dev.sebastianb.util.RenderUtils;

public class Main {

    public static void main(String[] args) {

        var config = RenderUtils.getDefaultConfiguration();
        new Lwjgl3Application(new GameClient(), config);

    }

}
