package dev.sebastianb.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.sebastianb.entity.Entity;
import dev.sebastianb.entity.FakeWASDEntity;
import dev.sebastianb.entity.Player;
import dev.sebastianb.entity.boid.SmallBoidEntity;
import dev.sebastianb.util.RenderUtils;

import java.util.ArrayList;
import java.util.Random;

public class WorldLevelStage {

    private int tickCount = 0;

    private ArrayList<Entity> entities = new ArrayList<>();
    private Player player;

    public static int TICKS_PER_SECOND = 60;

    private int TICK_SPREAD_DURATION = (int) (1000F / TICKS_PER_SECOND);
    // Used to control the tick rate (60 ticks per second).

    private long lastTickTime;

    public FakeWASDEntity fakeWASDEntity;

    public WorldLevelStage() {
        // Initialize player at position (200, 200)
        player = new Player(this, 200, 200);
        entities.add(player);

        // Test entities
        spawnBoid();
        fakeWASDEntity
                = new FakeWASDEntity(this, RenderUtils.monitorWidth / 40, RenderUtils.monitorHeight / 40);

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
        fakeWASDEntity.tick(tickCount);

        if (tickCount % (TICKS_PER_SECOND * 2) == 0) {
            // Spawn new boids periodically
            spawnBoid();
        }
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public void render(SpriteBatch spriteBatch) {
        // Render all entities, including the player
        for (Entity entity : entities) {
            entity.render(spriteBatch);
        }
        fakeWASDEntity.render(spriteBatch);
    }
}