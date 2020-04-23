package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int width = 3;
        int height = 3;
        String[][] fieldState = setField(height, width);
        boolean continueGame = true;

        Scanner scanner = new Scanner(System.in);

        String player = "X";
        while (continueGame) {
            printField(fieldState);
            makeMove(fieldState, scanner, player);
            GameEnd gameRes = checkWinner(fieldState);
            if (gameRes != GameEnd.CONTINUE) {
                printField(fieldState);
                continueGame = false;
            }  else {
                player = getNextPlayer(player);
            }
            printGameRes(gameRes);
        }

    }
    static String[][] setField(int height, int width) {
        String [][] field = new String[height][width];
        for(int i = 0; i < height; ++i) {
            for(int j = 0; j < width; ++j) {
                field[i][j] = " ";
            }
        }
        return field;
    }

    static void printField(String[][] field) {
        int pad = 1;
        int heightField = field.length;
        int widthFiled = field[0].length;

        int height = heightField + 2;
        int width = 2*widthFiled - 1 + 2*pad + 2;

        for (int i = 0, j = 0; j < height; ++j) {
            if (j == 0 || j == height - 1) {
                System.out.println("-".repeat(width));
            } else {
                System.out.println("|" + " ".repeat(pad) +  String.join(" ", field[i]) + " ".repeat(pad) + "|");
                ++i;
            }
        }
    }

    static void makeMove(String[][] field, Scanner scanner, String player) {
        int heightField = field.length;

        char coordXChar = 0;
        char coordYChar = 0;

        int coordY = coordYChar;
        int coordX = coordXChar;

        boolean validMove = false;
        do {
            System.out.println("Enter the coordinates:");
            String[] input = scanner.nextLine().split(" ");
            if (input.length == 2) {
                coordXChar = input[0].charAt(0);
                coordYChar = input[1].charAt(0);
            }
            if (!(Character.isDigit(coordXChar) &&  Character.isDigit(coordYChar))) {
                System.out.println("You should enter numbers!");
            } else {
                coordY = coordYChar - '0';
                coordX = coordXChar - '0';
                if (coordX < 1 || coordX > 3 || coordY < 1 || coordY > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else if (!field[heightField - coordY ][coordX - 1].equals(" ")) {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    validMove = true;
                }
            }
        } while(!validMove);

        field[heightField - coordY][coordX - 1] = player;
    }

    static GameEnd checkWinner(String[][] fieldState) {
        boolean xWins = horWinner(fieldState, "X") || verWinner(fieldState, "X") || diagWinner(fieldState, "X");

        boolean oWins = horWinner(fieldState, "O") || verWinner(fieldState, "O") || diagWinner(fieldState, "O");

        int countX = countPlayersMoves(fieldState, "X");
        int countO = countPlayersMoves(fieldState, "O");

        if (xWins && oWins || Math.abs(countO - countX) > 1) {
            return GameEnd.IMPOSSIBLE;
        } else if (xWins) {
                return GameEnd.X_WINS;
        } else if (oWins) {
            return GameEnd.O_WINS;
        }  else if (!gameFinished(fieldState)) {
            return GameEnd.CONTINUE;
        } else {
            return GameEnd.DRAW;
        }
    }

    static boolean horWinner(String[][] fieldState, String player) {
        boolean hasWinner = false;
        for (int i = 0; i < fieldState.length; ++i) {
            int j = 0;
            for (; j < fieldState[i].length; ++j) {
                if (!fieldState[i][j].equals(player)) {
                    break;
                }
            }
            if (j == fieldState[i].length) {
                hasWinner = true;
                break;
            }
        }
        return hasWinner;
    }

    static boolean verWinner(String[][] fieldState, String player) {
        boolean hasWinner = false;
        for (int j = 0; j < fieldState[0].length; ++j) {
            int i = 0;
            for (; i < fieldState.length; ++i) {
                if (!fieldState[i][j].equals(player)) {
                    break;
                }
            }
            if (i == fieldState.length) {
                hasWinner = true;
                break;
            }
        }
        return hasWinner;
    }

    static boolean diagWinner(String[][] fieldState, String player) {
        boolean hasWinner = false;
        int i = 0;
        for (int j = 0; j < 2; ++j) {
            for (; i < fieldState.length; ++i) {
                if (j % 2 == 0) {
                    if (!fieldState[i][i].equals(player)) {
                        break;
                    }
                } else {
                    if (!fieldState[i][fieldState[0].length - 1 - i].equals(player)) {
                        break;
                    }
                }
            }
            if (i == fieldState.length) {
                hasWinner = true;
                break;
            }
        }
        return hasWinner;
    }

    static int countPlayersMoves(String[][] fieldState, String player) {
        int count = 0;
        for (int i = 0; i < fieldState.length; ++i) {
            for (int j = 0; j < fieldState[i].length; ++j) {
                if (fieldState[i][j].equals(player)) {
                    ++count;
                }
            }
        }
        return count;
    }

    static boolean gameFinished(String[][] fieldState) {
        boolean notFinished = true;
        for (int i = 0; i < fieldState.length && notFinished; ++i) {
            for (int j = 0; j < fieldState[i].length; ++j) {
                if (fieldState[i][j].equals(" ")) {
                    notFinished = false;
                    break;
                }
            }
        }
        return notFinished;
    }

    static void printGameRes(GameEnd end) {
        switch (end) {
            case IMPOSSIBLE:
                System.out.println("Impossible");
                break;
            case X_WINS:
                System.out.println("X wins");
                break;
            case O_WINS:
                System.out.println("O wins");
                break;
            case DRAW:
                System.out.println("Draw");
                break;
        }
    }
    static String getNextPlayer(String player) {
        if (player.equals("X")) {
            return "O";
        } else {
            return "X";
        }
    }
    enum GameEnd {
        CONTINUE, X_WINS, O_WINS, IMPOSSIBLE, DRAW
    }
}
