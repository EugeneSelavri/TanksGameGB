package com.tanks.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;

public class RenderableEmitter<T extends Renderable> implements Iterable<T> {
    private ArrayList<T> arrayList = new ArrayList<>();

    public void render(SpriteBatch batch) {
        ArrayList<T> newArrayList = new ArrayList<>();
        for (T e : arrayList) {
            if (e.render(batch)) {
                newArrayList.add(e);
            }
        }
        arrayList = newArrayList;
    }

    public void addNew(T t) {
        arrayList.add(t);
    }

    @Override
    public Iterator<T> iterator() {
        return arrayList.iterator();
    }
}
