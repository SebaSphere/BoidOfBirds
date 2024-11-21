package dev.sebastianb.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.sebastianb.client.atlas.TextureAtlas;
import dev.sebastianb.client.screen.MainMenuScreen;
import dev.sebastianb.client.registry.ScreenRegistry;
import dev.sebastianb.client.screen.WorldRendererScreen;
import dev.sebastianb.entity.Entity;
import dev.sebastianb.world.WorldLevelStage;

public class GameClient extends Game {


    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;

    private WorldLevelStage worldLevelStage = new WorldLevelStage();

    @Override
    public void create() {
        batch = new SpriteBatch();
        // use libGDX's default font
        font = new BitmapFont();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(true);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        TextureAtlas.register();

        ScreenRegistry.register(this, worldLevelStage);

        this.setScreen(ScreenRegistry.MENU.getGameScreen());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
        // if in world screen, update logic
        if (this.screen instanceof WorldRendererScreen worldRendererScreen) {
            worldLevelStage.preTick();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
