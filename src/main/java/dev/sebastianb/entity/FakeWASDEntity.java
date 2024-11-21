package dev.sebastianb.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.sebastianb.client.atlas.TextureAtlas;
import dev.sebastianb.world.WorldLevelStage;

public class FakeWASDEntity extends Entity {
    private static final float VELOCITY_Y = 2f; // Speed at which the entity moves down

    public FakeWASDEntity(WorldLevelStage worldLevelStage, int x, int y) {
        super(worldLevelStage, x, y);
    }

    @Override
    public void tick(int time) {
        // Move down
        y -= VELOCITY_Y;

        // Check if it hits the bottom of the screen
        if (y + getSprite().getHeight() < 0) {
            // Remove from the stage
            worldLevelStage.removeEntity(this);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        super.render(spriteBatch);
        TextureAtlas.FAKE_WASD.setPosition(this.x, this.y);
        TextureAtlas.FAKE_WASD.draw(spriteBatch);
        TextureAtlas.FAKE_WASD.setScale(0.4f);
    }

    @Override
    public Sprite getSprite() {
        return TextureAtlas.FAKE_WASD;
    }
}