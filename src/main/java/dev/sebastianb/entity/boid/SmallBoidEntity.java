package dev.sebastianb.entity.boid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.sebastianb.client.atlas.TextureAtlas;
import dev.sebastianb.entity.Entity;
import dev.sebastianb.world.WorldLevelStage;

import java.util.List;

public class SmallBoidEntity extends Entity {


    private static final float COHERENCE_RATE = 0.05f;  // Rate for steering towards the group
    private static final float SEPARATION_DISTANCE = 20F; // Minimum distance to maintain
    private static final float SEPARATION_RATE = 0.2f;  // Rate for steering away
    private static final float ALIGNMENT_RATE = 0.05f;  // Rate for matching speed/direction

    private float velocityX = 0;
    private float velocityY = 0;

    public SmallBoidEntity(WorldLevelStage worldLevelStage, int initialX, int initialY) {
        super(worldLevelStage, initialX, initialY);

    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    @Override
    public void tick(int time) {

        // Get all nearby boids
        List<SmallBoidEntity> nearbyBoids = getNearbyBoids(40);

        // Calculate adjustments
        Vector2 coherenceAdjustment = calculateCoherence(nearbyBoids);
        Vector2 separationAdjustment = calculateSeparation(nearbyBoids);
        Vector2 alignmentAdjustment = calculateAlignment(nearbyBoids);

        // Combine adjustments
        velocityX += coherenceAdjustment.x + separationAdjustment.x + alignmentAdjustment.x;
        velocityY += coherenceAdjustment.y + separationAdjustment.y + alignmentAdjustment.y;

        // Limit speed to avoid overly fast boids
        limitSpeed(10);

        // Update position based on velocity
        this.x += velocityX;
        this.y += velocityY;

        // Adjust velocity based on boundary constraints from superclass
        adjustVelocityForBoundaries();

    }


    public float getRotationAngle() {
        return (float) Math.toDegrees(Math.atan2(velocityY, velocityX));
    }

    /**
     * Adjusts the velocity of the entity based on the boundaries of the window.
     * If the entity reaches the horizontal or vertical edges of the window, the velocity is reversed
     * and the position is adjusted to prevent the entity from going out of bounds.
     */
    private void adjustVelocityForBoundaries() {
        int windowWidth = Gdx.graphics.getWidth();
        int windowHeight = Gdx.graphics.getHeight();
        int textureWidth = getSprite().getRegionWidth();
        int textureHeight = getSprite().getRegionHeight();

        // Add a small buffer to prevent jittering when the sprite is right next to the boundary
        int buffer = 5;

        // Reverse velocity when hitting horizontal edge
        if (x < buffer || x + textureWidth + buffer > windowWidth) {
            velocityX = -velocityX;
            // Set x back to within the window
            x = Math.max(buffer, Math.min(x, windowWidth - textureWidth - buffer));
        }

        // Reverse velocity when hitting vertical edge
        if (y < buffer || y + textureHeight + buffer > windowHeight) {
            velocityY = -velocityY;
            // Set y back to within the window
            y = Math.max(buffer, Math.min(y, windowHeight - textureHeight - buffer));
        }
    }

    /**
     * Retrieves a list of nearby SmallBoidEntity objects within a specified radius.
     *
     * @param radius The radius within which to search for nearby boids
     * @return List of SmallBoidEntity objects within the specified radius
     */
    private List<SmallBoidEntity> getNearbyBoids(float radius) {
        return worldLevelStage.getEntities().stream()
                .filter(entity -> entity instanceof SmallBoidEntity && entity != this)
                .filter(entity -> Vector2.dst(this.x, this.y, entity.getX(), entity.getY()) <= radius)
                .map(entity -> (SmallBoidEntity) entity)
                .toList();
    }

    /**
     * Calculates the coherence vector for the current boid based on the positions of nearby boids.
     *
     * @param nearbyBoids List of nearby boids from which to calculate coherence
     * @return Vector2 representing the coherence adjustment for the current boid
     */
    private Vector2 calculateCoherence(List<SmallBoidEntity> nearbyBoids) {
        if (nearbyBoids.isEmpty()) return new Vector2(0, 0);

        Vector2 centerOfMass = new Vector2();
        for (SmallBoidEntity boid : nearbyBoids) {
            centerOfMass.add(boid.getX(), boid.getY());
        }
        centerOfMass.scl(1f / nearbyBoids.size()); // Average position

        return centerOfMass.sub(this.x, this.y).scl(COHERENCE_RATE); // Steer towards center
    }

    /**
     * Calculates the separation vector for the current boid based on the distances to nearby boids.
     *
     * @param nearbyBoids List of nearby boids from which to calculate separation
     * @return Vector2 representing the separation adjustment for the current boid
     */
    private Vector2 calculateSeparation(List<SmallBoidEntity> nearbyBoids) {
        Vector2 separation = new Vector2();
        for (SmallBoidEntity boid : nearbyBoids) {
            float distance = Vector2.dst(this.x, this.y, boid.getX(), boid.getY());
            if (distance < SEPARATION_DISTANCE) {
                separation.add(this.x - boid.getX(), this.y - boid.getY());
            }
        }
        return separation.scl(SEPARATION_RATE); // Steer away
    }

    /**
     * Calculates the alignment vector for the current boid based on the velocities of nearby boids.
     *
     * @param nearbyBoids List of nearby boids from which to calculate alignment
     * @return Vector2 representing the alignment adjustment for the current boid
     */
    private Vector2 calculateAlignment(List<SmallBoidEntity> nearbyBoids) {
        if (nearbyBoids.isEmpty()) return new Vector2(0, 0);

        Vector2 averageVelocity = new Vector2();
        for (SmallBoidEntity boid : nearbyBoids) {
            averageVelocity.add(boid.velocityX, boid.velocityY);
        }
        averageVelocity.scl(1f / nearbyBoids.size()); // Average velocity

        return averageVelocity.sub(velocityX, velocityY).scl(ALIGNMENT_RATE); // Steer to match
    }

    /**
     * Limits the speed of the entity to the specified maximum speed.
     *
     * @param maxSpeed The maximum speed to limit the entity to
     */
    private void limitSpeed(float maxSpeed) {
        float speed = (float) Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (speed > maxSpeed) {
            velocityX = (velocityX / speed) * maxSpeed;
            velocityY = (velocityY / speed) * maxSpeed;
        }
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
