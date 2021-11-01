package com.example.rmit_android_assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.nio.channels.InterruptedByTimeoutException;

public class MenuActivity extends AppCompatActivity {

    Button startBtn;
    Button settingBtn;

    String name1 = "";
    String name2 = "";
    String themeColor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set theme according to settings
        Intent intent = getIntent();
        themeColor = intent.getStringExtra("theme_color");
        // Try catch in case themeColor is null
        try {
            if (!themeColor.isEmpty()) {
                Window window;
                switch (themeColor) {
                    case "blue":
                        // Set theme from theme.xml
                        // Equivalent to getTheme().applyStyle(R.style.AppTheme_blue, true);
                        setTheme(R.style.AppTheme_blue);
                        // Set status bar color
                        window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.blue_900));
                        break;
                    case "green":
                        // Set theme from theme.xml
                        // Equivalent to getTheme().applyStyle(R.style.AppTheme_green, true);
                        setTheme(R.style.AppTheme_green);
                        // Set status bar color
                        window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.green_900));
                        break;
                    default:
                        // Set theme from theme.xml
                        setTheme(R.style.Theme_RMITAndroidAssignment1);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // Set content view
        setContentView(R.layout.activity_menu);

        // Get button view
        startBtn = (Button) findViewById(R.id.btn_play);
    }

    public void goToGameActivity(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("name1", name1);
        intent.putExtra("name2", name2);
        startActivityForResult(intent, 200); // From menu directly to game
    }

    public void goToRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, 100); // From menu to register
    }

    public void goToSettingsActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, 300); // From menu to settings
    }

    public void goToStart(View view) {
        if (name1.isEmpty()) {
            goToRegisterActivity(view);
        } else {
            //TODO: send names to game
            goToGameActivity(view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // If game started by registering name
        // Menu => Register => Game => Menu
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                name1 = intent.getStringExtra("name1");
                name2 = intent.getStringExtra("name2");
            } else {
                System.out.println(resultCode);
                System.out.println("Result from Game CANCELLED");
            }
        }

        // If game started directly
        // Menu => Register => Game => Menu => Game => Menu
        if (requestCode == 200) {

        }

        // Menu => Settings => Menu
        if (requestCode == 300) {
            if (resultCode == RESULT_OK) {
                themeColor = intent.getStringExtra("theme_color");
//                System.out.println("Send from settings, themecolor=" + themeColor);

//                setTheme(R.style.AppTheme_blue);
                // Set status bar color
//                Window window = getWindow();
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(getResources().getColor(R.color.blue_900));
//                setTheme(R.style.AppTheme_blue);
//                getTheme().applyStyle(R.style.AppTheme_blue, true);

                // Approach: finish this Menu and create another Menu
                Intent thisIntent = getIntent();
                thisIntent.putExtra("theme_color", themeColor);
                finish();
                startActivity(getIntent());

                // Approach: recreate() => try this already and it didn't work
                // It was supposed to work????
//                this.recreate();

            } else {
                System.out.println(resultCode);
                System.out.println("Result from settings CANCELLED");
            }
        }
    }
}

    //TODO: make menu animation