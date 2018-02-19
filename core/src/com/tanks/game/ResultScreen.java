package com.tanks.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ResultScreen implements Screen {
    private SpriteBatch batch;

    private BitmapFont font32;
    private BitmapFont font96;
    private Tank player;

    private TextureRegion textureBackground;

    private Stage stage;
    private Skin skin;

    private String winner;

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public ResultScreen(SpriteBatch batch, GameScreen gameScreen) {
        this.batch = batch;
        this.player = gameScreen.getPlayer();
    }

    @Override
    public void show() {
       textureBackground = Assets.findTexture("background");
        font32 = Assets.getInstance().getAssetManager().get("zorque32.ttf", BitmapFont.class);
        font96 = Assets.getInstance().getAssetManager().get("zorque96.ttf", BitmapFont.class);
        createGUI();
    }

    public void createGUI() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        skin = new Skin(Assets.getInstance().getAtlas());
        Gdx.input.setInputProcessor(stage);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menuBtn");
        textButtonStyle.font = font32;
        skin.add("tbs", textButtonStyle);

        TextButton btnOk = new TextButton("OK((((0(", skin, "tbs");
        btnOk.setPosition(520, 20);
        stage.addActor(btnOk);

        btnOk.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.MENU, null);
            }
        });
    }

    @Override
    public void render(float delta) {
        update(delta);
        batch.begin();
        batch.draw(textureBackground, 0, 0);
        font96.draw(batch, "Winner: ", 0, 320, 1280, 1, false);
        font32.draw(batch, winner, 0, 190, 1280, 1, false);
        batch.end();
        stage.draw();
    }

    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void resize(int width, int height) { ScreenManager.getInstance().onResize(width, height); }

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
