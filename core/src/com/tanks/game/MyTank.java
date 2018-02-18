package com.tanks.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyTank extends Tank {
    public MyTank(GameScreen game, Vector2 position) {
        super(game, position);
    }

    @Override
    public void render(SpriteBatch batch) {
        turr.setX((float) (position.x + textureBaseBody.getRegionWidth() * 0.8));
        turr.setY((float) (position.y + textureBaseTrack.getRegionHeight() + textureBaseBody.getRegionHeight() * 0.2));
        turr.draw(batch);
        super.render(batch);
    }

    public void setExternalMoveTurret(int externalMoveTurret) {
        this.externalMoveTurret = externalMoveTurret;
    }

    private int externalMoveTurret = 0;

    private int externalMoveTank = 0;

    public void setExternalMoveTank(int externalMoveTank) {
        this.externalMoveTank = externalMoveTank;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || externalMoveTurret == 1) {
            moveTurret(1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || externalMoveTurret == -1) {
            moveTurret(-1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) || externalMoveTank == 1) {
            moveTank(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || externalMoveTank == -1) {
            moveTank(-1);
        }
    }
}
