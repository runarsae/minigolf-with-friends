package com.mygdx.minigolf.model.components;

import com.badlogic.ashley.core.Component;
import com.mygdx.minigolf.model.powerup.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Player implements Component {
    public final String name;
    private boolean completed = false;
    private int totalStrokes = 0;
    private int levelStrokes = 0;
    private List<Effect> effects = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean b) {
        completed = b;
    }

    public void resetStrokes() {
        totalStrokes += levelStrokes;
        levelStrokes = 0;
    }

    public void setStrokes(int strokes) {
        this.levelStrokes = strokes;
    }

    public int getStrokes() {
        return totalStrokes + levelStrokes;
    }

    public int getLevelStrokes() {
        return levelStrokes;
    }

    public void incrementStrokes() {
        this.levelStrokes += 1;
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public List<Effect> getEffects() {
        return this.effects;
    }

    public void removeExhaustedEffects() {
        this.effects = effects.stream().filter(effect -> effect.getConstraint().powerExhausted(this.levelStrokes)).collect(Collectors.toList());
    }

    public void removeEffect(Effect effect) {
        this.effects.remove(effects.indexOf(effect));
    }
}
