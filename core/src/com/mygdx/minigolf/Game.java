package com.mygdx.minigolf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.minigolf.controller.GameController;
import com.mygdx.minigolf.controller.ScreenController;
import com.mygdx.minigolf.controller.Screens;

import java.io.IOException;


public class Game extends com.badlogic.gdx.Game {
    private static Game instance;
    public Music music;
    public GameController gameController;

    public static Game getInstance() {
        return instance;
    }

    @Override
    public void create() {
        instance = this;

        music = Gdx.audio.newMusic(Gdx.files.internal("music/Maxime Abbey - Operation Stealth - The Ballad of J. & J.ogg"));
        music.setLooping(true);
        ScreenController.get().changeScreen(Screens.MAIN_MENU_VIEW);

        try {
            gameController = new GameController(Screens.GAME_VIEW);
        } catch (IOException e) {
            // No use continuing without a game controller. Let it crash.
            throw new RuntimeException(e);
        }
    }
}