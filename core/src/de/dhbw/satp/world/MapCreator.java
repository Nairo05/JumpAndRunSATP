package de.dhbw.satp.world;

import static de.dhbw.satp.main.Statics.PPM;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import de.dhbw.satp.main.Statics;

public class MapCreator implements Disposable {

    private final World world;
    private TiledMap map;
    private TmxMapLoader tmxMapLoader;

    public MapCreator(World world) {
        this.world = world;
        tmxMapLoader = new TmxMapLoader();
        map = tmxMapLoader.load("1-1.tmx");

        for (RectangleMapObject mapObject : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = mapObject.getRectangle();

            new Ground(world, rect.x, rect.y, rect.width, rect.height);
        }

        for (PolygonMapObject mapObject : map.getLayers().get(3).getObjects().getByType(PolygonMapObject.class)) {
            PolygonShape polygon = new PolygonShape();

            float[] vertices = mapObject.getPolygon().getTransformedVertices();

            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = vertices[i] / PPM;
            }

            polygon.set(vertices);

            Body body;

            BodyDef block = new BodyDef();
            block.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(block);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygon;
            fixtureDef.filter.categoryBits = BitFilterDef.GROUND_BIT;
            fixtureDef.filter.maskBits = BitFilterDef.PLAYER_BIT | BitFilterDef.OBJECT_BIT | BitFilterDef.ENEMY_BIT;
            body.createFixture(fixtureDef);
        }
    }

    public TiledMap getMap() {
        return map;
    }

    @Override
    public void dispose() {
        map.dispose();
    }
}
