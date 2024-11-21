package dev.sebastianb.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.sebastianb.client.GameClient;
import dev.sebastianb.entity.Entity;
import dev.sebastianb.world.WorldLevelStage;

public class WorldRendererScreen extends GameScreen {

    private GameClient gameClient;
    private WorldLevelStage worldLevelStage;
    private Texture background;

    private SpriteBatch batch;

    private Stage stage;

    private TextureRegion region; //added texture region

    public WorldRendererScreen(GameClient game, WorldLevelStage worldLevelStage) {
        super(game);
        this.gameClient = game;
        this.worldLevelStage = worldLevelStage;
        this.batch = gameClient.batch;

        background = new Texture(Gdx.files.internal("assets/menu/game/desert_moons.png")); // Replace with your image path

        this.region = new TextureRegion(new Texture(Gdx.files.internal("assets/entity/dodo.png"))); // Initialize texture region with your texture

        stage = new Stage(gameClient.viewport, gameClient.batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
        batch.end();
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
        region.getTexture().dispose(); //dispose of texture when done
    }
}
