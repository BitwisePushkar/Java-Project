import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    // Game configuration values
    int boardWidth = 600;
    int boardHeight = 650;

    // Player symbols
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX; // X starts by default

    // Game state tracking
    boolean gameOver = false;
    int turns = 0;
    int winsX = 0;
    int winsO = 0;

    // GUI Components
    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();     // Displays game status (e.g., turn info, winner)
    JPanel textPanel = new JPanel();     // Holds the label at the top
    JPanel boardPanel = new JPanel();    // Grid of game buttons
    JButton[][] board = new JButton[3][3]; // 3x3 Tic-Tac-Toe board

    // Constructor - sets up the game interface and logic
    TicTacToe() {
        // Window settings
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Status label settings
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true); // Needed to show background color

        // Add label to panel and panel to frame
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Set up the game board panel (3x3 Grid)
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        // Initialize buttons and add them to the board
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                // Style the buttons
                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                // Add click behavior
                tile.addActionListener( // Start of method call
                    new ActionListener() { // Create a new object of an anonymous class that implements ActionListener
                    public void actionPerformed(ActionEvent e) {
                        // If game is over, don't allow moves
                        if (gameOver) return;

                        JButton tile = (JButton) e.getSource();

                        // Only allow placing mark on empty cell
                        if (tile.getText().equals("")) {
                            tile.setText(currentPlayer);
                            turns++; // Increment move count

                            checkWinner(); // Check for win or tie

                            // Switch player if game isn't over
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                }
                );
            }
        }
    }

    // Checks for a winner or a tie
    void checkWinner() {
        // Check horizontal rows
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText().equals("")) continue;

            if (board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                // Mark winning line
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                showWinnerAndRestart();
                return;
            }
        }

        // Check vertical columns
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().equals("")) continue;
            if (board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                showWinnerAndRestart();
                return;
            }
        }

        // Check main diagonal
        if (!board[0][0].getText().equals("") &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            showWinnerAndRestart();
            return;
        }

        // Check anti-diagonal
        if (!board[0][2].getText().equals("") &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            showWinnerAndRestart();
            return;
        }

        // If all moves played and no winner, it's a tie
        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
            showWinnerAndRestart();
        }
    }

    // Visually highlight winner tiles
    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
    }

    // Visually mark tie state
    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
    }

    // Shows a dialog with the result and handles replay logic
    void showWinnerAndRestart() {
        String resultMessage;

        if (turns == 9 && !hasWinner()) {
            resultMessage = "Tie!\n";
        } else {
            if (currentPlayer.equals(playerX)) {
                winsX++;
            } else {
                winsO++;
            }
            resultMessage = currentPlayer + " is the winner!\n";
        }

        resultMessage += "Total Wins:\nX: " + winsX + " | O: " + winsO;

        // Show dialog to restart or exit
        int option = JOptionPane.showConfirmDialog(frame, resultMessage + "\nPlay again?", "Game Over", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    // Returns true if any winning combination is found
    boolean hasWinner() {
        for (int i = 0; i < 3; i++) {
            // Horizontal
            if (!board[i][0].getText().equals("") &&
                board[i][0].getText().equals(board[i][1].getText()) &&
                board[i][1].getText().equals(board[i][2].getText())) return true;

            // Vertical
            if (!board[0][i].getText().equals("") &&
                board[0][i].getText().equals(board[1][i].getText()) &&
                board[1][i].getText().equals(board[2][i].getText())) return true;
        }

        // Main diagonal
        if (!board[0][0].getText().equals("") &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) return true;

        // Anti-diagonal
        if (!board[0][2].getText().equals("") &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) return true;

        return false;
    }

    // Reset game state and UI for a new round
    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.darkGray);
                board[r][c].setForeground(Color.white);
            }
        }
        turns = 0;
        gameOver = false;
        currentPlayer = playerX;
        textLabel.setText("Tic-Tac-Toe");
    }

    // Main method – entry point
    public static void main(String[] args) {
        new TicTacToe(); // Start the game
    }
}
