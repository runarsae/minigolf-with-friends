package com.mygdx.minigolf.model.powerup;

public class UseConstraint implements Constraint{

    private int uses;

    public UseConstraint(int uses) {
        setConstrainingInt(uses);
    }

    public void decrementUse(){
        this.uses -= 1;
    }

    @Override
    public boolean powerExhausted(int... compareValue) {
        return uses <= 0;
    }

    @Override
    public void setConstrainingInt(int constrainingInt) {
        this.uses = constrainingInt;
    }

    @Override
    public String toString(){
        return "UseConstraint (" + uses + " remaining uses)";
    }
}
