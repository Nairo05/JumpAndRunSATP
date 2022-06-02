package de.dhbw.satp.world;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class MapCreator implements Disposable {

    private final World world;
    private Map map;
    private TmxMapLoader tmxMapLoader;

    public MapCreator(World world) {
        this.world = world;
        tmxMapLoader = new TmxMapLoader();
        map = tmxMapLoader.load("unbenannt.tmx");

        for (RectangleMapObject mapObject : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = mapObject.getRectangle();

            new Ground(world, rect.x, rect.y, rect.width);
        }
    }

    @Override
    public void dispose() {
        map.dispose();
    }
}
