package io.github.sandeeplakka.games;

import java.util.*;
import java.util.stream.Collectors;

public class TicTacToeGame {

    private static final Team INITIAL_TEAM = Team.Lucky;
    private static Team AI;
    private static Board board;
    public static final int ROUNDS = 5;
    private PlayMode playMode;

    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame();
        board = new Board(4);
        game.playGame();
    }

    //TODO : Error handling
    private static String getInput() {
        Scanner scannerObj = new Scanner(System.in);
        System.out.println("Enter input : ");
        return scannerObj.nextLine();
    }

    private Team getNextTeam(Team team){
        return team == null ? INITIAL_TEAM : (
                team != Team.Lucky ? Team.Lucky : Team.Ricky);
    }

    private void playGame() {
        Map<Team,Integer> results = Arrays.stream(Team.values())
                .collect(Collectors.toMap(team -> team, team -> 0));

        setPlayMode();

        for(int round = 1; round <= ROUNDS; round++) {
            board.initializeCleanBoard();
            Arrays.stream(Team.values()).forEach(Team::resetPinLocations);
            Optional<Team> wonTeam = Optional.empty();
            Team team = null;
            board.printBoard();
            while (!(wonTeam.isPresent() || board.isFull())) {
                team = getNextTeam(team);
                System.out.println(team.name() + " is playing with pin '" + team.getPin() + "'");
                boolean isMoveValid = false;
                int horizontal, vertical;
                do {
                    String input = getInputForTeam(team);
                    horizontal = board.getHorizontalIndex(input);
                    vertical = board.getVerticalIndex(input);

                    if (board.isPinNotWithinBounds(horizontal, vertical)) {
                        if(team != AI) {
                            System.out.println("Please give number between 1 and " + board.getSize());
                        }
                        continue;
                    }
                    if (board.isPinAlreadySet(horizontal, vertical)) {
                        if(team != AI){
                            System.out.println("Position is already set, please select another option");
                        }
                        continue;
                    }
                    isMoveValid = true;
                } while (!isMoveValid);

                board.setOnBoard(team, horizontal, vertical);
                //System.out.println(team.name()+" positions : "+team.getPinPositions());
                wonTeam = board.checkIfWon();
                //System.out.println("won team : "+wonTeam);
            }
            //Round over
            System.out.println();

            System.out.println("Round : " + (round) + "----------------------");
            if(wonTeam.isPresent()){
                Team winner = wonTeam.get();
                results.put(winner, results.get(winner)+1);
                System.out.println(winner.name() + " Won the match");
            }else if(board.isFull()) {
                System.out.println("Match tied!!!");
            }
            Arrays.stream(Team.values()).forEach(gameTeam -> {
                System.out.println(gameTeam.name() + " is with " + results.get(gameTeam) + " points");
            });

            System.out.println("-------------------------------\n");
        }
    }

    private String getInputForTeam(Team team) {
        Random random = new Random();
        String input;
        if(playMode == PlayMode.SINGLE_PLAYER && team == AI){
            int aiInput = random.nextInt(board.getSize()*board.getSize());
            input = String.format("%s,%s",
                    aiInput/board.getSize()+1,
                    aiInput%board.getSize()+1);
        }else{
            input = getInput();
        }
        return input;
    }

    private void setPlayMode() {
        System.out.println("Do you want to play 2 player mode or against PC?");
        System.out.println("Press 1 to play against PC");
        System.out.println("Press 2 to play 2-player mode");

        String input = getInput();
        if(Integer.parseInt(input) == 1){
            playMode = PlayMode.SINGLE_PLAYER;
            AI = Team.Ricky;
        }else if(Integer.parseInt(input) == 2){
            playMode = PlayMode.TWO_PLAYER;
        }else{
            System.out.println("Invalid input, please try again");
            System.exit(100); //E_INVALID_INPUT
        }
    }


}
