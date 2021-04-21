package com.mygdx.minigolf.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.minigolf.Game;
import com.mygdx.minigolf.controller.ScreenController;
import com.mygdx.minigolf.controller.Screens;

import java.io.IOException;
import java.util.Arrays;

public class MainMenuView extends View {

    public MainMenuView() {
        super();

        // Creating elements
        TextButton newGame = new TextButton("New Game", skin);
        newGame.addListener(new Screens.ChangeViewListener(Screens.LOBBY_VIEW));

        TextButton joinGame = new TextButton("Join Game", skin);
        joinGame.addListener(new Screens.ChangeViewListener(Screens.JOIN_GAME_VIEW));

        TextButton settings = new TextButton("Settings", skin);
        settings.addListener(new Screens.ChangeViewListener(Screens.SETTINGS_VIEW));

        TextButton tutorial = new TextButton("Tutorial", skin);
        tutorial.addListener(new Screens.ChangeViewListener(Screens.TUTORIAL_VIEW));

        // Transform actors
        for (TextButton btn : Arrays.asList(newGame, joinGame, settings, tutorial)) {
            btn.setTransform(true);
            btn.scaleBy(1f);
            btn.setOrigin(Align.center);
        }

        // Add actors to table
        table.add(newGame).expand();
        table.row().pad(30, 0, 30, 0).expand();
        table.add(joinGame).expand();
        table.row().expand();
        table.add(settings).expand();
        table.row().pad(30, 0, 30, 0).expand();
        table.add(tutorial).expand();

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try { // TODO: Find or create a better way to access the game controller
                    Game.getInstance().gameController.createLobby();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
