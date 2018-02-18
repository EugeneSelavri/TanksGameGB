package com.tanks.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyTank extends Tank {
    public MyTank(TanksGame game, Vector2 position) {
        super(game, position);
    }

    @Override
    public void render(SpriteBatch batch) {
        turr.setX((float) (position.x + textureBaseBody.getWidth() * 0.8));
        turr.setY((float) (position.y + textureBaseTrack.getHeight() + textureBaseBody.getHeight() * 0.2));
        turr.draw(batch);
        super.render(batch);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveTurret(1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveTurret(-1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveTank(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveTank(-1);
        }
    }
}