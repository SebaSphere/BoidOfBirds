package dev.sebastianb.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.sebastianb.entity.Entity;

import java.util.ArrayList;

public class WorldLevelStage {


    private ArrayList<Entity> entities = new ArrayList<>();

    int TICK_SPREAD_DURATION = (int) (1000F/60F);
    // used in how many times a tick will update per second, in this case 60tps

    long lastTickTime;

    public WorldLevelStage() {
        // test entity
        entities.add(new Entity(this, 10, 10));
        // use gdx time since start
        lastTickTime = System.currentTimeMillis();
    }

    boolean shouldUpdateTick = true;

    public void preTick() {


        if (lastTickTime + TICK_SPREAD_DURATION < System.currentTimeMillis()) {
            lastTickTime = System.currentTimeMillis();
            shouldUpdateTick = true;
        }


        if (shouldUpdateTick) {
            System.out.println("tick" + lastTickTime);
            shouldUpdateTick = false;
        }
    }






}
