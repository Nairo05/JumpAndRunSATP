package de.dhbw.satp.world;

import static de.dhbw.satp.main.Statics.PPM;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import de.dhbw.satp.main.Statics;
import de.dhbw.satp.screens.PlayScreen;

public class MapCreator implements Disposable {

    private final World world;
    private TiledMap map;
    private TmxMapLoader tmxMapLoader;
    private Rectangle playerRectangle;

    public MapCreator(PlayScreen playScreen) {
        this.world = playScreen.getWorld();
        tmxMapLoader = new TmxMapLoader();
        map = tmxMapLoader.load("tmx/1-1.tmx");
        playerRectangle = new Rectangle(3.3f,5f,1,1);

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

        for (RectangleMapObject mapObject : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            if (mapObject.getName().equalsIgnoreCase("Player")) {
                System.out.println(mapObject.getName() + "overriding the Start-Position for Entity Player");
                playerRectangle.x = (mapObject.getRectangle().x + mapObject.getRectangle().width / 2) / PPM;
                playerRectangle.y = (mapObject.getRectangle().y + 16) / PPM;
            }
        }

        for (RectangleMapObject mapObject : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            playScreen.getParticleManager().addParticleEffect(mapObject.getName(), mapObject.getRectangle().x / PPM, mapObject.getRectangle().y / PPM);
        }
    }

    public TiledMap getMap() {
        return map;
    }

    @Override
    public void dispose() {
        map.dispose();
    }

    public Rectangle getPlayerRectangle() {
        return playerRectangle;
    }
}
