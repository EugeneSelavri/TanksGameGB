package com.tanks.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public final class BotTank extends Tank {
    private Tank playerTank;
    private int flagTurr = 1;
    private int flagMove = -1;
    RenderableEmitter<Bullet> botBulletEmitter = new RenderableEmitter<>();

    public RenderableEmitter<Bullet> getBotBulletEmitter() {
        return botBulletEmitter;
    }

    @Override
    public Bullet makeBullet(long dt) {
        float cos = (float) Math.cos(Math.toRadians(turretAngle));
        float sin = (float) Math.sin(Math.toRadians(turretAngle));

        float turrSize = textureTurret.getRegionWidth();

        float ammoPosX = turr.getX() + turr.getWidth()/2 - turrSize/2 * cos;
        float ammoPosY = turr.getY() + turr.getHeight()/2 - turrSize * sin;

        float k = position.x / (Map.getFullWidth()/2);

        float power = (float) Math.min(800, 400 * k);

        float ammoVelX = power * cos * -1;
        float ammoVelY = power * sin * -1;
        Bullet b = new Bullet(ammoPosX, ammoPosY, ammoVelX, ammoVelY);

        return b;
    }

    public BotTank(GameScreen game, Vector2 position, Tank tank) {
        super(game, position);
        playerTank = tank;
        body.flip(true, false);
        turr.flip(true, false);
        turr.setOrigin(textureTurret.getRegionWidth(), textureTurret.getRegionHeight()/2);
    }

    @Override
    public void render(SpriteBatch batch) {
        turr.setX((float) (position.x - textureBaseBody.getRegionWidth() * 0.19));
        turr.setY((float) (position.y + textureBaseTrack.getRegionHeight() + textureBaseBody.getRegionHeight() * 0.2));
        turr.draw(batch);
        super.render(batch);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        moveTurret(flagTurr);
        int random = 60 + (int)(Math.random() * 60);
        if(turretAngle > 0) {
            Bullet b = makeBullet(0);
            botBulletEmitter.addNew(b);
            flagTurr = -1;
        }

        if (turretAngle < random * -1) {
            Bullet b = makeBullet(0);
            botBulletEmitter.addNew(b);
            flagTurr = 1;
        }

        moveTank(flagMove);

        if (position.x < Map.getFullWidth()/2 + textureBaseTrack.getRegionWidth() && flagMove == -1) {
            flagMove = 1;
        }
        if (position.x > Map.getFullWidth() - textureBaseTrack.getRegionWidth() && flagMove == 1) {
            flagMove = -1;
        }

    }


}
