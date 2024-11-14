package dev.sebastianb.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.sebastianb.client.GameClient;
import dev.sebastianb.client.buttons.BaseButton;

import java.util.ArrayList;

// TODO: perhaps have a widget system to make it simple to make buttons?
public class MainMenuScreen implements Screen
{
    private ArrayList<BaseButton> buttons;
    private SpriteBatch batch;
    GameClient gameClient;

    public MainMenuScreen(GameClient game) {
        this.gameClient = game;
    }
    {
        batch = new SpriteBatch();
        buttons = new ArrayList<>();
        buttons.add(new BaseButton( 100, 200, "MainMenu_Background.png", 1.0f));

    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);

        gameClient.viewport.apply();
        gameClient.batch.setProjectionMatrix(gameClient.viewport.getCamera().combined);

        gameClient.batch.begin();

        // render logic
/*
        for (BaseButton button : buttons)
        {
            Basebutton.draw(batch);
        }
*/
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
