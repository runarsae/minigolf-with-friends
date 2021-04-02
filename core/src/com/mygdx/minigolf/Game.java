package com.mygdx.minigolf;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.minigolf.controller.ComponentMappers.PhysicalMapper;
import com.mygdx.minigolf.controller.systems.GraphicsSystem;
import com.mygdx.minigolf.model.levels.CourseLoader;

import java.util.List;

public class Game extends HeadlessGame {

    @Override
    public void create() {
        super.create();

        engine.addSystem(new GraphicsSystem());

        // Test code. Loads a level
        List<Entity> levelContents = levelLoader.loadLevel(CourseLoader.getCourses().get(0));
        Entity player = factory.createPlayer(9, 12, false);
        PhysicalMapper.get(player).setVelocity(new Vector2(1, 1));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.0f, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(Gdx.graphics.getDeltaTime()); // TODO: Move stuff to GameView
    }

    @Override
    public void dispose() {
    }
}
