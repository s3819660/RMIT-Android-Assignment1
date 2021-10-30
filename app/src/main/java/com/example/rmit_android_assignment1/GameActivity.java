package com.example.rmit_android_assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private Board board;
    private TextView playerTurnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // R.layout.activity_main refers to the activity_main.xml file

        // Get the views
        getViews();

        // Set up game
        board.setUpBoard(playerTurnView, "Player X", "Player O");
        // Set first player name on TextView
        playerTurnView.setText("Player X's Turn");
    }

    public void onResetClick(View view) {
        board.resetBoard();
        // Reset first player name on TextView
        playerTurnView.setText("Player X's Turn");
    }

    public void onHomeClick(View view) {
        finish();
    }

    private void getViews() {
        board = (Board) findViewById(R.id.board);
        playerTurnView = (TextView) findViewById(R.id.text_view_turn);
    }
}