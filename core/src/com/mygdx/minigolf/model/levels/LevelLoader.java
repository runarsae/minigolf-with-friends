package com.mygdx.minigolf.model.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.minigolf.util.ComponentMappers.PhysicalMapper;
import com.mygdx.minigolf.controller.EntityFactory;

import java.util.ArrayList;
import java.util.List;


public class LevelLoader {
    EntityFactory factory;

    public LevelLoader(EntityFactory factory) {
        this.factory = factory;
    }

    public Level load(String fileName) {
        return new Level(CourseLoader.load(fileName));
    }

    static public Shape getShape(CourseElement elem) {
        Vector2 middle = new Vector2(elem.width / 2, elem.height / 2);
        switch (elem.shape) {
            case ELLIPSE:
                float radius = elem.width / 2;
                CircleShape circle = new CircleShape();
                circle.setRadius(radius);
                return circle;
            case TRIANGLE:
                PolygonShape triangle = new PolygonShape();
                triangle.set(new Vector2[]{
                        new Vector2(0, 0).rotateAroundDeg(middle, elem.rotation),
                        new Vector2(elem.width, elem.height / 2).rotateAroundDeg(middle, elem.rotation),
                        new Vector2(0, elem.height).rotateAroundDeg(middle, elem.rotation)
                });
                return triangle;
            case RECTANGLE:
                PolygonShape rectangle = new PolygonShape();
                rectangle.set(new Vector2[]{
                        new Vector2(0, 0).rotateAroundDeg(middle, elem.rotation),
                        new Vector2(elem.width, 0).rotateAroundDeg(middle, elem.rotation),
                        new Vector2(elem.width, elem.height).rotateAroundDeg(middle, elem.rotation),
                        new Vector2(0, elem.height).rotateAroundDeg(middle, elem.rotation),
                });
                return rectangle;
            default:
                throw new IllegalArgumentException("Illegal course element shape");
        }
    }

    public Entity createEntity(CourseElement elem) {
        switch (elem.function) {
            case HOLE:
                return factory.createHole(elem.x, elem.y, (CircleShape) getShape(elem));
            case SPAWN:
                return factory.createSpawn(elem.x, elem.y);
            case OBSTACLE:
                return factory.createObstacle(elem.x, elem.y, (PolygonShape) getShape(elem));
            case EXPLODING_POWERUP:
                return factory.createExplodingPowerup(elem.x, elem.y, (CircleShape) getShape(elem));
            case NO_COLLISION_POWERUP:
                return factory.createNoCollisionPowerUp(elem.x, elem.y, (CircleShape) getShape(elem));
            case COURSE:
                return factory.createCourse(elem.x, elem.y, (PolygonShape) getShape(elem));
            case WALL:
                return factory.createWall(elem.x, elem.y, (PolygonShape) getShape(elem));
            default:
                throw new IllegalArgumentException("Illegal course element function");
        }
    }

    public class Level {
        public final List<Entity> elements = new ArrayList<>();
        public final List<Entity> powerups = new ArrayList<>();
        public final List<Entity> holes = new ArrayList<>();
        public final List<Entity> spawns = new ArrayList<>();

        private Level(List<CourseElement> courseElems) {
            courseElems.forEach(ce -> {
                Entity e = createEntity(ce);
                switch (ce.function) {
                    case NO_COLLISION_POWERUP:
                    case EXPLODING_POWERUP:
                        powerups.add(e);
                        break;
                    case HOLE:
                        holes.add(e);
                        break;
                    case SPAWN:
                        spawns.add(e);
                        break;
                }
                elements.add(e);
            });
        }

        public void dispose(Engine engine) {
            elements.forEach(engine::removeEntity);
        }

        public Vector2 getSpawnCenter() {
            return PhysicalMapper.get(this.spawns.get(0)).getPosition();
        }
    }

}
