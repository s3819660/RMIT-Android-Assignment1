package com.example.rmit_android_assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private Board board;
    private TextView playerTurnView;
    String name1 = "Player 1";
    String name2 = "Player 2";
    String themeColor;
    String language = "en_US";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load theme and language according to previous settings using SharedPreferences
        // Theme should be retrieved before setting content view
        loadSavedPreferences();
        setThemeColor(themeColor);
        // And set content view
        setContentView(R.layout.activity_main); // R.layout.activity_main refers to the activity_main.xml file

        // Get the views
        getViews();

        // Load names sent from previous activities
        loadNamesFromIntent();

        // Set up game
        board.setUpBoard(playerTurnView, name1, name2, language);

        // Set first player name on TextView
        setFirstTurnView();

        // Change background of board if dark mode
        setDarkMode();
    }

    public void onResetClick(View view) {
        board.resetBoard();
        // Reset first player name on TextView
        if (language.equals("vi")) playerTurnView.setText(("Lượt của " + name1));
        else playerTurnView.setText((name1 + "'s Turn"));
    }

    public void onHomeClick(View view) {
        // Finish activity and go back to MenuActivity
        finish();
    }

    private void getViews() {
        board = (Board) findViewById(R.id.board);
        playerTurnView = (TextView) findViewById(R.id.text_view_turn);
    }

    private void setFirstTurnView() {
        if (language.equals("vi")) playerTurnView.setText(("Lượt của " + name1));
        else playerTurnView.setText((name1 + "'s Turn"));
    }

    private void setDarkMode() {
        int nightModeFlags =
                this.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES)
            board.setLineColor(Color.BLACK);
    }

    private void setThemeColor(String themeColor) {
        // Try catch in case themeColor is null
        try {
            if (!themeColor.isEmpty()) {
                Window window;
                switch (themeColor) {
                    case "blue":
                        // Set theme from theme.xml
                        // Equivalent to getTheme().applyStyle(R.style.AppTheme_blue, true);
                        setTheme(R.style.AppTheme_blueNoActionBar);
                        // Set status bar color
                        window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.blue_900));
                        break;
                    case "green":
                        // Set theme from theme.xml
                        // Equivalent to getTheme().applyStyle(R.style.AppTheme_green, true);
                        setTheme(R.style.AppTheme_greenNoActionBar);
                        // Set status bar color
                        window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.green_900));
                        break;
                    default:
                        // Set theme from theme.xml
                        setTheme(R.style.Theme_RMITAndroidAssignment1NoActionBar);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.print("Intial start of game: ");
            System.out.println(e.toString());
        }
    }

    private void loadSavedPreferences() {
        // Get saved SharedPreferences from last changes
        SharedPreferences savedDataSP = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        // Load saved preferences
        themeColor = savedDataSP.getString("color_theme", "pink");
        // Load saved language from last settings
        language = savedDataSP.getString("language", "en_US");
    }

    private void loadNamesFromIntent() {
        // Get intent and name extras
        Intent intent = getIntent();
        name1 = intent.getStringExtra("name1");
        name2 = intent.getStringExtra("name2");

        // name1 and name2 can be null due to null intent
        try {
            if (language.equals("vi")) {
                if (name1.isEmpty()) name1 = "Người chơi 1";
                if (name2.isEmpty()) name2 = "Người chơi 2";
            } else {
                if (name1.isEmpty()) name1 = "Player 1";
                if (name2.isEmpty()) name2 = "Player 2";
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}