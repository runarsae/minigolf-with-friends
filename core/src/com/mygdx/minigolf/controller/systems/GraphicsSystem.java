package com.mygdx.minigolf.controller.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonRegionLoader;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.minigolf.controller.ComponentMappers.GraphicalMapper;
import com.mygdx.minigolf.controller.ComponentMappers.PhysicalMapper;
import com.mygdx.minigolf.controller.LayerComparator;
import com.mygdx.minigolf.model.components.Graphical;
import com.mygdx.minigolf.model.components.Physical;

import java.util.Comparator;

public class GraphicsSystem extends SortedIteratingSystem {

    // Number of pixels per meter
    public static final float PPM = 32.0f;

    // Height and width of camera frustum, based off width and height of the screen and pixel per meter ratio
    private static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth() / PPM;
    public static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight() / PPM;

    private final OrthographicCamera cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);

    private final Array<Entity> renderQueue = new Array<>(); // A sorted array of entities based on level
    private final Comparator<Entity> comparator = new com.mygdx.minigolf.controller.LayerComparator(); // A comparator to sort entities based on their level

    // A shape renderer used for testing purpose (not using textures)
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public GraphicsSystem() {
        super(Family.all(Physical.class, Graphical.class).get(), new LayerComparator());

        cam.position.set(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f, 0);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        // Sort the renderQueue based on layer
        renderQueue.sort(comparator);

        cam.update();

        // Render shapes
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();

        for (Entity entity : renderQueue) {
            Physical physical = PhysicalMapper.get(entity);
            shapeRenderer.setColor(GraphicalMapper.get(entity).color);
            switch (physical.getShape().getType()) {
                case Circle:
                    shapeRenderer.set(ShapeType.Filled);
                    shapeRenderer.circle(physical.getPosition().x, physical.getPosition().y, physical.getShape().getRadius(), 50);
                    break;
                case Polygon:
                    PolygonShape polygonShape = (PolygonShape) physical.getShape();
                    float[] vertices = new float[2 * polygonShape.getVertexCount()];
                    Vector2 v = new Vector2(), position = physical.getPosition();
                    for (int i = 0; i < polygonShape.getVertexCount(); i++) {
                        polygonShape.getVertex(i, v);
                        v.add(position);
                        vertices[2*i] = v.x;
                        vertices[2*i + 1] = v.y;
                    }
                    shapeRenderer.polygon(vertices);
                    break;
            }
        }

        shapeRenderer.end();
        renderQueue.clear(); // why?
    }

    @Override
    public void processEntity(Entity entity, float dt) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCam() {
        return cam;
    }
}