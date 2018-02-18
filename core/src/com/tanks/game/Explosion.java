package com.tanks.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion implements Renderable{
    float x;
    float y;
    TextureRegion[] explosion;
    int i = -1;
    long time1 = -1;
    long time2 = -1;

    public Explosion(float x, float y) {
        this.x = x;
        this.y = y;
        explosion = new TextureRegion[3];
        explosion[0] = Assets.findTexture("explosion1");
        explosion[1] = Assets.findTexture("explosion2");
        explosion[2] = Assets.findTexture("explosion3");
    }

    public boolean render(SpriteBatch batch) {
        update();
        if (i >= 0 && i < explosion.length) {
            batch.draw(explosion[i], x - explosion[i].getRegionWidth()/2, y - explosion[i].getRegionHeight()/2);
        }
        return i != -2;
    }

    public void update() {
        long t = System.currentTimeMillis();
        if (time1 < 0) {
            time1 = t;
            return;
        }

        time2 = t;
        long dt = time2 - time1;
        int k = 100;
        if (dt < k) {
            i = 0;
        } else if (dt < 2*k) {
            i = 1;
        } else if (dt < 3*k) {
            i = 2;
        } else {
            i = -2;
        }
    }
}
