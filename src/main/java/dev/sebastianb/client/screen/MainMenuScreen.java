package dev.sebastianb.client.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.sebastianb.client.GameClient;

public class MainMenuScreen implements Screen {

    GameClient gameClient;

    public MainMenuScreen(GameClient game) {
        this.gameClient = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);

        gameClient.viewport.apply();
        gameClient.batch.setProjectionMatrix(gameClient.viewport.getCamera().combined);

        gameClient.batch.begin();
        //draw text. Remember that x and y are in meters
        gameClient.font.draw(gameClient.batch, "Welcome to Drop!!! ", 1, 1.5f);
        gameClient.font.draw(gameClient.batch, "Tap anywhere to begin!", 1, 1);
        gameClient.batch.end();
        
    }

    @Override
    public void resize(int i, int i1) {

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
