package io.github.sandeeplakka.games;

import java.util.*;
import java.util.stream.Collectors;

public class TicTacToeGame {

    private static final Team INITIAL_TEAM = Team.TeamA;
    private static Board board = new Board();
    public static final int ROUNDS = 10;

    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame();
        game.playGame();
    }

    private static String getInput() {
        Scanner scannerObj = new Scanner(System.in);
        System.out.println("Enter position : ");
        return scannerObj.nextLine();
    }

    private Team getNextTeam(Team team){
        return Optional.ofNullable(team)
                .flatMap(team1 -> Arrays.stream(Team.values()).filter(team2 -> team2 != team1).findFirst())
                .orElse(INITIAL_TEAM);
    }
    private void playGame() {
        Map<Team,Integer> results = Arrays.stream(Team.values())
                .collect(Collectors.toMap(team -> team, team -> 0));

        for(int round = 1; round <= ROUNDS; round++) {
            board = new Board();
            Optional<Team> wonTeam = Optional.empty();
            Team team = null;
            board.printBoard();
            while (!(wonTeam.isPresent() || board.isFull())) {
                team = getNextTeam(team);
                System.out.println(team.name() + " is playing with pin '" + team.getPin() + "'");
                boolean isMoveValid = false;
                int horizontal, vertical;
                do {
                    String input = getInput();
                    horizontal = board.getHorizontalIndex(input);
                    vertical = board.getVerticalIndex(input);

                    if (board.isPinNotWithinBounds(horizontal, vertical)) {
                        System.out.println("Please give number between 1 and " + board.getSize());
                        continue;
                    }
                    if (board.isPinAlreadySet(horizontal, vertical)) {
                        System.out.println("Position is already set, please select another option");
                        continue;
                    }
                    isMoveValid = true;
                } while (!isMoveValid);

                board.setOnBoard(team, horizontal, vertical);
                wonTeam = board.checkIfWon();
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


}
