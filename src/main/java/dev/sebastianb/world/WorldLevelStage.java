package dev.sebastianb.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.sebastianb.entity.Entity;
import dev.sebastianb.entity.boid.SmallBoidEntity;
import dev.sebastianb.util.RenderUtils;

import java.util.ArrayList;
import java.util.Random;

public class WorldLevelStage {

    private int tickCount = 0;

    private ArrayList<Entity> entities = new ArrayList<>();

    public static int TICKS_PER_SECOND = 60;

    private int TICK_SPREAD_DURATION = (int) (1000F/TICKS_PER_SECOND);
    // used in how many times a tick will update per second, in this case 60tps
    // I could probably look into making it 20 tps and lerping if I wanted it to be smooth

    private long lastTickTime;

    public WorldLevelStage() {
        // test entity
        spawnBoid();


        // use gdx time since start
        lastTickTime = System.currentTimeMillis();
    }

    private Random random = new Random();

    private void spawnBoid() {
        int x = random.nextInt(RenderUtils.monitorWidth);
        int y = random.nextInt(RenderUtils.monitorHeight);
        var boid = new SmallBoidEntity(this, x, y);
        boid.setVelocityX(-5 + random.nextFloat(10));
        boid.setVelocityY(-5 + random.nextFloat(10));

        entities.add(boid);
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
        if (tickCount % TICKS_PER_SECOND * 10 == 0) {
            // try spawning again if player is within 200 units
            spawnBoid();
        }
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

}
