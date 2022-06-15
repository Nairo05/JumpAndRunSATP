package de.dhbw.satp.parallax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class ParallaxConfiguration {

    private Array<ParallaxBackgroundLayer> layers = new Array<>();

    public ParallaxConfiguration() {

    }

    public void load(String s) {
        try {
            FileHandle handle = Gdx.files.local("filename.txt");
            String text = handle.readString();

            //in CSV
            //TODO in 1 repalceAll call
            text = text.replaceAll("\r", "");
            text = text.replaceAll("\n", "");
            text = text.replaceAll(" ","");
            text = text.replaceAll("Source=","");
            text = text.replaceAll("yOffset=","");
            text = text.replaceAll("RowCount=","");
            text = text.replaceAll("Parallax=","");

            //Split
            String[] wordsArray = text.split(",");

            //Parse
            //ParallaxBackgroundLayer parallaxBackgroundLayer = new ParallaxBackgroundLayer();
            Texture texture;

            for(int i = 0; i < wordsArray.length; i ++) {

                System.out.println(wordsArray[i]);

                if ((i % 4) == 0) {
                    texture = new Texture(wordsArray[i]);

                    //parallaxBackgroundLayer.setBg(texture);

                    System.out.println("parses Texture");
                    texture.dispose();
                }
                if ((i % 4) == 1) {
                    //parallaxBackgroundLayer.setyOffset(Float.parseFloat(wordsArray[i]));

                    System.out.println("parsed Offset");
                }
                if ((i % 4) == 2) {
                    //parallaxBackgroundLayer.setDisplayCountInLine(Float.parseFloat(wordsArray[i]));

                    System.out.println("parsed Count");
                }
                if ((i % 4) == 3) {
                    //parallaxBackgroundLayer.setParallaxFactor(Float.parseFloat(wordsArray[i]));
                    //parallaxBackgroundLayer.finish();

                    //layers.add(parallaxBackgroundLayer);

                    System.out.println("parsed Factor ++");
                }

            }

        } catch (Exception e) {
            System.out.println("Could not load Configuration, using defaults");
            e.printStackTrace();
        }
    }

    public int getSize() {
        System.out.println("Layers" + layers.size);
        return layers.size;
    }

    public Array<ParallaxBackgroundLayer> getPLayers() {
        return layers;
    }

    public float getBG_ROW_COUNT() {
        return 3;
    }

}
