package dev.sebastianb.boids.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.sebastianb.boids.client.atlas.TextureAtlas;
import dev.sebastianb.boids.world.WorldLevelStage;

public class FakeWASDEntity extends Entity {
    private static final float VELOCITY_Y = 2f; // Speed at which the entity moves down


    public FakeWASDEntity(WorldLevelStage worldLevelStage, int x, int y) {
        super(worldLevelStage, x, y);
    }

    @Override
    public void tick(int time) {
        // Move down after 5 seconds
        if (worldLevelStage.getTickCount() > WorldLevelStage.TICKS_PER_SECOND * 5) {
            y -= VELOCITY_Y;
        }

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