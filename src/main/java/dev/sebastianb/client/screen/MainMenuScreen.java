package dev.sebastianb.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.sebastianb.client.GameClient;

// TODO: perhaps have a widget system to make it simple to make buttons?
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

        // render logic
        {
            gameClient.font.draw(gameClient.batch, "Welcome to the game!!! ", 1, 1.5f);
            gameClient.font.draw(gameClient.batch, "Tap anywhere to begin!", 1, 1);
        }

        gameClient.batch.end();
        if (Gdx.input.isTouched()) {
            System.out.println("\t\t\t\t\t\t\t\t\t\t\tThere is no Game Here");
            gameClient.setScreen(new MainMenuScreen(gameClient));
            dispose();
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

    }
}
