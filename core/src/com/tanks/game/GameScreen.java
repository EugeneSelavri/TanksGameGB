package com.tanks.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private TextureRegion textureBackground;
    private Map map;
    private MyTank player;
    private BotTank bot;
    private float dt;
    private RenderableEmitter<Bullet> bulletEmitter = new RenderableEmitter<>();
    private RenderableEmitter<Explosion> expEmitter = new RenderableEmitter<>();

    private Stage stage;
    private BitmapFont font32;
    private Skin skin;
    private Group group;

    public Map getMap() {
        return map;
    }

    public static final float GLOBAL_GRAVITY = 300.0f;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;



    }

    private void createGUI() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        skin = new Skin(Assets.getInstance().getAtlas());

    }

    private void createJoystick() {

    }

    @Override
    public void show() {
        dt = Gdx.graphics.getDeltaTime();
        font32 = Assets.getInstance().getAssetManager().get("zorque32.ttf", BitmapFont.class);
        textureBackground = Assets.findTexture("background");

        map = new Map();
        player = new MyTank(this, new Vector2(100, 380));
        bot = new BotTank(this, new Vector2(1100, 380));

        createGUI();
        keyboardListener();
    }

    private long time;

    private void keyboardListener() {
        InputProcessor ip = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    time = System.currentTimeMillis();
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    long time2 = System.currentTimeMillis();
                    long dt = time2 - time;
                    bulletEmitter.addNew(player.makeBullet(dt));
                }
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };
        Gdx.input.setInputProcessor(ip);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(textureBackground, 0, 0);
        map.render(batch);
        if (player.getHp() != 0) {
            player.render(batch);
            player.renderHP(batch);
        }
        bot.render(batch);
        bot.renderHP(batch);

        expEmitter.render(batch);
        bulletEmitter.render(batch);
        bot.getBotBulletEmitter().render(batch);

        update(delta);

        batch.end();
        stage.draw();
    }

    public void update(float dt) {

        map.update(dt);
        player.update(dt);
        bot.update(dt);
        checkCollisions(bulletEmitter);
        checkCollisions(bot.botBulletEmitter);
    }

    public void checkCollisions(RenderableEmitter<Bullet> emitter) {

        for (Bullet b : emitter) {
            if (b.isActive()) {
                Vector2 pos = b.getPosition();

                if (map.isGround(pos.x, pos.y)) {
                    b.deactivate();
                    Vector2 center = b.getCenter();
                    expEmitter.addNew(new Explosion(center.x, center.y));
                    map.clearGround(pos.x, pos.y, 8);
                }

                if (player.isTank(pos.x, pos.y)) {
                    b.deactivate();
                    player.makeDamage(5);
                    Vector2 center = b.getCenter();
                    expEmitter.addNew(new Explosion(center.x, center.y));
                }

                if (bot.isTank(pos.x, pos.y)) {
                    b.deactivate();
                    bot.makeDamage(5);
                    Vector2 center = b.getCenter();
                    expEmitter.addNew(new Explosion(center.x, center.y));
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {

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
