package dev.sebastianb.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.sebastianb.client.GameClient;

public class WorldRendererScreen extends GameScreen {

    private GameClient gameClient;

    public WorldRendererScreen(GameClient game) {
        super(game);
        this.gameClient = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLUE);

        gameClient.viewport.apply();
        gameClient.batch.setProjectionMatrix(gameClient.viewport.getCamera().combined);

        gameClient.batch.begin();
        {
            
        }
        gameClient.batch.end();

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

    }
}
