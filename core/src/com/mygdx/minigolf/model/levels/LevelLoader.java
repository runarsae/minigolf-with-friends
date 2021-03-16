package com.mygdx.minigolf.model.levels;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.minigolf.controller.EntityFactory;
import com.mygdx.minigolf.controller.EntityFactory.EntityType;
import com.mygdx.minigolf.model.components.Physical;

import java.util.ArrayList;
import java.util.List;


public class LevelLoader {

    static public List<Entity> loadLevel(String fileName) {
        return loadLevel(CourseLoader.getCourse(fileName));
    }

    static public List<Entity> loadLevel(Course course) {
        List<Entity> entities = new ArrayList<>();
        for (CourseElement elem : course.getElements()) {
            Entity e = EntityFactory.get().createEntity(getEntityType(elem));

            Physical phys = e.getComponent(Physical.class);
            phys.setPosition(new Vector2(elem.x, elem.y));
            phys.setShape(getShape(elem));

            entities.add(e);
        }
        return entities;
    }

    static private Shape getShape(CourseElement elem) {
        switch (elem.shape) {
            case ELLIPSE:
                return new CircleShape();
            case TRIANGLE:
                PolygonShape shape = new PolygonShape();
                shape.set(new float[]{
                        0, 0,
                        elem.width, 0,
                        elem.width / 2f, elem.height
                });
                return shape;
            case RECTANGLE:
                shape = new PolygonShape();
                shape.set(new float[]{
                        0, 0,
                        elem.width, 0,
                        elem.width, elem.height,
                        0, elem.height
                });
                return shape;
            default:
                throw new IllegalArgumentException("Illegal course element shape");
        }
    }

    static private EntityType getEntityType(CourseElement elem) {
        switch (elem.function) {
            case HOLE:
                return EntityType.HOLE;
            case SPAWN:
                return EntityType.SPAWN;
            default:
                return EntityType.OBSTACLE;
        }
    }

}
