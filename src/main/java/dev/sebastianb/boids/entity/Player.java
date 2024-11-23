package dev.sebastianb.boids.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.sebastianb.boids.client.atlas.TextureAtlas;
import dev.sebastianb.boids.entity.boid.SmallBoidEntity;
import dev.sebastianb.boids.world.WorldLevelStage;

public class Player extends Entity {

    private float speed = 10f; // Movement speed of the player

    public Player(WorldLevelStage worldLevelStage, int x, int y) {
        super(worldLevelStage, x, y);
    }

    @Override
    public void tick(int time) {
        handleInput();
        // Ensure the player stays within the game window boundaries
        adjustPositionForBoundaries();

        int radius = 20;
        int boidInRange = worldLevelStage.getEntities().stream()
                .filter(entity -> entity instanceof SmallBoidEntity)
                // is in 15 radius
                .filter(entity -> Vector2.dst(this.x, this.y, entity.getX(), entity.getY()) <= radius)
                .toList().size();
        if (boidInRange >= 1) {
            worldLevelStage.setGameOver(true);
        }

    }

    /**
     * Handles WASD input to move the player.
     */
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += speed;
        }
    }

    /**
     * Prevents the player from moving outside the game window.
     */
    private void adjustPositionForBoundaries() {
        int windowWidth = Gdx.graphics.getWidth();
        int windowHeight = Gdx.graphics.getHeight();
        int textureWidth = getSprite().getRegionWidth();
        int textureHeight = getSprite().getRegionHeight();
        int margin = 25;

        // Keep the player within the window, considering the margin
        x = Math.max(margin, Math.min(x, windowWidth - textureWidth - margin));
        y = Math.max(margin, Math.min(y, windowHeight - textureHeight - margin));
    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        TextureAtlas.DODO_PLAYER.setPosition(this.x, this.y);
        TextureAtlas.DODO_PLAYER.setScale(1f); // Set the scale to desired size
        TextureAtlas.DODO_PLAYER.draw(spriteBatch);
    }

    @Override
    public Sprite getSprite() {
        return TextureAtlas.DODO_PLAYER;
    }
}