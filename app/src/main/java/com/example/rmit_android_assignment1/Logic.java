package com.example.rmit_android_assignment1;

import android.widget.TextView;

public class Logic {
    private int[][] board;

    private int numCellPerRow;
    private int currentPlayer = 1;

    private String language = "en_US";

    private TextView playerTurnView;
    private String playerNameX = "Player X";
    private String playerNameO = "Player O";

    Object[] winData;

    public Logic(int numCellPerRow) {
        // Set number of cell per row
        this.numCellPerRow = numCellPerRow;
        // Set board as a new 2D array accordingly
        board = new int[numCellPerRow][numCellPerRow];

        // Initialize default value to draw win line later
        winData = new Object[numCellPerRow];
        winData[0] = -1;
        winData[1] = -1;
        winData[2] = LineType.HORIZONTAL;

        // Reset board
        resetBoard();
    }

    // Update board when a new cell is clicked on
    public boolean updateBoard(int row, int col) {
        // Convert row and column number to indices in the board array
        row -= 1;
        col -= 1;

        // Check if current cell is empty (-1)
        if (board[row][col] < 0) {
            // Mark current cell by current player
            board[row][col] = currentPlayer;

            // Check if the current language is Vietnamese and change the text view
            if (language.equals("vi")) {
                if (currentPlayer == 1)
                    playerTurnView.setText(("Lượt của " + playerNameO));
                else
                    playerTurnView.setText(("Lượt của " + playerNameX));
            } else {
                // Else set the text views to English
                if (currentPlayer == 1)
                    playerTurnView.setText((playerNameO + "'s Turn"));
                else
                    playerTurnView.setText((playerNameX + "'s Turn"));
            }
            return true;
        } else
            return false;
    }

    // Empty all cells in array
    public void resetBoard() {
        for (int row = 0; row < numCellPerRow; row++) {
            for (int col = 0; col < numCellPerRow; col++) {
                board[row][col] = -1; // empty all boxes on board
            }
        }

        // Reset to player 1-X's turn
        currentPlayer = 1;
    }

    // Return true if a there is a winner for 3x3 grid game
    public boolean checkWinner3x3() {
        int winner = -1;
        boolean isWinnerFound = false;

        // Check horizontal win
        for (int row = 0; row < numCellPerRow; row++) {
            winner = board[row][0];
            if (winner > -1) {
                if (board[row][1] == winner && board[row][2] == winner) {
                    // Already found winner
                    isWinnerFound = true;

                    // Set win data to draw win line
                    winData[0] = row;
                    winData[1] = 0;
                    winData[2] = LineType.HORIZONTAL;
                    break;
                }
            }
        }

        // Check vertical win
        if (!isWinnerFound) {
            for (int col = 0; col < numCellPerRow; col++) {
                winner = board[0][col];
                if (winner > -1) {
                    if (board[1][col] == winner && board[2][col] == winner) {
                        // Already found winner
                        isWinnerFound = true;

                        // Set win data to draw win line
                        winData[0] = 0;
                        winData[1] = col;
                        winData[2] = LineType.VERTICAL;
                        break;
                    }
                }
            }
        }

        // Check diagonal win
        if (!isWinnerFound && board[1][1] > -1) {
            winner = board[1][1];
            if (board[0][0] == winner && board[2][2] == winner) {
                // Set win data to draw win line
                winData[0] = -1;
                winData[1] = -1;
                winData[2] = LineType.NEGATIVE_DIAGONAL;

                // Already found winner
                isWinnerFound = true;
            } else if (board[0][2] == winner && board[2][0] == winner) {
                // Set win data to draw win line
                winData[0] = -1;
                winData[1] = -1;
                winData[2] = LineType.POSITIVE_DIAGONAL;

                // Already found winner
                isWinnerFound = true;
            }
        }

        // Display won/tie message according to current language
        displayResultMessage(isWinnerFound, winner, 9);

        return isWinnerFound;
    }

    // Return true if a there is a winner for 4x4 grid game
    public boolean checkWinner4x4() {
        int winner = -1;
        boolean isWinnerFound = false;

        // Check horizontal win
        for (int row = 0; row < numCellPerRow; row++) {
            winner = board[row][0];
            if (winner > -1) {
                if (board[row][1] == winner && board[row][2] == winner && board[row][3] == winner) {
                    // Already found winner
                    isWinnerFound = true;

                    // Set win data to draw win line
                    winData[0] = row;
                    winData[1] = 0;
                    winData[2] = LineType.HORIZONTAL;
                    break;
                }
            }
        }

        // Check vertical win
        if (!isWinnerFound) {
            for (int col = 0; col < numCellPerRow; col++) {
                winner = board[0][col];
                if (winner > -1) {
                    if (board[1][col] == winner && board[2][col] == winner && board[3][col] == winner) {
                        // Already found winner
                        isWinnerFound = true;

                        // Set win data to draw win line
                        winData[0] = 0;
                        winData[1] = col;
                        winData[2] = LineType.VERTICAL;
                        break;
                    }
                }
            }
        }

        // Check diagonal win
        if (!isWinnerFound) {
            if (board[0][0] == board[1][1] && board[0][0] == board[2][2] &&
                    board[0][0] == board[3][3] && board[0][0] > -1) {
                // Set win data to draw win line
                winData[0] = -1;
                winData[1] = -1;
                winData[2] = LineType.NEGATIVE_DIAGONAL;

                // Already found winner
                isWinnerFound = true;
                // Set winner
                winner = board[0][0];
            } else if (board[0][3] == board[1][2] && board[0][3] == board[2][1] &&
                    board[0][3] == board[3][0] && board[0][3] > -1) {
                // Set win data to draw win line
                winData[0] = -1;
                winData[1] = -1;
                winData[2] = LineType.POSITIVE_DIAGONAL;

                // Already found winner
                isWinnerFound = true;
                // Set winner
                winner = board[0][3];
            }
        }

        // Display won/tie message according to current language
        displayResultMessage(isWinnerFound, winner, 16);

        return isWinnerFound;
    }

    // Return true if a there is a winner for 5x5 grid game
    public boolean checkWinner5x5() {
        int winner = -1;
        boolean isWinnerFound = false;

        // Check horizontal win
        for (int row = 0; row < numCellPerRow; row++) {
            winner = board[row][0];
            if (winner > -1) {
                if (board[row][1] == winner && board[row][2] == winner &&
                        board[row][3] == winner && board[row][4] == winner) {
                    // Already found winner
                    isWinnerFound = true;

                    // Set win data to draw win line
                    winData[0] = row;
                    winData[1] = 0;
                    winData[2] = LineType.HORIZONTAL;
                    break;
                }
            }
        }

        // Check vertical win
        if (!isWinnerFound) {
            for (int col = 0; col < numCellPerRow; col++) {
                winner = board[0][col];
                if (winner > -1) {
                    if (board[1][col] == winner && board[2][col] == winner &&
                            board[3][col] == winner && board[4][col] == winner) {
                        // Already found winner
                        isWinnerFound = true;

                        // Set win data to draw win line
                        winData[0] = 0;
                        winData[1] = col;
                        winData[2] = LineType.VERTICAL;
                        break;
                    }
                }
            }
        }

        // Check diagonal win
        if (!isWinnerFound && board[2][2] > -1) {
            winner = board[2][2];
            if (board[0][0] == winner && board[1][1] == winner && board[3][3] == winner &&
                    board[4][4] == winner) {

                // Set win data to draw win line
                winData[0] = -1;
                winData[1] = -1;
                winData[2] = LineType.NEGATIVE_DIAGONAL;

                // Already found winner
                isWinnerFound = true;
            } else if (board[0][4] == winner && board[1][3] == winner && board[3][1] == winner
                    && board[4][0] == winner) {
                // Set win data to draw win line
                winData[0] = -1;
                winData[1] = -1;
                winData[2] = LineType.POSITIVE_DIAGONAL;

                // Already found winner
                isWinnerFound = true;
            }
        }

        // Display won/tie message according to current language
        displayResultMessage(isWinnerFound, winner, 25);

        return isWinnerFound;
    }

    // Display result message on Text View
    private void displayResultMessage(boolean isWinnerFound, int winner, int maxNumCell) {
        int count = 0;
        String wonMsg = " won!!!!!!";
        String tieMsg = "Tie!";

        // Check if current language is Vietnamese
        if (language.equals("vi")) {
            tieMsg = "Hòa!";
            wonMsg = " thắng!!!!!!";
        }
        // If winner was found, display won message
        if (isWinnerFound) {
            if (winner == 0)
                playerTurnView.setText((playerNameO + wonMsg));
            else
                playerTurnView.setText((playerNameX + wonMsg));
        } else {
            // Check if all cells were filled
            for (int row = 0; row < numCellPerRow; row++) {
                for (int col = 0; col < numCellPerRow; col++) {
                    if (board[row][col] > -1)
                        count++;
                }
            }

            // All cells were filled
            if (count == maxNumCell)
                // Display tie message
                playerTurnView.setText(tieMsg);
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayerTurnView(TextView playerTurnView) {
        this.playerTurnView = playerTurnView;
    }

    public void setPlayerNameX(String playerNameX) {
        this.playerNameX = playerNameX;
    }

    public void setPlayerNameO(String playerNameO) {
        this.playerNameO = playerNameO;
    }

    public Object[] getWinData() {
        return winData;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
