package com.mygdx.minigolf.view;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.minigolf.HeadlessGame;
import com.mygdx.minigolf.controller.ComponentMappers;
import com.mygdx.minigolf.controller.EntityFactory;
import com.mygdx.minigolf.controller.systems.GraphicsSystem;
import com.mygdx.minigolf.controller.systems.Physics;
import com.mygdx.minigolf.model.components.Physical;
import com.mygdx.minigolf.model.levels.CourseLoader;
import com.mygdx.minigolf.model.levels.LevelLoader;

import java.util.List;

public class GameView extends HeadlessGame implements Screen {

    public GameView() {
        super.create();

        GraphicsSystem graphicsSystem = new GraphicsSystem();

        engine.addSystem(graphicsSystem);
        // engine.addSystem(new PhysicsDebugSystem(world, graphicsSystem.getCam()));

        factory = new EntityFactory(engine, world);

        // --- Start dummy demo code ---
        // Test code. Loads a level
        LevelLoader levelLoader = new LevelLoader(factory);
        List<Entity> levelContents = levelLoader.loadLevel(CourseLoader.getCourses().get(1));
        ComponentMappers.PhysicalMapper.get(factory.createPlayer(9, 12)).setVelocity(new Vector2(1, 1));
        ComponentMappers.PhysicalMapper.get(factory.createPlayer(9, 12)).setVelocity(new Vector2(0, 0));
        ComponentMappers.PhysicalMapper.get(factory.createPlayer(9, 12)).setVelocity(new Vector2(0, 0));

        // --- End dummy demo code ---
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
