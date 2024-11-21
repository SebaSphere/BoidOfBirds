package dev.sebastianb.client.atlas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TextureAtlas {

    public static Sprite BIRD = getSprite(new Texture("assets/entity/fighter.png"));

    public static Sprite getSprite(Texture texture) {
        return new Sprite(texture);
    }

    // inits class for first time
    public static void register() {

    }
}