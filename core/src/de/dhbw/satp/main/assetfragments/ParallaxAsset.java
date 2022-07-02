package de.dhbw.satp.main.assetfragments;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import de.dhbw.satp.parallax.ParallaxBackgroundLayer;

public class ParallaxAsset {
    //TODO: Use JSON file and try to preload it

    private final Array<ParallaxBackgroundLayer> layers;

    public ParallaxAsset() {
        layers = new Array<>();
    }

    public void load(String s) {

        try {

            //Android uses a different file system (linked Asset-Folder)
            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                    s = "assets/" + s;
            }

            FileHandle handle = Gdx.files.local(s);
            String text = handle.readString();

            text = text.replaceAll("\r\n",",");
            text = text.replaceAll(" ","");

            //Split
            String[] wordsArray = text.split(",");

            for (int i = 0; i < wordsArray.length; i+=4) {
                String textureString = wordsArray[i].replaceAll("Source=","");
                String yOffset = wordsArray[i+1].replaceAll("yOffset=","");
                String rowCount = wordsArray[i+2].replaceAll("RowCount=","");
                String parallax = wordsArray[i+3].replaceAll("Parallax=","");

                Texture texture = new Texture(textureString);
                ParallaxBackgroundLayer parallaxBackgroundLayer = new ParallaxBackgroundLayer(texture);

                try {
                    parallaxBackgroundLayer.setyOffset(Float.parseFloat(yOffset));
                    parallaxBackgroundLayer.setRowCount(Integer.parseInt(rowCount));
                    parallaxBackgroundLayer.setParallaxFactor(Float.parseFloat(parallax));

                } catch (NumberFormatException e) {
                    e.printStackTrace();

                    //Use Defaults
                    parallaxBackgroundLayer.setyOffset(0);
                    parallaxBackgroundLayer.setRowCount(3);
                    parallaxBackgroundLayer.setParallaxFactor(26f);

                } finally {
                    layers.add(parallaxBackgroundLayer);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSize() {
        System.out.println("Layers" + layers.size);
        return layers.size;
    }

    public Array<ParallaxBackgroundLayer> getPBgLayers() {
        return layers;
    }

}
