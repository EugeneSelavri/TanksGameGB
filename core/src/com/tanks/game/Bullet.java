package com.tanks.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet implements Renderable {
    private Texture texture = Textures.BULLET;
    private Vector2 position;
    private Vector2 velocity;

    private boolean active = true;
    private float angle = 0f;

    public Bullet(float x, float y, float vx, float vy) {
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
            batch.draw(texture, position.x - texture.getWidth() / 2, position.y - texture.getHeight() / 2);

            update();
        }

        return active;
    }

    private void update() {
        float dt = Gdx.graphics.getDeltaTime();

        velocity.y -= TanksGame.GLOBAL_GRAVITY * dt;
        angle = velocity.angle();
        position.mulAdd(velocity, dt);
    }

    public Vector2 getCenter() {
        float x = position.x - texture.getWidth() / 2;
        float y = position.y - texture.getHeight() / 2;
        Vector2 center = new Vector2(x, y);
        return center;

    }
}
