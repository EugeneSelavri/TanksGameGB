package com.tanks.game;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tank {
    protected TextureRegion textureBaseBody;
    protected TextureRegion textureBaseTrack;
    protected TextureRegion textureTurret;
    protected TextureRegion healthPoint;
    protected Vector2 position;
    protected Vector2 weaponPosition;
    protected GameScreen game;
    protected float turretAngle;
    protected int hp;
    protected int maxHp;
    protected Sprite turr;
    protected Sprite body;
    protected Sprite track;
    protected boolean isHurt;

    public Tank(GameScreen game, Vector2 position) {
        this.game = game;
        this.position = position;
        weaponPosition = new Vector2(position).add(0, 0);
        textureBaseBody = Assets.findTexture("tankNavy");
        textureBaseTrack = Assets.findTexture("tankTrack");
        textureTurret = Assets.findTexture("turret");
        healthPoint = Assets.findTexture("hbar");
        healthPoint.setRegionWidth(healthPoint.getRegionWidth());
        healthPoint.setRegionHeight(healthPoint.getRegionHeight() / 2);
        turr = new Sprite(textureTurret);
        turr.setOrigin(0, textureTurret.getRegionHeight() / 2);
        body = new Sprite(textureBaseBody);
        body.setOrigin(0, 0);
        track = new Sprite(textureBaseTrack);
        track.setOrigin(0, 0);
        turretAngle = 0.0f;
        maxHp = 100;
        hp = maxHp;
    }

    public int getHp() {
        return hp;
    }

    public void render(SpriteBatch batch) {
        track.setPosition(position.x, position.y);
        track.draw(batch);

        body.setPosition(position.x -2, position.y + textureBaseTrack.getRegionHeight() / 2 + 5);
        body.setSize(textureBaseTrack.getRegionWidth() + 4, textureBaseBody.getRegionHeight());
        body.draw(batch);
    }

    public void renderHP(SpriteBatch batch) {
        batch.setColor(0.5f, 0, 0, 0.5f);
        batch.draw(healthPoint, position.x, position.y + textureBaseTrack.getRegionHeight() / 2 + 10 + textureBaseBody.getRegionHeight());
        batch.setColor(0, 1.0f, 0, 0.5f);
        batch.draw(healthPoint, position.x, position.y + textureBaseTrack.getRegionHeight() / 2 + 10 + textureBaseBody.getRegionHeight(), (int) (healthPoint.getRegionWidth() * (float) hp / maxHp), healthPoint.getRegionHeight());

        batch.setColor(1, 1, 1, 1);
    }

    public void update(float dt) {

        if (!checkOnGround()) {
            position.y -= 100.0f * dt;
            weaponPosition.set(position).add(20, 26);
        }
    }

    public void moveTurret(int n) {
        turretAngle += 0.7f * n;
        turr.setRotation(turretAngle);
    }

    public void moveTank(int n) {
        this.getPosition().x += 2 * n;

    }

    public Bullet makeBullet(long dt) {
        float cos = (float) Math.cos(Math.toRadians(turretAngle));
        float sin = (float) Math.sin(Math.toRadians(turretAngle));

        float turrSize = textureTurret.getRegionWidth();

        float ammoPosX = turr.getX() + turrSize * cos;
        float ammoPosY = turr.getY() + textureTurret.getRegionHeight() / 2 + turrSize * sin;

        float power = (float) Math.min(800, 400 + 0.4 * dt);

        float ammoVelX = power * cos;
        float ammoVelY = power * sin;

        Bullet b = new Bullet(ammoPosX, ammoPosY, ammoVelX, ammoVelY);

        return b;
    }

    public void makeDamage(int dmg) {
        hp -= dmg;
        isHurt = true;
        if (hp <= 0) {
            hp = 0;
        }
    }


    public Vector2 getPosition() {
        return position;
    }

    public boolean isTank(float x, float y) {
        boolean body = x > position.x - 2 && x < position.x - 2 + textureBaseBody.getRegionWidth() && y > position.y + textureBaseTrack.getRegionHeight() / 2 + 5 && y < position.y + textureBaseTrack.getRegionHeight() / 2 + 5 + textureBaseBody.getRegionHeight();
        boolean track = x > position.x && x < position.x + textureBaseTrack.getRegionWidth() && y > position.y && y < position.y + textureBaseTrack.getRegionHeight();

        return body || track;
    }

    public boolean checkOnGround() {
        float bottom = 25;
        for (int i = 25; i < 75; i += 10) {
            if (game.getMap().isGround(position.x + i, position.y + bottom)) {
                return true;
            }
        }
        return false;
    }
}
