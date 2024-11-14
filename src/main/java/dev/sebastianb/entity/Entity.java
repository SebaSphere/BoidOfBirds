package dev.sebastianb.entity;

import dev.sebastianb.world.WorldLevelStage;

import java.awt.*;

public class Entity {

    private int x, y;
    private int oldX, oldY;
    private WorldLevelStage worldLevelStage;

    public Entity(WorldLevelStage worldLevelStage, int x, int y) {
        this.worldLevelStage = worldLevelStage;
        this.x = x;
        this.y = y;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getOldPosition() {
        return new Point(oldX, oldY);
    }
}
