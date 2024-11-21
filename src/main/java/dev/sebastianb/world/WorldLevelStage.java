package dev.sebastianb.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.sebastianb.entity.Entity;
import dev.sebastianb.entity.boid.SmallBoidEntity;

import java.util.ArrayList;
import java.util.Random;

public class WorldLevelStage {

    private int tickCount = 0;

    private ArrayList<Entity> entities = new ArrayList<>();

    private int TICK_SPREAD_DURATION = (int) (1000F/60F);
    // used in how many times a tick will update per second, in this case 60tps
    // I could probably look into making it 20 tps and lerping if I wanted it to be smooth

    private long lastTickTime;

    public WorldLevelStage() {
        // test entity
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(400);
            int y = random.nextInt(400);
            entities.add(new SmallBoidEntity(this, x, y));
        }
        // use gdx time since start
        lastTickTime = System.currentTimeMillis();
    }

    private boolean shouldUpdateTick = true;

    public int getTickCount() {
        return tickCount;
    }

    public void preTick() {

        if (lastTickTime + TICK_SPREAD_DURATION < System.currentTimeMillis()) {
            lastTickTime = System.currentTimeMillis();
            shouldUpdateTick = true;
        }

        if (shouldUpdateTick) {
            tickCount++;
            shouldUpdateTick = false;
            postTick();
        }
    }

    private void postTick() {
        for (Entity entity : entities) {
            entity.tick(tickCount);
        }
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

}
