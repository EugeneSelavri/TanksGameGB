package com.tanks.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bullet implements Renderable {
    private TextureRegion bullet;
    private Vector2 position;
    private Vector2 velocity;

    private boolean active = true;
    private float angle = 0f;

    public Bullet(float x, float y, float vx, float vy) {
        bullet = Assets.getInstance().getAtlas().findRegion("ammo");
        position = new Vector2(x,y);
        velocity = new Vector2(vx, vy);
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    @Override
    public boolean render(SpriteBatch batch) {
        if (active) {
            batch.draw(bullet, position.x - bullet.getRegionWidth() / 2, position.y - bullet.getRegionHeight() / 2);

            update();
        }

        return active;
    }

    private void update() {
        float dt = Gdx.graphics.getDeltaTime();

        velocity.y -= GameScreen.GLOBAL_GRAVITY * dt;
        angle = velocity.angle();
        position.mulAdd(velocity, dt);
    }

    public Vector2 getCenter() {
        float x = position.x - bullet.getRegionWidth() / 2;
        float y = position.y - bullet.getRegionHeight() / 2;
        Vector2 center = new Vector2(x, y);
        return center;

    }
}
