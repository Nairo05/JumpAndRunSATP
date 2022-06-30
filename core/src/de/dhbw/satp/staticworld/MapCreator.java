package de.dhbw.satp.staticworld;

import static de.dhbw.satp.main.FinalStatics.PPM;

import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import de.dhbw.satp.gameobjects.Enemy;
import de.dhbw.satp.gameobjects.EnemySpike;
import de.dhbw.satp.gameobjects.EnemyStandard;
import de.dhbw.satp.gameobjects.ToSpawnObjectDefinition;
import de.dhbw.satp.main.NotFinalStatics;
import de.dhbw.satp.screens.PlayScreen;

public class MapCreator implements Disposable {
    //TODO: Fix PolygonShape handling

    private final World world;
    private TiledMap map;
    private TmxMapLoader tmxMapLoader;
    private Rectangle playerRectangle;

    public MapCreator(PlayScreen playScreen) {
        this.world = playScreen.getWorld();
        //TODO: move in level-loading screen
        tmxMapLoader = new TmxMapLoader();
        try {
            map = tmxMapLoader.load(NotFinalStatics.levelPath);
        } catch (Exception e) {
            System.err.println("Warning: File not found - loading default level: 1-1");
            map = tmxMapLoader.load("tmx/1-1.tmx");
        }

        playerRectangle = new Rectangle(3.3f,5f,1,1);

        //HitBox-Layer
        //Rectangle-Shapes
        for (RectangleMapObject mapObject : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = mapObject.getRectangle();

            new Ground(world, rect.x, rect.y, rect.width, rect.height);
        }

        //HitBox-Layer
        //Polygon-Shapes
        for (PolygonMapObject mapObject : map.getLayers().get(3).getObjects().getByType(PolygonMapObject.class)) {

            float[] vertices = mapObject.getPolygon().getTransformedVertices();

            new Ground(world, vertices);

        }

        //Entity-Layer
        for (RectangleMapObject mapObject : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {

            if (mapObject.getName().equalsIgnoreCase("Player")) {
                System.out.println(mapObject.getName() + "overriding the Start-Position of Entity Player");
                playerRectangle.x = (mapObject.getRectangle().x + mapObject.getRectangle().width / 2) / PPM;
                playerRectangle.y = (mapObject.getRectangle().y + 16) / PPM;

            } else if (mapObject.getName().equalsIgnoreCase("Enemy1")) {
                System.out.println("Found an enemy1 " + (mapObject.getRectangle().x + mapObject.getRectangle().width / 2) / PPM + " - " + (mapObject.getRectangle().y + 16) / PPM + " with width of: " + mapObject.getRectangle().width);
                playScreen.getEntityManager().spawnDynamicEntity(new ToSpawnObjectDefinition<>(EnemyStandard.class, (mapObject.getRectangle().x + mapObject.getRectangle().width / 2), (mapObject.getRectangle().y + 16), mapObject.getRectangle().width));
            } else if (mapObject.getName().equalsIgnoreCase("Enemy2")) {
                System.out.println("Found an enemy2 " + (mapObject.getRectangle().x + mapObject.getRectangle().width / 2) / PPM + " - " + (mapObject.getRectangle().y + 16) / PPM + " with width of: " + mapObject.getRectangle().width);
                playScreen.getEntityManager().spawnDynamicEntity(new ToSpawnObjectDefinition<>(EnemySpike.class, (mapObject.getRectangle().x + mapObject.getRectangle().width / 2), (mapObject.getRectangle().y + 16), mapObject.getRectangle().width));
            }
        }

        //Particle-Layer
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
