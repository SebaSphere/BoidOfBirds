package dev.sebastianb.boids.world;

import dev.sebastianb.boids.entity.Entity;
import dev.sebastianb.boids.entity.FakeWASDEntity;
import dev.sebastianb.boids.entity.Player;
import dev.sebastianb.boids.entity.boid.SmallBoidEntity;
import dev.sebastianb.boids.util.RenderUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class WorldLevelStage {

    private int tickCount = 0;

    private int boidCount = 0;

    private ArrayList<Entity> entities = new ArrayList<>();
    private Player player;

    public static int TICKS_PER_SECOND = 60;

    private int TICK_SPREAD_DURATION = (int) (1000F / TICKS_PER_SECOND);
    // Used to control the tick rate (60 ticks per second).

    private long lastTickTime;

    public FakeWASDEntity fakeWASDEntity;

    public WorldLevelStage() {
        // Initialize player at position (200, 200)
        player
                = new Player(this, RenderUtils.monitorWidth / 2, RenderUtils.monitorHeight / 2);
        entities.add(player);

        int x, y;

        do {
            x = random.nextInt(RenderUtils.monitorWidth);
            y = random.nextInt(RenderUtils.monitorHeight);
        } while (Math.hypot(x - player.getX(), y - player.getY()) < 100); // Check distance

        spawnBoid(x, y);
        fakeWASDEntity
                = new FakeWASDEntity(this, RenderUtils.monitorWidth / 40, RenderUtils.monitorHeight / 40);

        lastTickTime = System.currentTimeMillis();
    }

    private Random random = new Random();

    private void spawnBoid(int x, int y) {
        var boid = new SmallBoidEntity(this, x, y);
        boid.setVelocityX(-5 + random.nextFloat(10));
        boid.setVelocityY(-5 + random.nextFloat(10));

        entities.add(boid);

        boidCount++;
    }

    private boolean shouldUpdateTick = true;

    public int getTickCount() {
        return tickCount;
    }

    boolean isGameOver = false;

    // should flip from false to true after 5 or so seconds
    boolean isTrulyGameOver = false;


    public void preTick() {
        if (!isGameOver) {
            if (lastTickTime + TICK_SPREAD_DURATION < System.currentTimeMillis()) {
                lastTickTime = System.currentTimeMillis();
                shouldUpdateTick = true;
            }

            if (shouldUpdateTick) {
                tickCount++;
                shouldUpdateTick = false;
                postTick();
            }
        } else {
            if (lastTickTime + (1000 * 3) < System.currentTimeMillis()) {
                isTrulyGameOver = true;
            }
        }
    }

    private void postTick() {
        for (Entity entity : entities) {
            entity.tick(tickCount);
        }
        fakeWASDEntity.tick(tickCount);

        if (tickCount % (TICKS_PER_SECOND * 1.2) == 0) {
            // Spawn new boids periodically
            int x, y;

            do {
                x = random.nextInt(RenderUtils.monitorWidth);
                y = random.nextInt(RenderUtils.monitorHeight);
            } while (Math.hypot(x - player.getX(), y - player.getY()) < 100); // Check distance

            spawnBoid(x, y);
        }
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public int getBoidCount() {
        return boidCount;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isTrulyGameOver() {
        return isTrulyGameOver;
    }

    boolean hasAddedScore = false;

    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;

        if (!hasAddedScore) {
            this.addScoreToFile(getBoidCount());
        }
    }


    public int getHighestBoidCountFromFile() {
        String filePath = "scores.txt";
        BufferedReader reader = null;
        int maxCount = Integer.MIN_VALUE;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                try {
                    int count = Integer.parseInt(line.trim());
                    maxCount = Math.max(maxCount, count);
                } catch (NumberFormatException e) {
                    // Ignore malformed lines
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return maxCount;
    }

    public void addScoreToFile(int score) {
        String filePath = "scores.txt";
        FileWriter writer;
        try {
            writer = new FileWriter(filePath, true);  // Set the second argument to "true" for appending
            writer.write(String.valueOf(score) + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}