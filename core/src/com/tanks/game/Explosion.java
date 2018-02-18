package com.tanks.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Explosion implements Renderable{
    float x;
    float y;
    Texture[] explosion;
    int i = -1;
    long time1 = -1;
    long time2 = -1;

    public Explosion(float x, float y) {
        this.x = x;
        this.y = y;
        explosion = new Texture[3];
        explosion[0] = Textures.EXPLOSION1;
        explosion[1] = Textures.EXPLOSION2;
        explosion[2] = Textures.EXPLOSION3;
    }

    public boolean render(SpriteBatch batch) {
        update();
        if (i >= 0 && i < explosion.length) {
            batch.draw(explosion[i], x - explosion[i].getWidth()/2, y - explosion[i].getHeight()/2);
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
