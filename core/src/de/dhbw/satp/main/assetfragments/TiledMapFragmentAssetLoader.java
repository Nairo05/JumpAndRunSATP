package de.dhbw.satp.main.assetfragments;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

public class TiledMapFragmentAssetLoader extends SynchronousAssetLoader<TiledMap, TiledMapFragmentAssetLoader.TiledMapParameter> {

    public TiledMapFragmentAssetLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public TiledMap load(AssetManager assetManager, String fileName, FileHandle file, TiledMapParameter parameter) {

        TmxMapLoader tmxMapLoader = new TmxMapLoader();

        TiledMap tiledMap;

        try {
            tiledMap = tmxMapLoader.load(fileName);
        } catch (Exception e) {
            System.err.println("Warning: File not found - loading default level: 1-1");
            tiledMap = tmxMapLoader.load("tmx/1-1.tmx");
        }

        return tiledMap;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, TiledMapParameter parameter) {
        return null;
    }

    static public class TiledMapParameter extends AssetLoaderParameters<TiledMap> {
        public TiledMapParameter () {
        }
    }

}
