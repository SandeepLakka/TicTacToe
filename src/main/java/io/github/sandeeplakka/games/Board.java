package io.github.sandeeplakka.games;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board {

    private final int SIZE;
    char[][] ticTacToeBoard;
    private final char empty = ' ';

    private final int[][] winPositions = {
            //horizontal
            {0,1,2}, {3,4,5}, {6,7,8},
            //vertical
            {0,3,6}, {1,4,7}, {2,5,8},
            //diagonal
            {0,4,8}, {2,4,6}
    };

    public Board(){
        this(3);
    }
    public Board(int size) {
        SIZE = size;
        ticTacToeBoard = new char[SIZE][SIZE];
        initializeCleanBoard();
        Arrays.stream(Team.values()).forEach(Team::resetPinLocations);
    }

    public void initializeCleanBoard(){
        for(int i = 0; i < SIZE ; i++){
            for(int j = 0; j < SIZE ; j++){
                ticTacToeBoard[i][j] = empty;
            }
        }
    }
    public void printBoard(){
        System.out.println();
        for(int i = 0; i < SIZE ; i++){
            System.out.println(i == 0 ? ".---.---.---." : "");
            for(int j = 0; j < SIZE ; j++){
                System.out.print((j == 0 ? "| " : " ") + ticTacToeBoard[i][j]+ ( j < SIZE ? " |" : " "));
            }
            System.out.print(i == SIZE -1 ? "\n`---`---`---`" : "\n:---:---:---:");
        }
        System.out.println();
    }
    public void setOnBoard(Team team, int horizontal, int vertical){
        team.addPin(horizontal*SIZE+vertical);
        ticTacToeBoard[horizontal][vertical] = team.getPin();
        System.out.println("Board now is ");
        printBoard();
    }
    public boolean isPinAlreadySet(int horizontal, int vertical){
        return ticTacToeBoard[horizontal][vertical] != empty;
    }

    public Optional<Team> checkIfWon() {
        Team wonTeam = null;
/*        List<String> winList = Arrays.stream(winPositions).map(ints -> {
            StringBuilder sb = new StringBuilder();
            Arrays.stream(ints).forEach(operand -> sb.append(operand+""));
            return sb.toString();
        }).collect(Collectors.toList());*/
        //System.out.println("winList : "+winList);
        for(Team team : Team.values()){

            String positionString = team.getPinPositions().stream()
                    .map(integer -> Integer.toString(integer))
                    .collect(Collectors.joining());

            for(int[] winPosition : winPositions){
                int[] chars = winPosition;
                boolean isWon = true;
                for(int c : chars){
                    if(positionString.indexOf(Integer.toString(c)) == -1) {
                        isWon = false;
                    }
                }
                if(isWon) {
                    System.out.println(team.name()+" Won!!");
                    wonTeam = team;
                }
            }

        }
        return wonTeam == null ? Optional.empty() : Optional.of(wonTeam);
        /*
        //TeamA--------------------------------------------------------------
        List<Integer> teamAPositions = Team.TeamA.getPinPositions();
        //System.out.println("teamApositions : "+teamAPositions);
        teamAPositions.sort(Integer::compareTo);
        String teamAString = teamAPositions.stream().map(
                value -> String.valueOf(value)
        ).collect(Collectors.joining());
        //System.out.println("teamAstring : "+teamAString);


        boolean isTeamAReallyWon = false;
        for(String winPosition : winList){
            Boolean[] isTeamAWon = new Boolean[winPosition.length()];
            isTeamAReallyWon = false;
            char[] chars = winPosition.toCharArray();
            //System.out.println("chars is "+Arrays.toString(chars));
            for(int i = 0; i < chars.length ; i++){
                //System.out.println("checking for "+Character.toString(c)+" in string "+teamAString);
                if(teamAString.indexOf(chars[i]) == -1){
                    isTeamAWon[i] = true;
                }
            }
            isTeamAReallyWon = teamAString.length() >=3 && !(Arrays.stream(isTeamAWon).anyMatch(aBoolean -> aBoolean != null && !aBoolean.booleanValue()));
            if (isTeamAReallyWon) break;
        }
        if(isTeamAReallyWon){
            System.out.println("TeamA won!!!!");
            return Team.TeamA;
        }
        //TeamB--------------------------------------------------------------
        List<Integer> teamBPositions = Team.TeamB.getPinPositions();
        //System.out.println("team B positions : "+teamBPositions);
        teamBPositions.sort(Integer::compareTo);
        String teamBString = teamBPositions.stream().map(
                value -> String.valueOf(value)
        ).collect(Collectors.joining());
        //System.out.println("teamBString  : "+ teamBString);
        boolean isTeamBReallyWon = false;
        for(String winPosition : winList) {
            Boolean[] isTeamBWon = new Boolean[winPosition.length()];
            isTeamBReallyWon = false;
            char[] chars = winPosition.toCharArray();
            //System.out.println("chars is "+Arrays.toString(chars));
            for (int i = 0; i < chars.length; i++) {
                //System.out.println("checking for "+Character.toString(c)+" in string "+teamBString);
                if (teamBString.indexOf(chars[i]) != -1) {
                    isTeamBWon[i] = true;
                }
            }
            isTeamBReallyWon = teamBString.length() >= 3 && !Arrays.stream(isTeamBWon).anyMatch(aBoolean -> aBoolean != null && !aBoolean.booleanValue());
            if (isTeamBReallyWon) break;
        }
        if(isTeamBReallyWon){
            System.out.println("TeamB won!!!!");
            return Team.TeamB;
        }
        return Team.TeamNA;*/
    }

    public boolean isFull() {
        boolean isFull = true;
        for(int i = 0; i < SIZE ; i++){
            for(int j = 0; j < SIZE; j++){
                if(ticTacToeBoard[i][j] == empty) {
                    isFull = false;
                }
            }
        }
        return isFull;
    }

    public boolean isPinNotWithinBounds(int horizontalIndex, int verticalIndex){
        return (isNotWithinBounds(horizontalIndex) || isNotWithinBounds(verticalIndex));
    }
    private boolean isNotWithinBounds(int index) {
        return index < 0 || index >= SIZE;
    }

    public static int getVerticalIndex(String input) {
        return Integer.parseInt(input.split(",")[1]) - 1;
    }

    public static int getHorizontalIndex(String input) {
        return Integer.parseInt(input.split(",")[0]) - 1;
    }

    public int getSize() {
        return SIZE;
    }
}
