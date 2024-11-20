package dev.sebastianb.entity;

import dev.sebastianb.world.WorldLevelStage;

import java.awt.*;
import java.awt.geom.Point2D;

public class Entity {

    private double x, y;
    private double oldX, oldY;
    private WorldLevelStage worldLevelStage;

    public Entity(WorldLevelStage worldLevelStage, int x, int y) {
        this.worldLevelStage = worldLevelStage;
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
    }

    public Point2D.Double getPosition() {
        return new Point2D.Double(x, y);
    }

    public void tick(int time) {
        double offset = Math.cos(time);
        this.x += offset;

        System.out.println(getVelocity());

    }

    public void setPosition(int x, int y) {
        this.oldX = this.x;
        this.oldY = this.y;
        this.x = x;
        this.y = y;
    }

    public Point2D.Double getOldPosition() {
        return new Point2D.Double(oldX, oldY);
    }

    // get velocity, the difference between old and new point
    public double getVelocity() {
        return Math.abs(Math.sqrt(Math.pow(x - oldX, 2) + Math.pow(y - oldY, 2)));
    }
}
