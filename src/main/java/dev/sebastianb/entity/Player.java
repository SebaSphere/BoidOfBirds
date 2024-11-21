package dev.sebastianb.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.sebastianb.client.atlas.TextureAtlas;
import dev.sebastianb.world.WorldLevelStage;

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

        // Keep the player within the window
        x = Math.max(0, Math.min(x, windowWidth - textureWidth));
        y = Math.max(0, Math.min(y, windowHeight - textureHeight));
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