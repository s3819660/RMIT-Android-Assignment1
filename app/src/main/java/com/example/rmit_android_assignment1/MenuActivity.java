package com.example.rmit_android_assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
        // Set theme
//        Intent intent = getIntent();
//        themeColor = intent.getStringExtra("theme_color");
//        try {
//            if (!themeColor.isEmpty())
//                setTheme(R.style.AppTheme_blue);
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }

//        setTheme(R.style.AppTheme_blue);
        setContentView(R.layout.activity_menu);

        // Set status bar color
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(getResources().getColor(R.color.blue_900));

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

//                System.out.println("result OK name1=" + name1);
//                System.out.println("result OK name2=" + name2);

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
                System.out.println("Send from settings, themecolor=" + themeColor);

//                setTheme(R.style.AppTheme_blue);
                // Set status bar color
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.blue_900));
                setTheme(R.style.AppTheme_blue);
                Intent recreateIntent = new Intent(this, MenuActivity.class);
                intent.putExtra("theme_color", themeColor);
                finish();
//                startActivity(recreateIntent);
                startActivity(getIntent());
            } else {
                System.out.println(resultCode);
                System.out.println("Result from settings CANCELLED");
            }
        }
    }
}

    //TODO: make menu animation