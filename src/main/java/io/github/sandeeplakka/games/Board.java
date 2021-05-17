package io.github.sandeeplakka.games;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private final int SIZE;
    Character[][] ticTacToeBoard;
    private final char empty = ' ';

    private Set<Set<Integer>> winningPositions  = new HashSet<>();

    public Board(){
        this(3);
    }

    public Board(int size) {
        SIZE = size;
        ticTacToeBoard = new Character[SIZE][SIZE];
        winningPositions = populateWinningPositions();
        initializeCleanBoard();
    }

    public void initializeCleanBoard(){
        for(int i = 0; i < SIZE ; i++){
            for(int j = 0; j < SIZE ; j++){
                ticTacToeBoard[i][j] = empty;
            }
        }
    }
    public void printBoard(){

        String topLeftCorner = "\u250F";
        String topRightCorner = "\u2513";
        String topTBar = "\u2533";
        String sleepingBar = "\u2501\u2501\u2501";
        String standingBar = "\u2503";
        String leftTBar = "\u2523";
        String rightTBar = "\u252B";
        String midPlus = "\u254B";
        String bottomLeftCorner = "\u2517";
        String bottomRightCorner = "\u251B";
        String bottomTBar = "\u253B";
        String topRow = topLeftCorner + IntStream.range(0,SIZE).mapToObj(value -> sleepingBar).collect(Collectors.joining(topTBar)) + topRightCorner +"\n";
        String valRow = IntStream.range(0,SIZE).mapToObj(value -> standingBar + " %s ").collect(Collectors.joining()) + standingBar +"\n";
        String delimRow = leftTBar + IntStream.range(0,SIZE).mapToObj(value -> sleepingBar).collect(Collectors.joining(midPlus)) + rightTBar+"\n";
        String endRow = bottomLeftCorner + IntStream.range(0,SIZE).mapToObj(value -> sleepingBar).collect(Collectors.joining(bottomTBar)) + bottomRightCorner +"\n";

        System.out.println("Board is ");

        System.out.print(topRow);

        for(int i = 0; i < ticTacToeBoard.length ; i++){
            Character[] row = ticTacToeBoard[i];
            System.out.print(String.format(valRow, Arrays.stream(row).toArray()));
            if(i != ticTacToeBoard.length -1) System.out.print(delimRow); // dirty hack
        }
        System.out.println(endRow);
    }
    public void setOnBoard(Team team, int horizontal, int vertical){
        team.addPin(horizontal*SIZE+vertical);
        ticTacToeBoard[horizontal][vertical] = team.getPin();
        printBoard();
    }
    public boolean isPinAlreadySet(int horizontal, int vertical){
        return ticTacToeBoard[horizontal][vertical] != empty;
    }

    public Optional<Team> checkIfWon() {

        for(Team team : Team.values()){
            //System.out.println(" checking "+team.name()+" with positions : "+team.getPinPositions());
            //System.out.println("winningPositions : "+winningPositions);
            for(Set winningPosition : winningPositions){
                //System.out.println("checking if it contains : "+winningPosition);
                if(team.getPinPositions().containsAll(winningPosition)){
                    return Optional.of(team);
                }
            }
        }
        return Optional.empty();
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

    private Set<Set<Integer>> populateWinningPositions() {
        Set<Set<Integer>> positions = new HashSet<>();
        Set<Integer> verticalIndices = IntStream
                .iterate(0, i -> i+1)
                .limit(SIZE)
                .boxed()
                .collect(Collectors.toSet());
        Set<Integer> horizontalIndices = IntStream
                .iterate(0, i -> i+SIZE)
                .limit(SIZE)
                .boxed()
                .collect(Collectors.toSet());
        for(int horizontalIndex : horizontalIndices){
            positions.add(IntStream.iterate(horizontalIndex, i -> i+1)
                    .limit(SIZE)
                    .boxed()
                    .collect(Collectors.toSet()));
        }
        for(int verticalIndex :  verticalIndices){
            positions.add(IntStream.iterate(verticalIndex, i -> i+SIZE)
                    .limit(SIZE)
                    .boxed()
                    .collect(Collectors.toSet()));
        }
        positions.add(IntStream.iterate(0, i -> i +(SIZE+1))
                .limit(SIZE)
                .boxed()
                .collect(Collectors.toSet()));
        positions.add(IntStream.iterate(SIZE-1, i -> i +(SIZE-1))
                .limit(SIZE)
                .boxed()
                .collect(Collectors.toSet()));
        return positions;
    }
}
