package de.dhbw.satp.main.assetfragments;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class ParallaxFragmentAssetLoader extends SynchronousAssetLoader<ParallaxAsset, ParallaxFragmentAssetLoader.ParallaxParameter> {

    public ParallaxFragmentAssetLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public ParallaxAsset load(AssetManager assetManager, String fileName, FileHandle file, ParallaxParameter parameter) {
        ParallaxAsset parallaxAsset = new ParallaxAsset();

        System.out.println("loading CustomAsset ParallaxAsset (ParallaxConfiguration.java)");
        parallaxAsset.load(fileName);

        return parallaxAsset;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, ParallaxParameter parameter) {
        return null;
    }

    static public class ParallaxParameter extends AssetLoaderParameters<ParallaxAsset> {
        public ParallaxParameter () {
        }
    }
}
