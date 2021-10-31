package com.example.rmit_android_assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText playerName1;
    EditText playerName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void goToGameActivity(View view) {
        String name1;
        String name2;

        getViews();

        name1 = playerName1.getText().toString();
        name2 = playerName2.getText().toString();

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("name1", name1);
        intent.putExtra("name2", name2);
        startActivity(intent);
        finish();
    }

    private void getViews() {
        playerName1 = (EditText) findViewById(R.id.player1_name);
        playerName2 = (EditText) findViewById(R.id.player2_name);
    }
}