package com.example.rmit_android_assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button startBtn;
    Button settingBtn;

    String name1 = "";
    String name2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startBtn = (Button) findViewById(R.id.btn_play);
    }

    public void goToGameActivity(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("name1", name1);
        intent.putExtra("name2", name2);
        startActivityForResult(intent, 200); // Go directly to game
    }

    public void goToRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, 100); // Go to register
    }

    public void goToSettingsActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goToStart(View view) {
//        System.out.println("name1=" + name1);
//        System.out.println("name2=" + name2);

        if (name1.isEmpty()) {
            goToRegisterActivity(view);
        } else {
            //TODO: send names to game
//            Intent intent = getIntent();
//            name1 = intent.getStringExtra("name1");
//            name2 = intent.getStringExtra("name2");
//
//            System.out.println("name1=" + name1);
//            System.out.println("name2=" + name2);
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

                System.out.println("result OK name1=" + name1);
                System.out.println("result OK name2=" + name2);

            } else {
                System.out.println(resultCode);
                System.out.println("result not OK");
            }
        }

        // If game started directly
        // Menu => Register => Game => Menu => Game => Menu
        if (requestCode == 200) {

        }
    }

}

    //TODO: make menu animation