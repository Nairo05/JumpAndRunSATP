package de.dhbw.satp.main.assetfragments;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class ShaderFragmentAssetLoader extends SynchronousAssetLoader<ShaderAsset, ShaderFragmentAssetLoader.ShaderParameter> {

    public ShaderFragmentAssetLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public ShaderAsset load(AssetManager assetManager, String fileName, FileHandle file, ShaderParameter parameter) {

        ShaderAsset shaderAsset = new ShaderAsset();
        shaderAsset.initialize();
        System.out.println("loading CustomAsset ShaderAsset (ShaderProgramm.java)");
        shaderAsset.setShaderProgram(fileName);
        if (shaderAsset.getShaderProgram().isCompiled()) {
            System.out.println("- loaded Shader " + fileName);
            return shaderAsset;
        } else {
            shaderAsset.printErrors();
            System.out.println("- cant load Shader " + fileName + " using default instead, Stacktrace: ");
            return shaderAsset.getDefault();
        }
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, ShaderParameter parameter) {
        return null;
    }

    static public class ShaderParameter extends AssetLoaderParameters<ShaderAsset> {
        public ShaderParameter () {
        }
    }
}
