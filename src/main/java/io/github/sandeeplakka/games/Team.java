package io.github.sandeeplakka.games;

import java.util.ArrayList;
import java.util.List;

public enum Team {
    TeamA('X', new ArrayList<Integer>()),
    TeamB('O', new ArrayList<Integer>());

    private final char pin;
    private final List<Integer> pinPositions;

    Team(char pin, List<Integer> pinPositions) {
        this.pin = pin;
        this.pinPositions = pinPositions;
    }

    public char getPin() {
        return pin;
    }

    public List<Integer> getPinPositions() {
        return pinPositions;
    }

    public void addPin(int position){
        pinPositions.add(position);
    }

    public void resetPinLocations(){
        this.pinPositions.clear();
    }

}
