package com.tanks.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Tank {
    protected Texture textureBaseBody;
    protected Texture textureBaseTrack;
    protected Texture textureTurret;
    protected Texture healthPoint;
    protected Vector2 position;
    protected Vector2 weaponPosition;
    protected TanksGame game;
    protected float turretAngle;
    protected int hp;
    protected int maxHp;
    protected Sprite turr;
    protected Sprite body;
    protected Sprite track;
    protected boolean isDead;

    public Tank(TanksGame game, Vector2 position) {
        this.game = game;
        this.position = position;
        weaponPosition = new Vector2(position).add(0, 0);
        textureBaseBody = new Texture("tankNavy.png");
        textureBaseTrack = new Texture("tankTrack.png");
        textureTurret = new Texture("turret.png");
        healthPoint = new Texture("hbar.png");
        turr = new Sprite(textureTurret);
        turr.setOrigin(0, textureTurret.getHeight() / 2);
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

        body.setPosition(position.x -2, position.y + textureBaseTrack.getHeight() / 2 + 5);
        body.setSize(textureBaseTrack.getWidth() + 4, textureBaseBody.getHeight());
        body.draw(batch);
    }

    public void renderHP(SpriteBatch batch) {
        batch.setColor(0.5f, 0, 0, 0.5f);
        batch.draw(healthPoint, position.x, position.y + textureBaseTrack.getHeight() / 2 + 10 + textureBaseBody.getHeight(), 0, 0, healthPoint.getWidth(), healthPoint.getHeight() / 3);
        batch.setColor(0, 1.0f, 0, 0.5f);
        batch.draw(healthPoint, position.x, position.y + textureBaseTrack.getHeight() / 2 + 10 + textureBaseBody.getHeight(), 0, 0, (int) (healthPoint.getWidth() * (float) hp / maxHp), healthPoint.getHeight() / 3);

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

        float turrSize = textureTurret.getWidth();

        float ammoPosX = turr.getX() + turrSize * cos;
        float ammoPosY = turr.getY() + textureTurret.getHeight() / 2 + turrSize * sin;

        float power = (float) Math.min(800, 400 + 0.4 * dt);

        float ammoVelX = power * cos;
        float ammoVelY = power * sin;

        Bullet b = new Bullet(ammoPosX, ammoPosY, ammoVelX, ammoVelY);

        return b;
    }

    public void makeDamage(int dmg) {
        hp -= dmg;
        if (hp <= 0) {
            hp = 0;
            isDead = true;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isTank(float x, float y) {
        boolean body = x > position.x - 2 && x < position.x - 2 + textureBaseBody.getWidth() && y > position.y + textureBaseTrack.getHeight() / 2 + 5 && y < position.y + textureBaseTrack.getHeight() / 2 + 5 + textureBaseBody.getHeight();
        boolean track = x > position.x && x < position.x + textureBaseTrack.getWidth() && y > position.y && y < position.y + textureBaseTrack.getHeight();

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
