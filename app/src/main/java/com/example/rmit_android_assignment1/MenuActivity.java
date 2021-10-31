package com.example.rmit_android_assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button startBtn;
    Button settingBtn;

    String name1;
    String name2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startBtn = (Button) findViewById(R.id.btn_play);
    }

//    public void goToGameActivity(View view) {
//        Intent intent = new Intent(this, GameActivity.class);
//        startActivity(intent);
//    }

    public void goToRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToSettingsActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

//    public void goToStart(View view) {
//        Intent intent = new Intent(this, RegisterActivity.class);
//        startActivityForResult(intent, 100); // When first started the game
//
//        System.out.println("name1=" + name1);
//        System.out.println("name2=" + name2);
//
//
//    }

//    private void startActivityForResult(int requestCode, int resultCode, Intent intent) {
//        super(requestCode, resultCode, intent);
//
//        // Send back from the first game
//        if (requestCode == 0) {
//
//        }
//    }
}

    //TODO: ask for player name
    //TODO: make menu animation