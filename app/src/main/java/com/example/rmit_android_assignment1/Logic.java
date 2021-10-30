package com.example.rmit_android_assignment1;

import android.widget.TextView;

import java.sql.SQLOutput;

public class Logic {
    private int[][] board;
    private int currentPlayer = 1;

    private TextView playerTurnView;
    private String playerNameX = "Player X";
    private String playerNameO = "Player O";

    Object[] winData;

    public Logic() {
        board = new int[3][3];

        // Default value
        winData = new Object[3];
        winData[0] = -1;
        winData[1] = -1;
        winData[2] = LineType.HORIZONTAL;

        resetBoard();
    }

    public boolean updateBoard(int row, int col) {
        //TODO: row -= 1; col -= 1;
        if (board[row - 1][col - 1] < 0) {
            board[row - 1][col - 1] = currentPlayer;

//            System.out.println("currentPlayer=" + currentPlayer);
            if (currentPlayer == 1)
                playerTurnView.setText((playerNameO + "'s Turn"));
            else
                playerTurnView.setText((playerNameX + "'s Turn"));

            return true;
        } else
            return false;
    }

    // Empty all cells in array
    public void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = -1; // empty all boxes on board
            }
        }

        // Reset to player 1-X's turn
        currentPlayer = 1;
    }

    // Return 0 if player O wins
    // Return 1 if player X wins
    public boolean checkWinner() {
        int winner = -1;
        boolean isWinnerFound = false;
        int count = 0;

        // Check horizontal win
        for (int row = 0; row < 3; row++) {
            winner = board[row][0];
            if (winner > -1) {
                if (board[row][1] == winner && board[row][2] == winner) {
                    isWinnerFound = true;
                    winData[0] = row;
                    winData[1] = 0;
                    winData[2] = LineType.HORIZONTAL;
                    break;
                }
            }
//            System.out.println("horizonal win");
        }

        // Check vertical win
        if (!isWinnerFound) {
            for (int col = 0; col < 3; col++) {
                winner = board[0][col];
                if (winner > -1) {
                    if (board[1][col] == winner && board[2][col] == winner) {
                        isWinnerFound = true;
                        winData[0] = 0;
                        winData[1] = col;
                        winData[2] = LineType.VERTICAL;
                        break;
                    }
                }
            }
//            System.out.println("vertical win");
        }

        // Check diagonal win
        if (!isWinnerFound && board[1][1] > -1) {
            winner = board[1][1];
            if (board[0][0] == winner && board[2][2] == winner) {
                winData[0] = -1;
                winData[1] = -1;
                winData[2] = LineType.NEGATIVE_DIAGONAL;
                isWinnerFound = true;
            } else if (board[0][2] == winner && board[2][0] == winner) {
                winData[0] = -1;
                winData[1] = -1;
                winData[2] = LineType.POSITIVE_DIAGONAL;
                isWinnerFound = true;
            }
//            System.out.println("diagonal win" + winData[2]);

        }

        if (isWinnerFound) {
            if (winner == 0)
                playerTurnView.setText((playerNameO + " won!!!!!!"));
            else
                playerTurnView.setText((playerNameX + " won!!!!!!"));
        } else {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] > -1)
                        count++;
                }
            }

            if (count == 9)
                playerTurnView.setText("Tie!");
        }

        return isWinnerFound;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
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
}
