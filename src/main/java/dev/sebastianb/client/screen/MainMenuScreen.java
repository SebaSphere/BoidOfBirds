package dev.sebastianb.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.sebastianb.client.GameClient;
import dev.sebastianb.client.registry.ScreenRegistry;


import java.util.ArrayList;

// TODO: perhaps have a widget system to make it simple to make buttons?
public class MainMenuScreen extends GameScreen {

    private SpriteBatch batch;

    Texture texture;
    TextButton.TextButtonStyle textButtonStyle;
    Button button;

    Stage stage = new Stage();

    float realButtonWidth = 0, realButtonHeight;



    public MainMenuScreen(GameClient game) {
        super(game);

        batch = new SpriteBatch();

        // Texture for button
        texture = new Texture(Gdx.files.internal("assets/menu/game/instructions_menu.png"));

        // Create textButtonStyle
        textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(texture));


        // Set the font you locally have available
        BitmapFont buttonFont = new BitmapFont();
        buttonFont.getData().setScale(5.0f);


        textButtonStyle.font = buttonFont;
        button = new TextButton("Start", textButtonStyle);

        realButtonHeight = button.getHeight();
        realButtonWidth = button.getWidth();


    }

    @Override
    public void show()
    {

    }



    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.RED);

        gameClient.viewport.apply();
        gameClient.batch.setProjectionMatrix(gameClient.viewport.getCamera().combined);

        gameClient.batch.begin();
        {
            gameClient.font.draw(gameClient.batch, "Welcome to the game!!! ", 1, 1.5f);
            gameClient.font.draw(gameClient.batch, "Tap anywhere to begin!", 1, 1);
        }
        gameClient.batch.end();


        button.setPosition(
                (float) Gdx.graphics.getWidth() / 2 - button.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - button.getHeight() / 2);


        float scale = Math.min(Gdx.graphics.getWidth() / realButtonWidth, Gdx.graphics.getHeight() / realButtonHeight);

        button.setSize(realButtonWidth * scale / 2, realButtonHeight * scale / 2);


        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
        // Input checking for button overlap
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX(); // Screen coordinates
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Invert Y-axis

            if (touchX >= button.getX() && touchX <= button.getX() + button.getWidth() &&
                    touchY >= button.getY() && touchY <= button.getY() + button.getHeight()) {

                this.dispose();
                gameClient.setScreen(ScreenRegistry.WORLD_SCREEN.getGameScreen());
            }
        }


        // Drawing the stage
        stage.draw();
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
    public void dispose()
    {
        batch.dispose();
        texture.dispose();
        stage.dispose();

    }
}
