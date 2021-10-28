package com.example.rmit_android_assignment1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Board extends View {

    private int numCellPerRow; // number of cells per row
    private int cellSize = getWidth()/3; // default might not be apply to apply
    public static final double BOX_PADDING_MARGIN = 0.15;
    
    private final int boardColor;
    private final int lineColor;
    private final int colorX;
    private final int colorO;
    private final int colorWinLine;

    private final Paint paint = new Paint();

    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Array of custom attributes in attrs.xml
        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Board,
                0, 0);

        try {
            // Get attributes from styled array, all default values = 0
            boardColor = arr.getInteger(R.styleable.Board_boardColor, 0);
            lineColor = arr.getInteger(R.styleable.Board_lineColor, 0);
            colorX = arr.getInteger(R.styleable.Board_colorX, 0);
            colorO = arr.getInteger(R.styleable.Board_colorO, 0);
            colorWinLine = arr.getInteger(R.styleable.Board_colorWinLine, 0);
        } finally {
            // Recycle to free memory
            arr.recycle();
        }
    }

    @Override
    /**
     * onMeasure: when measuring current device's screen
     */
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        // The board has to be of squared shape
        // Therefore, the dimension will vary depending on the device's measurement
        // Define dimension of board according to measurement
        // Get the smaller out of the current device's width and height
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());

        // Get cell size
        numCellPerRow = 3;
        cellSize = dimension / numCellPerRow;

        // Set same dimension so we have a square that fits on the current device
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    /**
     * canvas = area of Board view
     */
    protected void onDraw(Canvas canvas) {
        // Draw straight lines
        paint.setStyle(Paint.Style.STROKE);

        // Smooth out the edges of the shape, but has no impact on the shape's interior
        paint.setAntiAlias(true);

        drawBoard(canvas);
    }

    private void drawBoard(Canvas canvas) {
        // Paint board line color
        paint.setColor(lineColor);
        paint.setStrokeWidth(16);
        
        // Paint Board background color
        canvas.drawColor(boardColor);

        // Draw dividing lines
        for (int col = 1; col < 3; col++) {
            canvas.drawLine(cellSize * col, 0, cellSize * col, canvas.getHeight(), paint);
        }

        for (int row = 1; row < 3; row++) {
            canvas.drawLine(0, cellSize * row, canvas.getHeight(), cellSize * row, paint);
        }

        // test drawX
        drawX(canvas, 0, 0);
        drawX(canvas, 0, 1);
        drawX(canvas, 0, 2);
        drawX(canvas, 1, 0);
//        drawX(canvas, 1, 1);
//        drawX(canvas, 1, 2);
//        drawX(canvas, 2, 0);
//        drawX(canvas, 2, 1);
//        drawX(canvas, 2, 2);
//        drawO(canvas, 0, 0);
//        drawO(canvas, 0, 1);
//        drawO(canvas, 0, 2);
//        drawO(canvas, 1, 0);
        drawO(canvas, 1, 1);
        drawO(canvas, 1, 2);
        drawO(canvas, 2, 0);
        drawO(canvas, 2, 1);
        drawO(canvas, 2, 2);
    }

    private void drawX(Canvas canvas, int row, int col) {
        paint.setColor(colorX);

        // Draw diagonal line /
        canvas.drawLine((float) ((col + 1) * cellSize - cellSize * BOX_PADDING_MARGIN),
                (float) (row * cellSize + cellSize * BOX_PADDING_MARGIN),
                (float) (col * cellSize + cellSize * BOX_PADDING_MARGIN),
                (float) ((row + 1) * cellSize - cellSize * BOX_PADDING_MARGIN),
                paint);

        // Draw diagonal line \
        canvas.drawLine((float) (col * cellSize + cellSize * BOX_PADDING_MARGIN),
                (float) (row * cellSize + cellSize * BOX_PADDING_MARGIN),
                (float) ((col + 1) * cellSize - cellSize * BOX_PADDING_MARGIN),
                (float) ((row + 1) * cellSize - cellSize * BOX_PADDING_MARGIN),
                paint);
    }

    private void drawO(Canvas canvas, int row, int col) {
        paint.setColor(colorO);

        canvas.drawOval((float) (col * cellSize + cellSize * BOX_PADDING_MARGIN),
                        (float) (row * cellSize + cellSize * BOX_PADDING_MARGIN),
                        (float) (col * cellSize + cellSize - cellSize * BOX_PADDING_MARGIN),
                        (float) ((row * cellSize) + cellSize - cellSize * BOX_PADDING_MARGIN),
                        paint);
    }
    

    public int getNumCellPerRow() {
        return numCellPerRow;
    }

    public void setNumCellPerRow(int numCellPerRow) {
        this.numCellPerRow = numCellPerRow;
    }
}
