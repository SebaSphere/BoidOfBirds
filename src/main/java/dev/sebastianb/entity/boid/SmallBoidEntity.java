package dev.sebastianb.entity.boid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.sebastianb.client.atlas.TextureAtlas;
import dev.sebastianb.entity.Entity;
import dev.sebastianb.world.WorldLevelStage;

public class SmallBoidEntity extends Entity {

    public SmallBoidEntity(WorldLevelStage worldLevelStage, int initialX, int initialY) {
        super(worldLevelStage, initialX, initialY);

    }

    @Override
    public void tick(int time) {
        super.tick(time);
        moveForward(7);

    }

    public void moveForward(float speed) {
        float angleRad = (float) Math.toRadians(getRotationAngle());
        this.x += (float) (speed * Math.cos(angleRad));
        this.y += (float) (speed * Math.sin(angleRad));
    }

    public float getRotationAngle() {
        rotation = (worldLevelStage.getTickCount() + rotationOffset) % 360;
        return rotation;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        super.render(spriteBatch);
        TextureAtlas.BIRD.setPosition(this.x, this.y);
        TextureAtlas.BIRD.setRotation((float) (getRotationAngle() + 270));
        TextureAtlas.BIRD.draw(spriteBatch);
    }

    @Override
    public Sprite getSprite() {
        return TextureAtlas.BIRD;
    }
}
