package io.github.sandeeplakka.games;

import java.util.HashSet;

//TODO: Colored board printing is still WIP : Complete it
enum COLOR {
    RED("\033[31;1;3m","\033[0m"),
    YELLOW("\033[32;1;3m","\033[0m");

    private final String colorOn;
    private final String colorOff;

    COLOR(String colorOn, String colorOff) {
        this.colorOn = colorOn;
        this.colorOff = colorOff;
    }
}
public enum Team {

    Lucky('O', new HashSet<Integer>(),COLOR.RED),
    Ricky('X', new HashSet<Integer>(),COLOR.YELLOW);

    private final char pin;
    private final HashSet<Integer> pinPositions;
    private final COLOR color;

    Team(char pin, HashSet<Integer> pinPositions, COLOR color) {
        this.pin = pin;
        this.pinPositions = pinPositions;
        this.color = color;
    }

    public char getPin() {
        return pin;
    }

    public HashSet<Integer> getPinPositions() {
        return pinPositions;
    }

    public void addPin(int position){
        pinPositions.add(position);
    }

    public void resetPinLocations(){
        this.pinPositions.clear();
    }

    public COLOR getColor() {
        return color;
    }
}
