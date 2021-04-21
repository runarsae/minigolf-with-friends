package com.mygdx.minigolf.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.minigolf.Game;
import com.mygdx.minigolf.controller.ScreenController;
import com.mygdx.minigolf.model.GameData;
import com.mygdx.minigolf.util.ConcurrencyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LobbyView extends View {

    public static final int MAX_NUM_PLAYERS = 4;
    private List<Label> playerLabels = new ArrayList<>();
    Label lobbyIDLabel;

    public LobbyView(GameData.Observable... observables) {
        super(observables);

        // Creating actors
        Label title = new Label("New Game", skin);
        lobbyIDLabel = new Label("LOBBY ID", skin);
        Label players = new Label("Players", skin);
        TextButton start = new TextButton("Start", skin);

        // Transform actors
        title.setFontScale(3f);
        title.setOrigin(Align.center);
        lobbyIDLabel.setFontScale(2f);
        lobbyIDLabel.setOrigin(Align.center);
        players.setFontScale(2f);
        players.setOrigin(Align.center);
        start.setTransform(true);
        start.scaleBy(1f);
        start.setOrigin(Align.center);

        // Add actors to table
        table.add(title).expandX();
        table.row().pad(20f, 0, 40f, 0);
        table.add(lobbyIDLabel).expandX();
        table.row().pad(10f, 0, 40f, 0);
        table.add(players).expandX();

        for (int i = 0; i < MAX_NUM_PLAYERS; i++) {
            Label player = new Label("Player [empty]", skin);
            table.row().pad(10f, 0, 10f, 0);
            table.add(player).expandX();
            this.playerLabels.add(player);
        }

        table.row().pad(50f, 0, 0, 0);
        table.add(start).expandX();

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    Game.getInstance().gameController.startGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void enterGame() {
        ConcurrencyUtils.waitForPostRunnable(() -> {
            ScreenController.GAME_VIEW.create();
            ScreenController.changeScreen(ScreenController.GAME_VIEW);
        });
    }

    @Override
    public void notify(Object change, GameData.Event changeEvent) {
        System.out.println("change = " + change + ", changeEvent = " + changeEvent);
        switch (changeEvent) {
            case LOBBY_ID_SET:
                Integer lobbyID = (Integer) change;
                lobbyIDLabel.setText(lobbyID.toString());
                break;
            case PLAYER_NAMES_SET:
                List<String> playerNames = (List<String>) change;
                for (int i = 0; i < playerNames.size(); i++) {
                    playerLabels.get(i).setText(playerNames.get(i));
                }
                break;
        }
    }
}
