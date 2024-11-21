package dev.sebastianb.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.sebastianb.client.atlas.TextureAtlas;
import dev.sebastianb.world.WorldLevelStage;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Entity {

    protected float x, y;
    protected double oldX, oldY;
    protected WorldLevelStage worldLevelStage;

    protected Sprite sprite;

    public Entity(WorldLevelStage worldLevelStage, int x, int y) {
        this.worldLevelStage = worldLevelStage;
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
    }

    public void tick(int time) {
        int windowWidth = Gdx.graphics.getWidth();
        int windowHeight = Gdx.graphics.getHeight();

        // get the width and height of the texture
        int textureWidth = getSprite().getRegionWidth();
        int textureHeight = getSprite().getRegionHeight();

        boolean hitHorizontalEdge = false;
        boolean hitVerticalEdge = false;

        // check bounds considering the width and height of the texture
        if (x < 0 || x + textureWidth > windowWidth) {
            rotationOffset = (rotationOffset + 180) % 360;
            hitHorizontalEdge = true;
        }
        if (y < 0 || y + textureHeight > windowHeight) {
            rotationOffset = (rotationOffset + 180) % 360;
            hitVerticalEdge = true;
        }

        // If entity hits a corner, randomly changes its horizontal direction (I hope this works, VERY rarely happens and this code was AI gen)
        if (hitHorizontalEdge && hitVerticalEdge) {

            if (Math.random() > 0.5) {
                rotationOffset = (rotationOffset + 90) % 360;
            } else {
                rotationOffset = (rotationOffset - 90) % 360;
            }
        }
    }

    public float rotation;
    public float rotationOffset;

    public float getRotationAngle() {

        return 180 + rotationOffset;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setPosition(int x, int y) {
        this.oldX = this.x;
        this.oldY = this.y;
        this.x = x;
        this.y = y;
    }

    // get velocity, the difference between old and new point
    public double getVelocity() {
        return Math.abs(Math.sqrt(Math.pow(x - oldX, 2) + Math.pow(y - oldY, 2)));
    }

    public void render(SpriteBatch spriteBatch) {

    }

    public Sprite getSprite() {
        return null;
    }

}
