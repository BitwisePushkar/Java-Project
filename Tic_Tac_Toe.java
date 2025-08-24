import java.util.*;

public class Main {
    static String[] gameBoard; // Represents the Tic Tac Toe board
    static String currentPlayer; // Tracks whose turn it is (X or O)
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        gameBoard = new String[9];
        currentPlayer = "X";
        String winner = null;

        // Initialize board with numbers 1-9
        initializeBoard();

        System.out.println("Welcome to 3x3 Tic Tac Toe.");
        printBoard();
        System.out.println("X will play first. Enter a slot number (1-9) to place X:");

        while (winner == null) {
            int userInput = getPlayerInput(scanner);
            if (isSlotAvailable(userInput)) {
                gameBoard[userInput - 1] = currentPlayer; // Place the player's symbol

                printBoard();

                winner = checkWinner();

                if (winner == null) {
                    switchPlayer(); // Change turn
                    System.out.println(currentPlayer + "'s turn; enter a slot number to place " + currentPlayer + ":");
                }
            } else {
                System.out.println("Slot already taken; re-enter slot number:");
            }
        }

        displayResult(winner);
        scanner.close();
    }

    // Initializes the board with slot numbers 1 to 9
    static void initializeBoard() {
        for (int i = 0; i < 9; i++) {
            gameBoard[i] = String.valueOf(i + 1);
        }
    }

    // Prints the current board state
    static void printBoard() {
        System.out.println("|---|---|---|");
        System.out.println("| " + gameBoard[0] + " | " + gameBoard[1] + " | " + gameBoard[2] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + gameBoard[3] + " | " + gameBoard[4] + " | " + gameBoard[5] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + gameBoard[6] + " | " + gameBoard[7] + " | " + gameBoard[8] + " |");
        System.out.println("|---|---|---|");
    }

    // Gets valid input from the player
    static int getPlayerInput(Scanner scanner) {
        int input = -1;
        try {
            input = scanner.nextInt();
            if (input < 1 || input > 9) {
                System.out.println("Invalid input; please enter a number between 1 and 9:");
                return getPlayerInput(scanner);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input; please enter a number:");
            scanner.nextLine(); // clear the invalid input
            return getPlayerInput(scanner);
        }
        return input;
    }

    // Checks if the selected slot is still available
    static boolean isSlotAvailable(int slot) {
        return gameBoard[slot - 1].equals(String.valueOf(slot));
    }

    // Switches the turn to the other player
    static void switchPlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    // Checks if there's a winner or a draw
    static String checkWinner() {
        // All winning combinations
        int[][] winCombinations = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // diagonals
        };

        for (int[] combo : winCombinations) {
            String line = gameBoard[combo[0]] + gameBoard[combo[1]] + gameBoard[combo[2]];

            if (line.equals("XXX")) return "X";
            if (line.equals("OOO")) return "O";
        }

        // Check for draw (no empty slots left)
        boolean draw = true;
        for (int i = 0; i < 9; i++) {
            if (gameBoard[i].equals(String.valueOf(i + 1))) {
                draw = false;
                break;
            }
        }

        if (draw) return "draw";

        return null; // Game still in progress
    }

    // Displays final result
    static void displayResult(String winner) {
        if (winner.equalsIgnoreCase("draw")) {
            System.out.println("It's a draw! Thanks for playing.");
        } else {
            System.out.println("Congratulations! " + winner + " has won! Thanks for playing.");
        }
    }
}
