package com.tanks.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;


public class Assets {
    private static final Assets ourInstance = new Assets();

    private AssetManager assetManager;
    private TextureAtlas atlas;

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public static Assets getInstance() {
        return ourInstance;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    private Assets() {
        assetManager = new AssetManager();
        assetManager.load("Game.pack", TextureAtlas.class);
        assetManager.finishLoading();
        atlas = assetManager.get("Game.pack", TextureAtlas.class);
    }

    public static TextureRegion findTexture(String name) {
        return getInstance().getAtlas().findRegion(name);
    }

    public void clear() {
        assetManager.clear();
    }

    public void dispose() {
        assetManager.dispose();
    }
}