package dev.sebastianb.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.sebastianb.client.screen.MainMenuScreen;
import dev.sebastianb.client.screen.WorldRendererScreen;
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
        viewport = new FitViewport(8, 5);

        //font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        this.setScreen(new MainMenuScreen(this));
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
