package dev.sebastianb.boids.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.sebastianb.boids.Main;
import dev.sebastianb.boids.client.GameClient;
import dev.sebastianb.boids.entity.Entity;
import dev.sebastianb.boids.util.RenderUtils;
import dev.sebastianb.boids.world.WorldLevelStage;

import java.io.File;

public class WorldRendererScreen extends GameScreen {

    private GameClient gameClient;
    private WorldLevelStage worldLevelStage;
    private Texture background;

    private SpriteBatch batch;
    private BitmapFont font;

    public WorldRendererScreen(GameClient game, WorldLevelStage worldLevelStage) {
        super(game);
        this.gameClient = game;
        this.worldLevelStage = worldLevelStage;
        this.batch = gameClient.batch;

        font = new BitmapFont();

        background = new Texture(Gdx.files.internal("assets/menu/game/desert_moons.png")); // Replace with your image path

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (batch == null) {
            batch = new SpriteBatch();
        }

        ScreenUtils.clear(Color.BLACK);

        gameClient.viewport.apply();
        batch.setProjectionMatrix(gameClient.viewport.getCamera().combined);

        batch.begin();

        // Draw the background image to cover the screen
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();

        batch.begin();
        for (Entity entity : worldLevelStage.getEntities()) {
            entity.render(batch);
        }

        worldLevelStage.fakeWASDEntity.render(batch);
        batch.end();

        if (worldLevelStage.isGameOver()) {
            // render text with high score
            batch.begin();
            font.getData().setScale(2f); // Set font size (optional)
            font.setColor(Color.RED); // Set font color (optional)
            font.draw(batch, "Game Over!", RenderUtils.monitorWidth - 100, 200); // Text, x, y coordinates
            font.draw(batch, "High Score: " + worldLevelStage.getHighestBoidCountFromFile(), RenderUtils.monitorWidth - 100, 150); // High score text
            font.draw(batch, "Round Score: " + worldLevelStage.getBoidCount(), RenderUtils.monitorWidth - 100, 100); // score text


            batch.end();

        }

        // this is a really really bad thing you would NEVER do in production
        // I tried setting the screen but it's complaining about SpriteBatch not being allocated
        // when I try to set the screen and dispose
        if (worldLevelStage.isTrulyGameOver()) {
            try {
                // Get the main class and invoke its main method
                String className = Main.class.getName();
                String javaHome = System.getProperty("java.home");
                String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
                String classPath = System.getProperty("java.class.path");
                String[] command = new String[]{javaBin, "-cp", classPath, className};

                // Run the new process
                ProcessBuilder builder = new ProcessBuilder(command);
                builder.start();

                // Exit the current application
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        gameClient.viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
