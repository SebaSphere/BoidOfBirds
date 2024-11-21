package dev.sebastianb.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.sebastianb.client.GameClient;
import dev.sebastianb.client.registry.ScreenRegistry;

public class MainMenuScreen extends GameScreen {

    private SpriteBatch batch;

    private Texture texture;
    private Texture background;
    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton button;

    private Stage stage = new Stage();

    private float realButtonWidth, realButtonHeight;

    public MainMenuScreen(GameClient game) {
        super(game);

        batch = new SpriteBatch();

        background = new Texture(Gdx.files.internal("assets/menu/main/MainMenu_Background.png"));

        // Texture for button
        texture = new Texture(Gdx.files.internal("assets/menu/game/instructions_menu.png"));

        // Create TextButtonStyle
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(texture));


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/rubik_iso_regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48; // Adjust size to your preference
        parameter.incremental = true; // Enable incremental loading for larger fonts
        BitmapFont buttonFont = generator.generateFont(parameter);
        buttonFont.getData().setScale(2.0f); // Scale font
        generator.dispose(); // Dispose generator to avoid memory leaks

        textButtonStyle.font = buttonFont;

        // Create the button
        button = new TextButton("Start", textButtonStyle);

        // Get real dimensions after font scaling
        realButtonWidth = button.getWidth();
        realButtonHeight = button.getHeight();
    }

    @Override
    public void show() {
        // Add the button to the stage
        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.RED);

        gameClient.viewport.apply();
        gameClient.batch.setProjectionMatrix(gameClient.viewport.getCamera().combined);

        batch.begin();

        // Draw the background image to cover the screen
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();

        // Center the button on the screen
        button.setPosition(
                (Gdx.graphics.getWidth() - button.getWidth()) / 2,
                (Gdx.graphics.getHeight() - button.getHeight()) / 2
        );

        // Scale the button size based on screen size
        float scale = Math.min(
                Gdx.graphics.getWidth() / realButtonWidth,
                Gdx.graphics.getHeight() / realButtonHeight
        );
        button.setSize(realButtonWidth * scale / 2, realButtonHeight * scale / 2);

        // Draw the stage
        stage.draw();

        // Handle button click
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (touchX >= button.getX() && touchX <= button.getX() + button.getWidth() &&
                    touchY >= button.getY() && touchY <= button.getY() + button.getHeight()) {
                this.dispose();
                gameClient.setScreen(ScreenRegistry.WORLD_SCREEN.getGameScreen());
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        gameClient.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        stage.dispose();
    }
}