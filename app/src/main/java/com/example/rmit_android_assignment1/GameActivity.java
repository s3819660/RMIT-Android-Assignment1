package com.example.rmit_android_assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private Board board;
    private TextView playerTurnView;
    String name1 = "Player 1";
    String name2 = "Player 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // R.layout.activity_main refers to the activity_main.xml file

        // Get the views
        getViews();

        // Get intent
        Intent intent = getIntent();
        name1 = intent.getStringExtra("name1");
        name2 = intent.getStringExtra("name2");

        // name1 and name2 can be null due to null intent
        try {
            if (name1.isEmpty()) name1 = "Player 1";
            if (name2.isEmpty()) name2 = "Player 2";
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // Set up game
        board.setUpBoard(playerTurnView, name1, name2);
        // Set first player name on TextView
        playerTurnView.setText((name1 + "'s Turn"));

        // Change background of board if dark mode
        int nightModeFlags =
                this.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES)
            board.setLineColor(Color.BLACK);
    }

    public void onResetClick(View view) {
        board.resetBoard();
        // Reset first player name on TextView
        playerTurnView.setText((name1 + "'s Turn"));
    }

    public void onHomeClick(View view) {
        // Finish activity and go back to MenuActivity
        finish();
    }

    private void getViews() {
        board = (Board) findViewById(R.id.board);
        playerTurnView = (TextView) findViewById(R.id.text_view_turn);
    }
}