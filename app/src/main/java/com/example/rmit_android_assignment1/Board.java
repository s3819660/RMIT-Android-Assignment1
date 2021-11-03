package com.example.rmit_android_assignment1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Board extends View {

    private int numCellPerRow; // number of cells per row
    private int cellSize = getWidth() / 3; // default might not be apply to apply
    public static final double CELL_PADDING_PERC = 0.15; // box padding percentage
    private boolean winLineExist = false;

    private int boardColor;
    private int lineColor;
    private final int colorX;
    private final int colorO;
    private final int colorWinLine;

    private final Paint paint = new Paint();
    private final Logic logic;

    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Get saved SharedPreferences from last changes
        SharedPreferences savedDataSP = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        // Load saved preferences
        numCellPerRow = Integer.parseInt(savedDataSP.getString("board_grid", "3"));
        // Create an instance of Game Logic
        logic = new Logic(numCellPerRow);

        // Array of custom attributes in attrs.xml
        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Board,
                0, 0);

        try {
            // Get attributes from styled array, all default values = 0
            boardColor = arr.getInteger(R.styleable.Board_boardColor, 0);
//            lineColor = arr.getInteger(R.styleable.Board_lineColor, 0);
            colorX = arr.getInteger(R.styleable.Board_colorX, 0);
            lineColor = colorX;
            colorO = arr.getInteger(R.styleable.Board_colorO, 0);
            colorWinLine = arr.getInteger(R.styleable.Board_colorWinLine, 0);
        } finally {
            // Recycle to free memory
            arr.recycle();
        }
    }

    @Override
    // onMeasure: when measuring current device's screen
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        // The board has to be of squared shape
        // Therefore, the dimension will vary depending on the device's measurement
        // Define dimension of board according to measurement
        // Get the smaller out of the current device's width and height
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());

        // Get cell size
//        numCellPerRow = 4;
        cellSize = dimension / numCellPerRow;

        // Set same dimension so we have a square that fits on the current device
        setMeasuredDimension(dimension, dimension);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        // Get x and y of user touch
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        // Action of event
        int action = motionEvent.getAction();

        // Convert x and y to row and column index
        // If action was a tap down
        if (action == MotionEvent.ACTION_DOWN) {
            int row = (int) Math.ceil(y / cellSize);
            int col = (int) Math.ceil(x / cellSize);

            if (!winLineExist) {
                if (logic.updateBoard(row, col)) {
                    // Redraw/Update view when something changes on a custom view
                    // When the board array is changed, invalidate() calls onDraw() again
                    // A built-in widget, on the other hand, has automatic invalidate() calls
                    invalidate();

                    // Check if anyone has won
                    switch (numCellPerRow) {
                        case 4:
                            if (logic.checkWinner4x4()) {
                                winLineExist = true;

                                // Redraw board
                                invalidate();
                            }
                            break;
                        case 5:
                            if (logic.checkWinner5x5()) {
                                winLineExist = true;

                                // Redraw board
                                invalidate();
                            }
                            break;
                        default:
                            if (logic.checkWinner3x3()) {
                                winLineExist = true;

                                // Redraw board
                                invalidate();
                            }
                            break;
                    }

                    // 0 is o
                    // 1 is x
                    if (logic.getCurrentPlayer() == 0)
                        logic.setCurrentPlayer(1);
                    else if (logic.getCurrentPlayer() == 1)
                        logic.setCurrentPlayer(0);
                }
            }

            invalidate();
            return true;
        }
        
        return false;
    }

    @Override
    // canvas = area of Board view
    // On draw: the view draw itself using this method
    // On create: this method is used to create activity
    protected void onDraw(Canvas canvas) {
        // Draw straight lines
        paint.setStyle(Paint.Style.STROKE);

        // Smooth out the edges of the shape, but has no impact on the shape's interior
        paint.setAntiAlias(true);

        drawBoard(canvas);
        drawMarker(canvas);

        if (winLineExist) {
            paint.setColor(colorWinLine);
            drawWinLine(canvas);
        }
    }

    // Reset view Board
    public void resetBoard() {
        // Reset board array
        logic.resetBoard();

        // Reset winner line
        winLineExist = false;
    
        // Redraw view
        invalidate();
    }

    private void drawMarker(Canvas canvas) {
        // 0 is o
        // 1 is x
        int[][] board = logic.getBoard();

        for (int row = 0; row < numCellPerRow; row++) {
            for (int col = 0; col < numCellPerRow; col++) {
                if (board[row][col] > -1) {
                    if (board[row][col] == 0)
                        drawO(canvas, row, col);
                    else
                        drawX(canvas, row, col);
                }
            }
        }
    }

    private void drawBoard(Canvas canvas) {
        // Paint board line color
        paint.setColor(lineColor);
        paint.setStrokeWidth(16);
        
        // Paint Board background color
        canvas.drawColor(boardColor);

        // Draw dividing lines
        for (int col = 1; col < numCellPerRow; col++) {
            canvas.drawLine(cellSize * col, 0, cellSize * col, canvas.getHeight(), paint);
        }

        for (int row = 1; row < numCellPerRow; row++) {
            canvas.drawLine(0, cellSize * row, canvas.getHeight(), cellSize * row, paint);
        }
    }

    private void drawX(Canvas canvas, int row, int col) {
        paint.setColor(colorX);

        // Draw diagonal line /
        canvas.drawLine((float) ((col + 1) * cellSize - cellSize * CELL_PADDING_PERC),
                (float) (row * cellSize + cellSize * CELL_PADDING_PERC),
                (float) (col * cellSize + cellSize * CELL_PADDING_PERC),
                (float) ((row + 1) * cellSize - cellSize * CELL_PADDING_PERC),
                paint);

        // Draw diagonal line \
        canvas.drawLine((float) (col * cellSize + cellSize * CELL_PADDING_PERC),
                (float) (row * cellSize + cellSize * CELL_PADDING_PERC),
                (float) ((col + 1) * cellSize - cellSize * CELL_PADDING_PERC),
                (float) ((row + 1) * cellSize - cellSize * CELL_PADDING_PERC),
                paint);
    }

    private void drawO(Canvas canvas, int row, int col) {
        paint.setColor(colorO);

        canvas.drawOval((float) (col * cellSize + cellSize * CELL_PADDING_PERC),
                        (float) (row * cellSize + cellSize * CELL_PADDING_PERC),
                        (float) (col * cellSize + cellSize - cellSize * CELL_PADDING_PERC),
                        (float) ((row * cellSize) + cellSize - cellSize * CELL_PADDING_PERC),
                        paint);
    }

    private void drawLine(Canvas canvas, int row, int col, LineType lineType) {
        if (lineType == LineType.HORIZONTAL && row > -1) {
            canvas.drawLine(col, row * cellSize + (float)(cellSize / 2),
                            cellSize * numCellPerRow, row * cellSize + (float)(cellSize / 2),
                            paint);
        } else if (lineType == LineType.VERTICAL) {
            canvas.drawLine(col * cellSize + (float)(cellSize / 2), row,
                            col * cellSize + (float)(cellSize / 2), cellSize * numCellPerRow,
                            paint);
        } else if (lineType == LineType.POSITIVE_DIAGONAL) {
            canvas.drawLine(0, cellSize * numCellPerRow,
                            cellSize * numCellPerRow, 0,
                            paint);
        } else if (lineType == LineType.NEGATIVE_DIAGONAL) { // Negative diagonal
            canvas.drawLine(0, 0,
                    cellSize * numCellPerRow, cellSize * numCellPerRow,
                    paint);
        }
    }

    private void drawWinLine(Canvas canvas) {
        Object[] winData = logic.getWinData();
        int row = (int) winData[0];
        int col = (int) winData[1];
        LineType winLineType = (LineType) winData[2];

        drawLine(canvas, row, col, winLineType);
    }
    
    public void setUpBoard(TextView playerTurnView, String playerNameX, String playerNameO) {
        logic.setPlayerTurnView(playerTurnView);
        logic.setPlayerNameO(playerNameO);
        logic.setPlayerNameX(playerNameX);
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }
}
