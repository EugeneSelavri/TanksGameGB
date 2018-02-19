package com.tanks.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameScreen implements Screen {
    private SpriteBatch batch;

    public Tank getPlayer() {
        return player;
    }

    private TextureRegion textureBackground;
    private Map map;
    private MyTank player;
    private BotTank bot;
    private float dt;
    private RenderableEmitter<Bullet> bulletEmitter = new RenderableEmitter<>();
    private RenderableEmitter<Explosion> expEmitter = new RenderableEmitter<>();

    private Stage stage;

    public Tank getBot() {
        return bot;
    }

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

    private void createOtherBtn() {
        
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menuBtn");
        textButtonStyle.font = font32;
        skin.add("tbs", textButtonStyle);

        TextButton btnFire = new TextButton("FIRE", skin, "tbs");
        TextButton btnExit = new TextButton("EXIT", skin, "tbs");
        btnFire.setPosition(1000, 140);
        btnExit.setPosition(1000, 620);
        stage.addActor(btnFire);
        stage.addActor(btnExit);

        btnFire.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                time = System.currentTimeMillis();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                long time2 = System.currentTimeMillis();
                long dt = time2 - time;
                bulletEmitter.addNew(player.makeBullet(dt));
            }
        });
        btnExit.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.MENU, null);
            }
        });
    }



    private void createJoystick() {
        group = new Group();
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menuBtn");
        textButtonStyle.font = font32;
        skin.add("tbs", textButtonStyle);

        TextButton btnLeft = new TextButton("LEFT", skin, "tbs");
        TextButton btnRight = new TextButton("RIGHT", skin, "tbs");
        TextButton btnUp = new TextButton("UP", skin, "tbs");
        TextButton btnDown = new TextButton("DOWN", skin, "tbs");

        btnLeft.setPosition(20, 140);
        btnRight.setPosition(300, 140);
        btnUp.setPosition(140, 240);
        btnDown.setPosition(140, 40);

        group.addActor(btnLeft);
        group.addActor(btnRight);
        group.addActor(btnUp);
        group.addActor(btnDown);

        stage.addActor(group);

        btnLeft.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.setExternalMoveTank(-1);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.setExternalMoveTank(0);
            }
        });
        btnRight.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.setExternalMoveTank(1);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.setExternalMoveTank(0);
            }
        });

        btnUp.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.setExternalMoveTurret(1);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.setExternalMoveTurret(0);
            }
        });
        btnDown.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.setExternalMoveTurret(-1);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.setExternalMoveTurret(0);
            }
        });

    }

    @Override
    public void show() {
        dt = Gdx.graphics.getDeltaTime();
        font32 = Assets.getInstance().getAssetManager().get("zorque32.ttf", BitmapFont.class);
        textureBackground = Assets.findTexture("background");

        map = new Map();
        player = new MyTank(this, new Vector2(100, 380));
        bot = new BotTank(this, new Vector2(1100, 380), player);

        createGUI();
        setupInput();
        createJoystick();
        createOtherBtn();
    }

    private long time;

    private void setupInput() {
        InputMultiplexer multiInput = new InputMultiplexer();
        multiInput.addProcessor(createKeyboardInputProcessor());
        multiInput.addProcessor(stage);
        Gdx.input.setInputProcessor(multiInput);

    }

    private InputProcessor createKeyboardInputProcessor() {
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

        return ip;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(textureBackground, 0, 0);
        map.render(batch);
        if (player.getHp() > 0) {
            player.render(batch);
            player.renderHP(batch);
        } else if (player.getHp() == 0) {
            ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.RESULT, "bot");
        }

        if (bot.hp > 0) {
            bot.render(batch);
            bot.renderHP(batch);
        } else if (bot.getHp() == 0) {
            ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.RESULT, "player");
        }

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
